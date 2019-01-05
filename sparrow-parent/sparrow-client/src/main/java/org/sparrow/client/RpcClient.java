package org.sparrow.client;

import org.apache.commons.collections4.MapUtils;
import org.sparrow.common.annotation.Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @ClassName RpcClient
 * @Author Leo
 * @Description //TODO
 * @Date: 2018/12/31 10:54
 **/
public class RpcClient implements ApplicationContextAware, InitializingBean {


    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        //根据obj的类型、创建一个新的bean、添加到Spring容器中，
        //注意BeanDefinition有不同的实现类，注意不同实现类应用的场景
        BeanDefinition beanDefinition = new GenericBeanDefinition();
//        beanDefinition.setBeanClassName(obj.getClass().getName());
//        beanFactory.registerBeanDefinition(obj.getClass().getName(), beanDefinition);
    }
}
