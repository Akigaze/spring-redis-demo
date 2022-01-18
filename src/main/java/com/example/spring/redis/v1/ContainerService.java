package com.example.spring.redis.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContainerService {
  @Qualifier("containerRedisTemplate")
  private final RedisTemplate<String, ContainerEntity> redisTemplate;

  //  @Cacheable(cacheNames = "container", key = "container.refNum")
  public ContainerEntity save(ContainerEntity container) {
    redisTemplate.opsForValue().set("container:" + container.getRefNum(), container);
    return container;
  }

  //  @Cacheable(cacheNames = "container", key = "container.refNum")
  public ContainerEntity get(String refNum) {
    return redisTemplate.opsForValue().get("container:" + refNum);

  }
}
