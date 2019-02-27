package cn.lifengming.study.springbeen;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author lifengming
 * @date 2019-02-27 15:47
 * 可以监控been的创建在 初始化之前 与 初始化之后 的一些操作
 */

public class InitBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println( "9.been初始化前置处理(BeanPostProcessor：postProcessBeforeInitialization), bean = " + o.getClass() );
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println( "13.been初始化后置处理(BeanPostProcessor：postProcessAfterInitialization), bean = " + o.getClass() );
        return o;
    }
}
