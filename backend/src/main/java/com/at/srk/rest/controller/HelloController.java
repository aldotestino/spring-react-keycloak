package com.at.srk.rest.controller;

import com.at.srk.rest.controller.dto.SimpleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping()
    public SimpleResponse hello() {
        return new SimpleResponse("Hello World!");
    }
}
