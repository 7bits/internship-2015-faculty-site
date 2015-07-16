package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.web.domain.ImageDescriptionModel;
import it.sevenbits.FacultySite.web.service.ImageDescriptionService;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import it.sevenbits.FacultySite.web.domain.ImageDescriptionForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="NewsType", required = false) String newsType, @RequestParam(value="NewsId", required = false) String newsId, @ModelAttribute ImageDescriptionForm form, Model model) {
        LOG.info("News type param: " + newsType);
        LOG.info("News id param: " + newsId);

        try {
            List<ImageDescriptionModel> images = (new ImageDescriptionService()).getAllImages();
            for (ImageDescriptionModel image : images)
                LOG.info(image.toString());
        }
        catch (ServiceException e){
            LOG.info(e.getMessage());
        }

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
