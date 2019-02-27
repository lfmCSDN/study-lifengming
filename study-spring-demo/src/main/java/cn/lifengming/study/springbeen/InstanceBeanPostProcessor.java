package cn.lifengming.study.springbeen;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

/**
 * @author lifengming
 * @date 2019-02-27 15:48
 */
public class InstanceBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> aClass, String s) throws BeansException {
        System.out.println("2.实例化bean之前调用,即调用bean类构造函数之前调用（InstantiationAwareBeanPostProcessor:postProcessBeforeInstantiation） " + aClass.getName());
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object o, String s) throws BeansException {
        System.out.println( "4.bean实例化后调用,返回boolean,并且返回false则不会注入属性(InstantiationAwareBeanPostProcessor:postProcessAfterInstantiation)" );
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues,
            PropertyDescriptor[] propertyDescriptors, Object o, String s) throws BeansException {
        System.out.println( "5.属性注入之前调用(InstantiationAwareBeanPostProcessor:postProcessPropertyValues)...... beanName = " + s + " 属性名集合 : " +
                Arrays.toString( propertyValues.getPropertyValues() ) );
        return propertyValues;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println( "10.been初始化前置处理(InstantiationAwareBeanPostProcessor:postProcessBeforeInitialization) " );
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println( "14.postProcessAfterInitialization(InstantiationAwareBeanPostProcessor) " );
        return o;
    }
}
