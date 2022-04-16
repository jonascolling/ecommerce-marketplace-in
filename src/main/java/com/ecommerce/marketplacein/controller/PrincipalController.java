package com.ecommerce.marketplacein.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class PrincipalController {

    @GetMapping
    public void test() {
        System.out.println("dsd");
    }

}
