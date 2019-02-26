package cn.lifengming.study.framework.springmvc.servlet;

import cn.lifengming.study.framework.springmvc.annnotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by Lfm on 2018-12-18 20:39
 *
 * @author lifengming
 */
public class DispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String, Object> ioc = new HashMap<String, Object>();

    private List<Handler> handlerMapping = new ArrayList<Handler>();

    public DispatcherServlet() { super(); }

    @Override
    public void init(ServletConfig config) throws ServletException {

        //1、读取配置文件
        doLoadConfig( config.getInitParameter( "contextConfigLocation" ) );
        //2、扫描指定包路径下的类
        scanClass( contextConfig.getProperty( "scanPackage" ) );
        //3、把这些被扫描出来的类进行实例化(BeanFactroy)
        doInstance();
        //4、建立依赖关系，自动依赖注入
        doAutowired();
        //5、建立URL和Method的映射关系(HandlerMapping)
        doHandlerMapping();
        //输出一句话
        System.out.println( "Spring MVC Framework 已经准备就绪啦，欢迎来戳我!!!" );

    }

    private void doLoadConfig(String contextConfigLocation) {
        //	类路劲下去获取properties文件
        InputStream is = this.getClass().getClassLoader().getResourceAsStream( contextConfigLocation );

        try {
            contextConfig.load( is );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost( req, resp );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            //从上面已经初始化的信息中匹配
            //拿着用户请求url去找到其对应的Method
            boolean isMatcher = pattern( req, resp );
            if (! isMatcher) {
                resp.getWriter().write( "404 Not Found" );
            }
        } catch (Exception e) {
            resp.getWriter().write( "500 Exception,Details:\r\n" + e.getMessage() + "\r\n" +
                    Arrays.toString( e.getStackTrace() ).replaceAll( "\\[\\]", "" ).replaceAll( ",\\s", "\r\n" ) );
        }

    }

    private void scanClass(String packageName) {

        //拿到包路径，转换为文件路径
        URL url = this.getClass().getClassLoader().getResource( "/" + packageName.replaceAll( "\\.", "/" ) );

        File dir = new File( url.getFile() );

        //递归查找到所有的class文件
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanClass( packageName + "." + file.getName() );
            } else {
                //.class
                String className = packageName + "." + file.getName().replace( ".class", "" );
                classNames.add( className );
            }
        }

    }

    private void doInstance() {

        //利用反射机制将扫描到的类名全部实例化
        if (classNames.size() == 0) { return; }

        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName( className );
                //找那些加了@Controller、@Service

                if (clazz.isAnnotationPresent( Controller.class )) {

                    String beanName = lowerFirstChar( clazz.getSimpleName() );
                    ioc.put( beanName, clazz.newInstance() );

                } else if (clazz.isAnnotationPresent( Service.class )) {

                    Service Service = clazz.getAnnotation( Service.class );
                    String beanName = Service.value();
                    if (! "".equals( beanName.trim() )) {
                        ioc.put( beanName, clazz.newInstance() );
                        continue;
                    }
                    //如果自己没有起名字
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> i : interfaces) {
                        ioc.put( i.getName(), clazz.newInstance() );
                    }

                } else {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
        }

    }

    private void doAutowired() {

        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {

            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for (Field field : fields) {

                if (! field.isAnnotationPresent( Autowired.class )) {
                    continue;
                }

                Autowired autowired = field.getAnnotation( Autowired.class );

                String beanName = autowired.value().trim();
                if ("".equals( beanName )) {
                    beanName = field.getType().getName();
                }
                //如果是私有属性，设置访问权限
                field.setAccessible( true );

                try {
                    field.set( entry.getValue(), ioc.get( beanName ) );
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }

    }

    public void doHandlerMapping() {

        if (ioc.isEmpty()) {
            return;
        }

        for (Map.Entry<String, Object> entry : ioc.entrySet()) {

            Class<?> clazz = entry.getValue().getClass();
            if (! clazz.isAnnotationPresent( Controller.class )) {
                continue;
            }

            String url = "";
            if (clazz.isAnnotationPresent(RequestMapping.class )) {
               RequestMapping requstMapping = clazz.getAnnotation(RequestMapping.class );
                url = requstMapping.value();
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {

                if (! method.isAnnotationPresent(RequestMapping.class )) {
                    continue;
                }

               RequestMapping requstMapping = method.getAnnotation(RequestMapping.class );
                String customRegex = ( "/" + url + requstMapping.value() ).replaceAll( "/+", "/" );
                String regex = customRegex.replaceAll( "\\*", ".*" );

                Map<String, Integer> pm = new HashMap<String, Integer>();

                Annotation[][] pa = method.getParameterAnnotations();
                for (int i = 0; i < pa.length; i++) {
                    for (Annotation a : pa[i]) {
                        if (a instanceof RequestParam) {
                            String paramName = ( (RequestParam) a ).value();
                            if (! "".equals( paramName.trim() )) {
                                pm.put( paramName, i );
                            }
                        }
                    }
                }

                //提取Request和Response的索引
                Class<?>[] paramsTypes = method.getParameterTypes();
                for (int i = 0; i < paramsTypes.length; i++) {
                    Class<?> type = paramsTypes[i];
                    if (type == HttpServletRequest.class || type == HttpServletResponse.class) {
                        pm.put( type.getName(), i );
                    }
                }

                handlerMapping.add( new Handler( Pattern.compile( regex ), entry.getValue(), method, pm ) );

                System.out.println( "Mapping " + customRegex + "  " + method );
            }
        }

    }

    public boolean pattern(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //throw new Exception("这是一个假象，是我自己定义异常，弄着玩的");
        if (handlerMapping.isEmpty()) {
            return false;
        }

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace( contextPath, "" ).replaceAll( "/+", "/" );

        for (Handler handler : handlerMapping) {

            try {

                Matcher matcher = handler.pattern.matcher( url );

                if (! matcher.matches()) {
                    continue;
                }

                Class<?>[] paramTypes = handler.method.getParameterTypes();

                Object[] paramValues = new Object[paramTypes.length];

                Map<String, String[]> params = req.getParameterMap();

                for (Map.Entry<String, String[]> param : params.entrySet()) {

                    String value =
                            Arrays.toString( param.getValue() ).replaceAll( "\\]|\\[", "" ).replaceAll( ",\\s", "," );

                    if (! handler.paramMapping.containsKey( param.getKey() )) {
                        continue;
                    }

                    int index = handler.paramMapping.get( param.getKey() );

                    //涉及到类型转换
                    paramValues[index] = castStringValue( value, paramTypes[index] );

                }

                //
                int reqIndex = handler.paramMapping.get( HttpServletRequest.class.getName() );
                paramValues[reqIndex] = req;

                int repIndex = handler.paramMapping.get( HttpServletResponse.class.getName() );
                paramValues[repIndex] = resp;

                //需要对象.方法
                handler.method.invoke( handler.controller, paramValues );

                return true;
            } catch (Exception e) {
                throw e;
            }
        }
        return false;

    }

    private Object castStringValue(String value, Class<?> clazz) {

        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class) {
            return Integer.valueOf( value );
        } else if (clazz == int.class) {
            return Integer.valueOf( value ).intValue();
        } else {
            return null;
        }

    }

    /**
     * 手写字母转换成小写字母
     */
    public String lowerFirstChar(String str) {

        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf( chars );

    }

    private class Handler {

        protected Pattern pattern;
        protected Object controller;
        protected Method method;
        protected Map<String, Integer> paramMapping;

        protected Handler(Pattern pattern, Object controller, Method method, Map<String, Integer> paramMapping) {
            this.pattern = pattern;
            this.controller = controller;
            this.method = method;
            this.paramMapping = paramMapping;
        }

    }
}
