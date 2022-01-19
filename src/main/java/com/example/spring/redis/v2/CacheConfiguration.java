package com.example.spring.redis.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfiguration extends CachingConfigurerSupport {
  private final CacheProperties cacheProperties;
  private final ObjectMapper objectMapper;

  @Bean("v2CacheManager")
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration defaultCacheConfig = createConfiguration();
    RedisCacheManager.RedisCacheManagerBuilder cacheManagerBuilder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(defaultCacheConfig);

    if (cacheProperties.getRedis().isEnableStatistics()) {
      cacheManagerBuilder.enableStatistics();
    }
    Set<String> cacheNames = new HashSet<>();
    cacheNames.add("carrier");

    // 对每个缓存空间应用不同的配置
    Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
    configMap.put("carrier", defaultCacheConfig.entryTtl(Duration.ofSeconds(300)).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(Carrier.class))));

    RedisCacheManager cacheManager = cacheManagerBuilder
      .initialCacheNames(cacheNames)
      .withInitialCacheConfigurations(configMap)
      .build();
    return cacheManager;
  }

  private RedisCacheConfiguration createConfiguration() {
    CacheProperties.Redis redisProperties = cacheProperties.getRedis();
    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
    // 设置缓存管理器管理的缓存的默认过期时间
    defaultCacheConfig = defaultCacheConfig
      // 设置 key为string序列化
      .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
      // 设置value为json序列化
      .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));

    if (redisProperties.getTimeToLive() != null) {
      defaultCacheConfig = defaultCacheConfig.entryTtl(redisProperties.getTimeToLive());
    }
    if (redisProperties.getKeyPrefix() != null) {
//      defaultCacheConfig = defaultCacheConfig.prefixCacheNameWith(redisProperties.getKeyPrefix());
      defaultCacheConfig = defaultCacheConfig.computePrefixWith(name -> name + ":" + redisProperties.getKeyPrefix() + ":");
    }
    // 不缓存空值
    if (!redisProperties.isCacheNullValues()) {
      defaultCacheConfig = defaultCacheConfig.disableCachingNullValues();
    }
    if (!redisProperties.isUseKeyPrefix()) {
      defaultCacheConfig = defaultCacheConfig.disableKeyPrefix();
    }
    return defaultCacheConfig;
  }
}
