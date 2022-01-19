package com.example.spring.redis.v1;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class KeyPrefixStringRedisSerializer extends StringRedisSerializer {
  private String prefix;
  private static final String SEPARATOR = ":";

  public KeyPrefixStringRedisSerializer(String prefix) {
    Assert.hasText(prefix, "prefix should not be null or blank");
    this.prefix = prefix.endsWith(SEPARATOR) ? prefix : prefix + SEPARATOR;
  }

  @Override
  public String deserialize(@Nullable byte[] bytes) {
    String key = super.deserialize(bytes);
    return key == null ? null : key.replaceFirst(prefix, "");
  }

  @Override
  public byte[] serialize(@Nullable String key) {
    return super.serialize(key == null ? prefix :prefix + key);
  }
}
