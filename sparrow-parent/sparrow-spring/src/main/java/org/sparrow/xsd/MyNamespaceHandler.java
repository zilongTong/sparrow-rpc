package org.sparrow.xsd;

/**
 * @ClassName MyNamespaceHandler
 * @Author Reference
 * @Description //TODO
 * @Date: 2018/12/20 11:12
 **/
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("reference", new MyBeanDefinitionParser());
    }
}