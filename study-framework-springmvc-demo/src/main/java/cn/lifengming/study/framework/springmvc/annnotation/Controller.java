package cn.lifengming.study.framework.springmvc.annnotation;

import java.lang.annotation.*;

/**
 * create by Lfm
 * on 2018-12-18 21:06
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) //运行时
@Documented //可见的
public @interface Controller {

    String value() default "";
}
