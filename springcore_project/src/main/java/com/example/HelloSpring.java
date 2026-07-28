package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HelloSpring{

    @Qualifier("good")
    private Message message;

    public HelloSpring() {
        message = new Message();
    }

    @Autowired
    public HelloSpring(Message message) {
        this.message = message;
    }

    public void getMessage(){
        System.out.println("Hello Spring! +" + message.format());
    }
}
