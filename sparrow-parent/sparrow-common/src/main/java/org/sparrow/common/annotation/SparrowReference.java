package org.sparrow.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @ClassName SparrowReference
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/5/22 15:36
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SparrowReference {

    String name() default "";

    @AliasFor("value")
    String[] value() default {};

    String balanceStrategy() default "";

}
