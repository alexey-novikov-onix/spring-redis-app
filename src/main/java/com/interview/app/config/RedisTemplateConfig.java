package com.interview.app.config;

import com.interview.app.entity.MessageEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, MessageEntity> redisTemplate(final RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, MessageEntity> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(MessageEntity.class));
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

}
