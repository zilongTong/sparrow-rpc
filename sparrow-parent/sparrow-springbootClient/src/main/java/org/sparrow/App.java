package org.sparrow;

import org.sparrow.api.ILeoService;
import org.sparrow.api.ITonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring.xml");
        ILeoService leoService = (ILeoService) ctx.getBean("leoService");
        ITonService tonService = (ITonService) ctx.getBean("tonService");
        System.out.println("-----------------------------");
        System.out.println("leo返回值--------------->"+leoService.cool("11").toString());
        System.out.println("ton返回值--------------->"+tonService.say().toString());
    }
}
