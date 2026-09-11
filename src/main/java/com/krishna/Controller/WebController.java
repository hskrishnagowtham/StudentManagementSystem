package com.krishna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping({
            "/",
            "/login",
            "/register",
            "/dashboard"
    })
    public String index() {
        return "forward:/index.html";
    }
}