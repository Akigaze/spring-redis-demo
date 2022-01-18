package com.example.spring.redis.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfiguration {
  @Bean
  public RedisTemplate<String, ContainerEntity> containerRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, ContainerEntity> template = new RedisTemplate<>();
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ContainerEntity.class));
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}