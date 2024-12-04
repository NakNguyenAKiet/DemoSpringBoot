package com.NAKexample.demoSpring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Products")
public class ProductController {
    @GetMapping("")
    List<String> getAllProducts(){
        return List.of("iphone","Samsung");
    }
}
