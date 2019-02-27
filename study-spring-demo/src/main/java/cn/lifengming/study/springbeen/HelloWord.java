package cn.lifengming.study.springbeen;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * @author lifengming
 * @date 2019-02-27 15:36
 */
public class HelloWord implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        System.out.println("6.属性注入之后，setMessage的相关属性（BeanNameAware:setBeanName）");
        this.message = message;
    }

    public HelloWord() {
        System.out.println( "3.bean实例化，调用构造函数:"+this.getClass().getName() );
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //setBeanName 后调用
        System.out.println( "8. setBeanName后调用(BeanFactory：setBeanFactory)" );
    }

    @Override
    public void setBeanName(String s) {
        //属性注入后调用
        System.out.println( "7.属性注入后调用(BeanNameAware：setBeanName) , 此时s = " + s );
    }

    @Override
    public void destroy() throws Exception {
        System.out.println( "16. 在processAfterInitialization之后,配置的xml_destroy之前调用[destroy(DisposableBean)]" );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println( "11. processBeforeInitialization之后,配置的xml_init之前调用[afterPropertiesSet(InitializingBean)]" );
    }

    public void xml_init() {
        //xml开头的表示配置文件配置,这里是bean配置中init-method配置调用
        System.out.println( "12.HelloWorld 初始化(init-method)" );
    }

    public void xml_destroy() { //destroy-method 配置调用
        System.out.println( "17.HelloWorld 销毁(destroy-method)" );
    }
}
