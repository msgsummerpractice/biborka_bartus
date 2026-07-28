package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App 
{
    public static void main( String[] args )
    {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        HelloSpring obj = (HelloSpring) ctx.getBean(HelloSpring.class);
        obj.getMessage();
        ((AnnotationConfigApplicationContext) ctx).close();
    }
}
