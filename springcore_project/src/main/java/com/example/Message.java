package com.example;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("message")
@Qualifier("good")
public class Message {
    public String format() {
        return "Message";
    }
}
