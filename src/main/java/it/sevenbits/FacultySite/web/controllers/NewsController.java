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

    public final static Integer countOnPage = 4;

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="current", required = false) Integer current,
                       @RequestParam(value="page", required = false) String page,
                       Model model) {
        model.addAttribute("title", "Новости ОмГУ");
        model = constructNews(current, page, true, model, contentOfPagesService);
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


    public static Model constructNews(Integer current, String page, Boolean publish, Model model, ContentOfPagesService contentOfPagesService){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            model.addAttribute("canCreate", true);
            model.addAttribute("createType", "News:");
        }
        if (page == null)
            page = "1";
        if (current == null)
            current = 1;
        Long sumOfNews = (long)0;
        try{
            sumOfNews = contentOfPagesService.getSumOfPages("News:%", true);

        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        if (page.equals("<")){
            if (current > 1){
                current--;
            }
        }
        else {
            if (page.equals(">")) {
                if (current < sumOfNews) {
                    current++;
                }
            } else{
                try {
                    current = Integer.parseInt(page);
                }
                catch (Exception e){
                    current = 1;
                    LOG.error(e.getMessage());
                }
            }
        }
        List<String> pagination = new ArrayList<>();
        Integer sumOfPages = sumOfNews.intValue()/countOnPage;
        if (sumOfPages < 1)
            sumOfPages = 1;
        current = sumOfPages;
        pagination = generatePagination(current, sumOfPages);
        Long start = sumOfNews - (current) * countOnPage;
        if (start < 1){
            start = (long)0;
        }
        Long end = countOnPage + start;
        if (end>sumOfNews)
            end = sumOfNews;
        List<ContentDescriptionModel> content = getContentByType("News:", publish, start, end, contentOfPagesService);
        model.addAttribute("content", content);
        model.addAttribute("pagination", pagination);
        model.addAttribute("current", current+"");
        model.addAttribute("sumOfPages", sumOfPages+"");
        return model;
    }

    public static List<String> generatePagination(Integer current, Integer sum){
        List<String> pagination = new ArrayList<>();
        pagination.add("<");
        pagination.add("1");
        if (current-1 > 2){
            pagination.add("...");
        }
        for (int i = current-2; i<current+3 && i <= sum; i++)
            if (i > 1) {
                pagination.add("" + i);
            }
        if (sum-current > 2){
            pagination.add("...");
        }
        if (!pagination.get(pagination.size()-1).equals(""+sum))
            pagination.add(""+sum);
        pagination.add(">");
        return pagination;
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
            start = sum-start-count;//Рассчитываем с конца - получается, если start = 2, sum = 10, count = 15,
                                    // то взяты будут записи с 14 до 4 (10 записей, начиная со второй с конца)
            //Переворачиваем последовательность
            List<ContentDescriptionModel> tmp = contentOfPagesService.getPagesWhichContainTypeIsPublishWithBoundaries(type, publish, start, count);
            List<ContentDescriptionModel> result = new ArrayList<>();
            for (int i = tmp.size()-1; i>=0; i--){
                result.add(tmp.get(i));
            }
            return result;
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}