package com.sftech.sales.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.URI;

@Configuration
public class RedisConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.data.redis.host:localhost}")
    private String host;

    @Value("${spring.data.redis.port:6379}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.username:}")
    private String username;

    @Value("${spring.data.redis.url:}")
    private String redisUrl;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        if (redisUrl != null && !redisUrl.isEmpty()) {
            try {
                URI uri = URI.create(redisUrl);
                String uriHost = uri.getHost();
                if (uriHost != null) {
                    config.setHostName(uriHost);
                }
                int uriPort = uri.getPort();
                config.setPort(uriPort > 0 ? uriPort : 6379);
                
                String userInfo = uri.getUserInfo();
                if (userInfo != null && !userInfo.isEmpty()) {
                    String[] credentials = userInfo.split(":", 2);
                    if (credentials.length > 0 && !credentials[0].isEmpty()) {
                        config.setUsername(credentials[0]);
                    }
                    if (credentials.length > 1 && !credentials[1].isEmpty()) {
                        config.setPassword(credentials[1]);
                    }
                }
            } catch (Exception e) {
                logger.warn("Failed to parse REDIS_URL, using individual properties: {}", e.getMessage());
                if (host != null) {
                    config.setHostName(host);
                }
                config.setPort(port);
                if (password != null && !password.isEmpty()) {
                    config.setPassword(password);
                }
                if (username != null && !username.isEmpty()) {
                    config.setUsername(username);
                }
            }
        } else {
            if (host != null) {
                config.setHostName(host);
            }
            config.setPort(port);
            if (password != null && !password.isEmpty()) {
                config.setPassword(password);
            }
            if (username != null && !username.isEmpty()) {
                config.setUsername(username);
            }
        }

        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }
}

