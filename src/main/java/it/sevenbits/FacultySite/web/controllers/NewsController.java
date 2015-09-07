package it.sevenbits.FacultySite.web.controllers;

import com.google.gson.JsonObject;
import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.ServiceException;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import it.sevenbits.FacultySite.web.service.contentOfPages.NewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        try {
            model = newsService.constructData(current, page, null, true, countOnPage, model);
        }
        catch (Exception e){
            LOG.error(e);
        }
        return "home/news";
    }






}