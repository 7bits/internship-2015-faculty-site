package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.core.service.contentOfPages.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NewsController {
    private static Logger LOG = Logger.getLogger(NewsController.class);

    public final static Integer countOnPage = 4;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="current", required = false) Long current,
                       @RequestParam(value="page", required = false) String page,
                       Model model) {
        model.addAttribute("title", "Новости ОмГУ");
        model.addAttribute("type", "News:");
        try {
            model = newsService.constructData(current, page, null, true, countOnPage, model);
        }
        catch (Exception e){
            LOG.error(e);
        }
        return "home/news";
    }

}