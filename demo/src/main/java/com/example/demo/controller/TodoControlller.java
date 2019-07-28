package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TodoControlller {
    @GetMapping
    public String init() {
        return "todotop";
    }

    @RequestMapping("/taskAdd")
    public String add() {
        return "todoadd";
    }
}
