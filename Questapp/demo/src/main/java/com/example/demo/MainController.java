package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

     @RequestMapping("/")
    public String index() {
        return "index"; // Returns the name of the view (index.html in this case)
    }
}

