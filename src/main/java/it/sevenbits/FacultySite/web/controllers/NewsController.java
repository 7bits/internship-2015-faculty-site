package it.sevenbits.FacultySite.web.controllers;

import com.google.gson.JsonObject;
import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
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

    public final static Integer countOnPage = 1;

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="News", required = false) String newsType,
                       @RequestParam(value="Page", required = false) Integer page,
                       Model model) {
        model.addAttribute("title", "Новости ОмГУ");
        model = constructNews(newsType, page, true, model, contentOfPagesService);
        model.addAttribute("mainInfo", new ArrayList<>());
        return "home/news";
    }

//    @RequestMapping(value = "/load_news", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> loadNews(@RequestParam(value="checked", required=false, defaultValue="1") Integer checked, @RequestParam(value="sum", required=false, defaultValue="1") Long count) {
//        Map<String, Object> result = new HashMap<>();
//        try {
//            result.put("content", contentOfPagesService.getPagesWhichContainTypeIsPublishWithBoundaries("News:%", true, (checked - 1) * count, checked * count));
//            result.put("countOnPage", 1);
//            result.put("countNews", contentOfPagesService.getSumOfPages("News:%", true));
//        }
//        catch (Exception e){
//            LOG.error(e.getMessage());
//        }
//        return result;
//    }
    //Was for Ajax


    public static Model constructNews(String newsType, Integer page, Boolean publish, Model model, ContentOfPagesService contentOfPagesService){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            model.addAttribute("canCreate", true);
            model.addAttribute("createType", "News:");
        }
        Long start = (long)(page-1) * countOnPage;
        newsType = (newsType == null ? "" : newsType);
        List<ContentDescriptionModel> content = getContentByType("News:"+newsType, publish, start, (long)countOnPage, contentOfPagesService);
        model.addAttribute("content", content);
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