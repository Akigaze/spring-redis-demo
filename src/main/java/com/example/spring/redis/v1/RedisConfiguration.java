package com.example.spring.redis.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {
  private final ObjectMapper objectMapper;

  @Bean
  public RedisTemplate<String, ContainerEntity> containerRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
    RedisTemplate<String, ContainerEntity> template = new RedisTemplate<>();
    template.setKeySerializer(new KeyPrefixStringRedisSerializer("cntr"));
    //    template.setKeySerializer(new StringRedisSerializer());
    Jackson2JsonRedisSerializer<ContainerEntity> serializer = new Jackson2JsonRedisSerializer<>(ContainerEntity.class);
    serializer.setObjectMapper(objectMapper);
    template.setValueSerializer(serializer);
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
