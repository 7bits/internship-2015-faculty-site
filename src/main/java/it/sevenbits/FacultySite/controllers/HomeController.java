package it.sevenbits.Example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping(value = "/")
    public String index() {
        return "home/index";
    }

    @RequestMapping(value = "/news")
    public String news() {
        return "home/news";
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "home/main";
    }

    @RequestMapping(value = "/applicants")
    public String applicants() {
        return "home/applicants";
    }

    @RequestMapping(value = "/gallery")
    public String gallery() {
        return "home/gallery";
    }

    @RequestMapping(value = "/contacts")
    public String contacts() {
        return "home/contacts";
    }
}
