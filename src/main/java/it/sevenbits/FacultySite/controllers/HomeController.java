package it.sevenbits.FacultySite.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "home/index";
    }

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="NewsType") String newsType, Model model) {
        LOG.info("Search param: " + newsType);
        model.addAttribute("newsType", newsType);
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
