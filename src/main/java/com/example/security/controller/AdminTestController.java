package com.example.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminTestController {
    @RequestMapping("/home")
    @ResponseBody
    public String productInfo() {
        return " admin home page ";
    }
}