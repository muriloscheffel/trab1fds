package com.t1fsd.trab1fds;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {

  @GetMapping
  public Barca getBarca() {
    Barca barca = new Barca(new RelogioRealmpl(), 50);
    barca.defineAssento("F02A12");
    return null;
  }
}
