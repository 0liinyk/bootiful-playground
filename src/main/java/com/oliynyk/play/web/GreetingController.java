package com.oliynyk.play.web;

import com.oliynyk.play.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@Slf4j
public class GreetingController {
    @Autowired
    @Lazy
    private UserService userService;

    @GetMapping("/greeting/{userName}")
    public String getGreeting(@PathVariable String userName, Locale locale){
        log.info("Requesting greeting for user: {} with locale: {}", userName, locale);
        return userService.generatePersonalizedGreeting(userName);
    }
}
