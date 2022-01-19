package com.example.spring.redis.v1;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ContainerEntity {
  private String refNum;
  private String tenantId;
  private String scac;
  private String bookingNumber;
  private String containerNumber;
  private Instant createdTime;
}
