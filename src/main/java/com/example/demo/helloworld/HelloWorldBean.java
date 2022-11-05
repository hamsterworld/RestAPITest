package com.example.demo.helloworld;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
public class HelloWorldBean {

    private String message;
    public HelloWorldBean(String s){
        this.message = s;
    }


}
