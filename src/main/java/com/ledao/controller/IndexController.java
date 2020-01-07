package com.ledao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页Controller
 *
 * @author LeDao
 * @company
 * @create 2020-01-07 22:16
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/login.html";
    }
}
