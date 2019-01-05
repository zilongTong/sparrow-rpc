package org.sparrow.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Service
 * @Author Leo
 * @Description
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component //spring 扫描
public @interface Service {
    Class<?> interfaceClass() default void.class;
    String name() default "";
}
