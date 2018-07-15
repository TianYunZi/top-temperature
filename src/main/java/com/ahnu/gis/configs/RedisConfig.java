package com.ahnu.gis.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * Redis配置
 */
@Configuration
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${redis.url}")
    private String url;

    @Value("${redis.port}")
    private Integer port;

    @Bean("jedis")
    public Jedis jedis() {
        LOGGER.info("配置的redis url:{}, 端口: {}", new Object[]{url, port});
        return new Jedis(url, port);
    }
}
