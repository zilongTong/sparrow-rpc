package org.sparrow.xsd;

/**
 * @ClassName MyBeanDefinitionParser
 * @Author Reference
 * @Description //TODO
 * @Date: 2018/12/20 11:12
 **/
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class MyBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    protected Class getBeanClass(Element element) {
        return Reference.class;
    }
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String name = element.getAttribute("name");
        String id = element.getAttribute("id");
        String interfaces = element.getAttribute("interfaces");
        if (StringUtils.hasText(id)) {
            bean.addPropertyValue("id", id);
        }
        if (StringUtils.hasText(name)) {
            bean.addPropertyValue("name", name);
        }
        if (StringUtils.hasText(interfaces)) {
            bean.addPropertyValue("interfaces", interfaces);
        }
    }
}