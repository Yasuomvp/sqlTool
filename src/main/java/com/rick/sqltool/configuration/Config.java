package com.rick.sqltool.configuration;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootConfiguration
public class Config {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){

        return new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors() * 2,
                100,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue()
        );

    }

}
