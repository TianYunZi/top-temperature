package com.ahnu.gis.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectConfig {

    @Bean("objectMapper")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
