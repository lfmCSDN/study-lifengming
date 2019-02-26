package cn.lifengming.study.framework.springmvc.annnotation;

import java.lang.annotation.*;

/**
 * create by Lfm
 * on 2018-12-18 21:10
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME) //运行时
@Documented //可见的
public @interface RequestMapping {
    String value() default "";
}



