package com.dipanshu.habitTracker.checkup;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheckup {
    @GetMapping("/healthCheck")
    public String healthcheck(){
      return  "all good";
    }
}
