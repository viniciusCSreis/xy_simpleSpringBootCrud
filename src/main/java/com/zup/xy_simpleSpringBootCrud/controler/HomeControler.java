package com.zup.xy_simpleSpringBootCrud.controler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeControler {
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
}