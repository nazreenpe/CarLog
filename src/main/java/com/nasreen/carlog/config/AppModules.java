package com.nasreen.carlog.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppModules {
    @Bean
    public OkHttpClient okHttpClient() {
       return new OkHttpClient();
    }
}
