package org.example.userservice.controller;

import org.example.userservice.component.GreetingComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WelcomeController {
    private Environment env;
    private GreetingComponent greetingComponent;

    @Autowired
    public WelcomeController(Environment env, GreetingComponent greetingComponent) {
        this.env = env;
        this.greetingComponent = greetingComponent;
    }

    @GetMapping("/check")
    public String status(){
        return "OK " + env.getProperty("local.server.port");
    }
    @GetMapping("/welcome")
    public String welcome(){
        return env.getProperty("greeting.message");
    }
    @GetMapping("/greeting")
    public String greeting(){
        return greetingComponent.getMessage();
    }
}

