package org.sparrow.common.annotation;

import java.lang.annotation.*;

/**
 * @ClassName Reference
 * @Author Leo
 * @Description //TODO
 * @Date: 2018/12/31 11:10
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Reference {
    Class<?> interfaceClass() default void.class;

    String interfaceName() default "";
}
