package cn.lifengming.study.framework.springmvc.annnotation;

import java.lang.annotation.*;

/**
 * create by Lfm
 * on 2018-12-18 21:10
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME) //运行时
@Documented //可见的
public @interface RequestParam {
    String value() default "";
}



