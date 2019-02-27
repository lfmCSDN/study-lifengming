package cn.lifengming.study.springbeen;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author lifengming
 * @date 2019-02-27 15:46
 */
public class BeanFactoryPostProcessorTest implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        System.out.println("1.工厂后置类(BeanFactoryPostProcessor:postProcessorBeanFactory) , ApplicationContext容器初始化中refresh()中调用");
    }
}
