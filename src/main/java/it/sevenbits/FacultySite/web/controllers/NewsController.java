package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NewsController {
    private static Logger LOG = Logger.getLogger(NewsController.class);

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="News", required = false) String newsType,
                       @RequestParam(value="NewsId", required = false) Long newsId,
                       @ModelAttribute ImageDescriptionForm form,
                       Model model) {
        model.addAttribute("title", "Новости ОмГУ");
        model = constructNews(newsType, newsId, form, true, model, contentOfPagesService);
        model.addAttribute("mainInfo", new ArrayList<>());
        return "home/news";
    }

    public static Model constructNews(String newsType, Long newsId, ImageDescriptionForm form, Boolean publish, Model model, ContentOfPagesService contentOfPagesService){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            model.addAttribute("canCreate", true);
            model.addAttribute("createType", "News:");
        }
        if (newsId != null){
            if (newsId < 1)
                return model;
            ContentDescription news = getContentById(newsId, contentOfPagesService);

            if (news == null)
                return model;
            model.addAttribute("description", news.getDescription());
        }
        else{
            newsType = (newsType == null ? "" : newsType);
            List<ContentDescriptionModel> content = getContentByType("News:"+newsType, publish, (long)1, (long)1, contentOfPagesService);
            model.addAttribute("content", content);
        }
        return model;
    }

    public static ContentDescription getContentById(Long id, ContentOfPagesService contentOfPagesService){
        try{
            return contentOfPagesService.getPageById(id);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return new ContentDescription();
    }

    public static List<ContentDescriptionModel> getContentByType(String type, Boolean publish, Long start, Long count, ContentOfPagesService contentOfPagesService){
        try{
            if (type == null){
                type = "";
            }
            type = "%" + type + "%";
            Long sum = contentOfPagesService.getSumOfPages(type, publish);
            if (start>sum){
                return new ArrayList<>();
            }
            if (sum-start < count) {
                count = sum-start;
            }
            start = sum-start-count+1;//Рассчитываем с конца - получается, если start = 2, sum = 10, count = 15,
                                    // то взяты будут записи с 14 до 4 (10 записей, начиная со второй с конца)
            return contentOfPagesService.getPagesWhichContainTypeIsPublishWithBoundaries(type, publish, start, count);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
