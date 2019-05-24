package org.sparrow.proxy.registry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SpringConfiguration
 * @Author Leo
 * @Description //TODO
 * @Date: 2019/5/23 10:37
 **/
@Configuration
public class SpringConfiguration {

    @Bean
    public SpringBeanRegistry customBeanDefinitionRegistry() {
        return new SpringBeanRegistry();
    }


}