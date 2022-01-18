package com.example.spring.redis.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carrier")
public class CarrierController {
  private final CarrierService carrierService;

  @GetMapping("/{scac}")
  public Carrier get(@PathVariable String scac) {
    return carrierService.get(scac);
  }
}
