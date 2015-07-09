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
        return "redirect:/main";
    }

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="NewsType", required = false) String newsType, @RequestParam(value="NewsId", required = false) String newsId, Model model) {
        LOG.info("News type param: " + newsType);
        LOG.info("News id param: " + newsId);
        if (newsType == null)
            newsType = "All-news";
        model.addAttribute("newsType", newsType);
        try {
            if (newsId == null || Integer.parseInt(newsId) < 1)
                newsId = "0";
        }
        catch (Exception e){
            LOG.info("NewsId is incorrect: " + newsId);
            newsId = "0";
        }
        model.addAttribute("newsId", newsId);
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
