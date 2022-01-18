package com.example.spring.redis.v1;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainerEntity {
  private String refNum;
  private String tenantId;
  private String scac;
  private String bookingNumber;
  private String containerNumber;
}
