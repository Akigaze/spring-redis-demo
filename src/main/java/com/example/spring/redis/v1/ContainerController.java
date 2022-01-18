package com.example.spring.redis.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/container")
public class ContainerController {
  private final ContainerService containerService;

  @PostMapping("")
  public ContainerEntity create(@RequestBody ContainerEntity container){
    return containerService.save(container);
  }

  @GetMapping("/{refNum}")
  public ContainerEntity get(@PathVariable String refNum){
    return containerService.get(refNum);
  }
}
