package com.example.spring.redis.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cmn_crr")
public class Carrier {

  @Id
  private String scac;

  @Column(name = "nme")
  private String name;
}
