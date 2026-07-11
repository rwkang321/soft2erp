package com.soft2erp.erp.common.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/")
    public String index() {
        return "home/index";
    }
}
