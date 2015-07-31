package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="News", required = false) String newsType, @RequestParam(value="NewsId", required = false) Long newsId, @ModelAttribute ImageDescriptionForm form, Model model) {
        LOG.info("News type param: " + newsType);
        LOG.info("News id param: " + newsId);

        if (newsId != null){
            ContentDescription news = getContentById(newsId);
            if (news == null)
                return "redirect:/news?News=All";
            model.addAttribute("title", news.getTitle());
            model.addAttribute("description", news.getDescription());
            model.addAttribute("do", "id");
        }
        else{
            List<ContentDescriptionModel> news = getContentByType(newsType);
            List<Long> ids = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            List<String> img_links = new ArrayList<>();
            List<String> dates = new ArrayList<>();
            for (ContentDescriptionModel tmp : news){
                ids.add(tmp.getId());
                titles.add(tmp.getTitle());
                img_links.add(tmp.getImageLink());
                dates.add(tmp.getCreatingDate());
            }
            model.addAttribute("ids", ids);
            model.addAttribute("titles", titles);
            model.addAttribute("img_links", img_links);
            model.addAttribute("dates", dates);
            model.addAttribute("do", "type");
        }
        return "home/news";
    }

    public ContentDescription getContentById(Long id){
        try{
            return contentOfPagesService.getPageById(id);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return new ContentDescription();
    }

    public List<ContentDescriptionModel> getContentByType(String type){
        try{
            if (type == null || type.equals("All"))
                return contentOfPagesService.getPagesWhichContainType("News:%");
            return contentOfPagesService.getPagesByType("News:"+type);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return new ArrayList<>();
    }

}
