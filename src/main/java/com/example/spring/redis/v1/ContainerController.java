package com.example.spring.redis.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/container")
public class ContainerController {
  private final ContainerService containerService;

  @PostMapping("")
  public ContainerEntity create(@RequestBody ContainerEntity container, @RequestParam(defaultValue = "0") int secondsToLive) {
    return containerService.save(container, secondsToLive);
  }

  @GetMapping("/{refNum}")
  public ContainerEntity get(@PathVariable String refNum) {
    return containerService.get(refNum);
  }

  @DeleteMapping("/{refNum}")
  public Boolean delete(@PathVariable String refNum) {
    return containerService.delete(refNum);
  }

  @PutMapping("")
  public ContainerEntity delete(@RequestBody ContainerEntity container) {
    return containerService.update(container);
  }
}
