package com.example.spring.redis.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarrierService {
  private final CarrierRepository carrierRepository;

  @Cacheable(cacheNames = "carrier", cacheManager = "v2CacheManager", unless = "#result == null")
  public Carrier get(String scac) {
    return carrierRepository.findById(scac).orElse(null);
  }
}
