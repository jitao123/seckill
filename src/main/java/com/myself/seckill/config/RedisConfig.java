package com.myself.seckill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @description:
 * @author: AT
 * @Date: 2021/1/20 3:55 下午
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        /**
         * key 序列化
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        /**
         * value 序列化
         */
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        /**
         * hash key 序列化
         */
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        /**
         * hash value 序列化
         */
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
