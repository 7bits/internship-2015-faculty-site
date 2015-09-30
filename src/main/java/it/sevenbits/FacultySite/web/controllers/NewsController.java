package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.web.domain.content.ContentForm;
import it.sevenbits.FacultySite.web.service.content.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NewsController {
    private static Logger LOG = Logger.getLogger(NewsController.class);

    public final static Integer countOnPage = 4;

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="currentPage", required = false) Integer currentPage,
                       Model model) {
        model.addAttribute("title", "Новости ОмГУ");
        try {
            List<ContentForm> contentForm = newsService.constructNewsPage(currentPage);
            model.addAttribute("content", contentForm);
        }
        catch (Exception e){
            LOG.error(e);
        }
        return "home/news";
    }

}