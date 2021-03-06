package com.grossmann.apps.homeorganizer.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dummy")
@CrossOrigin(
  originPatterns = ["http://localhost:*"]
)
class DummyController {
  @GetMapping("/hello")
  fun hello() : String  {
    return "Hello, world! new"
  }
}
