package org.sparrow.spring;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;

import java.lang.reflect.Method;


/**
 * @ClassName MyProxy
 * @Author Reference
 * @Description //TODO
 * @Date: 2019/1/5 15:38
 **/
public class MyProxy implements InvocationHandler {

    private Class<?> interfaceClass;

    public Object bind(Class<?> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println(proxy);
        System.out.println(method);
        if (args.length > 0) {
            for (Object o : args) {
                System.out.println(o.toString());
            }
        }
        return method.getName() + "  is  invoking";
    }

}

