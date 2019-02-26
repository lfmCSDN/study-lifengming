package cn.lifengming.study.framework.springmvc.annnotation;

import java.lang.annotation.*;

/**
 * create by Lfm
 * on 2018-12-18 21:06
 */
// 很明显这个注解可以用到构造器，变量域，方法，注解类型上。
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})  //字段上
@Retention(RetentionPolicy.RUNTIME) //运行时
@Documented //可见的
public @interface Autowired {

    String value() default "";
}
