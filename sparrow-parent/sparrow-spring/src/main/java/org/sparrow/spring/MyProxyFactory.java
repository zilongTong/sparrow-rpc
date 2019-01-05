package org.sparrow.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @ClassName MyProxyFactory
 * @Author Reference
 * @Description //TODO
 * @Date: 2019/1/5 15:36
 **/
public class MyProxyFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;
    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }
    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
    @Override
    public T getObject() throws Exception {
        return (T) new MyProxy().bind(interfaceClass);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        // 单例模式
        return true;
    }

}

