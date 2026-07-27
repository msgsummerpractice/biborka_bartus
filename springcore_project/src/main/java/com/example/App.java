package com.example;

/**
 * Hello world!
 *
 */

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App 
{
    public static void main( String[] args )
    {
        // ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        // HelloSpring obj = (HelloSpring) context.getBean("spring", HelloSpring.class);
        // obj.getMessage();
        // ((ClassPathXmlApplicationContext) context).close();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloSpring obj = (HelloSpring) ctx.getBean(HelloSpring.class);
        obj.getMessage();
        ((AnnotationConfigApplicationContext) ctx).close();
    }
}
