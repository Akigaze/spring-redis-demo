package com.example.spring.redis.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ContainerService {
  @Qualifier("containerRedisTemplate")
  private final RedisTemplate<String, ContainerEntity> redisTemplate;

  public ContainerEntity save(ContainerEntity container) {
    if (containerExisted(container)) {
      return null;
    }
    redisTemplate.opsForValue().set(container.getRefNum(), container);
    return container;
  }

  public ContainerEntity save(ContainerEntity container, int secondsToLive) {
    if (secondsToLive > 0) {
      if (containerExisted(container)) {
        return null;
      }
      redisTemplate.opsForValue().set(container.getRefNum(), container, Duration.ofSeconds(secondsToLive));
      return container;
    }
    return this.save(container);
  }

  private Boolean containerExisted(ContainerEntity container) {
    return redisTemplate.hasKey(container.getRefNum());
  }

  public ContainerEntity get(String refNum) {
    return redisTemplate.opsForValue().get(refNum);

  }

  public Boolean delete(String refNum) {
    return redisTemplate.delete(refNum);
  }

  public ContainerEntity update(ContainerEntity container) {
    ContainerEntity existed = this.get(container.getRefNum());
    if (existed == null) {
      return null;
    }
    this.copyNonNullProperties(existed, container);
    redisTemplate.opsForValue().set(existed.getRefNum(), existed);
    return existed;
  }

  private void copyNonNullProperties(ContainerEntity existed, ContainerEntity updated) {
    if (updated.getTenantId() != null) {
      existed.setTenantId(updated.getTenantId());
    }
    if (updated.getScac() != null) {
      existed.setScac(updated.getScac());
    }
    if (updated.getBookingNumber() != null) {
      existed.setBookingNumber(updated.getBookingNumber());
    }
    if (updated.getContainerNumber() != null) {
      existed.setContainerNumber(updated.getContainerNumber());
    }
    if (updated.getCreatedTime() != null) {
      existed.setCreatedTime(updated.getCreatedTime());
    }
  }


}
