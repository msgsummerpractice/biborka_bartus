package com.example;

/**
 * Hello world!
 *
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        HelloSpring obj = (HelloSpring) context.getBean("spring", HelloSpring.class);
        obj.getMessage();
        ((ClassPathXmlApplicationContext) context).close();
    }
}
