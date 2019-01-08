package org.sparrow;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] arg){
        new ClassPathXmlApplicationContext("classpath:spring.xml");
    }
}
