package com.oliynyk.play.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ExceptionToStatusCodeConfig {

    @Bean
    Map<? extends Class<? extends Throwable>, HttpStatus> exceptionToStatusCodeMap(){
        Map<Class<? extends Throwable>, HttpStatus> exceptionToStatusCodeMap = new HashMap<>();
        return exceptionToStatusCodeMap;
    }
}
