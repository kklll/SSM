package com.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/message")
public class GuoJIHUA {

    @RequestMapping("myerror2")
    public String geterror() {
        return "guojihua";
    }
}
