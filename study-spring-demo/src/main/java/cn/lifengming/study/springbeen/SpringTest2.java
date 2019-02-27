package cn.lifengming.study.springbeen;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lifengming
 * @date 2019-02-27 15:52
 */
public class SpringTest2 {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext( "classpath*:config/appcontext-*.xml" );
        HelloWord obj = (HelloWord) context.getBean( "helloWord" );
        System.out.println( "15.Bean working, message = " + obj.getMessage() );
        ( (ClassPathXmlApplicationContext) context ).close();
    }
}
