package com.soft2erp.erp.common.dev;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DevPageController {

    @GetMapping("/admin/dev")
    public String index() {
        return "admin/dev/index";
    }
}

