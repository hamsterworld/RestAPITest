package com.example.demo.helloworld;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloWordController {


    private final MessageSource messageSource;

    @GetMapping("/hello-world")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello world bean");
    }

    @GetMapping("/hello-world-bean/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World , %s",name));
    }

    @GetMapping("/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name = "Accept-Language",required = false) Locale locale){
        return messageSource.getMessage("greeting.message",null,locale);
    }

}
