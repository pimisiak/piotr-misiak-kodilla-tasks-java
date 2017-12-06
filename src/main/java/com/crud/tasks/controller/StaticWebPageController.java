package com.crud.tasks.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StaticWebPageController {
    @RequestMapping("/")
    public String index(final Map<String, Object> model) {
        model.put("variable", "My Thymeleaf variable");
        model.put("two", 2);
        return "index";
    }
}
