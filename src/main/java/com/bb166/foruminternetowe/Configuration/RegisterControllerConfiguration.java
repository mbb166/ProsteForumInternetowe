package com.bb166.foruminternetowe.Configuration;

import com.bb166.foruminternetowe.Tool.Captcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class RegisterControllerConfiguration {
    @Bean
    public ConcurrentHashMap<Integer, Captcha> captchaConcurrentHashMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ScheduledExecutorService executorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
