package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {
    private static Logger LOG = Logger.getLogger(ContentController.class);

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam(value = "main-content", required = false) String mainContent, Model model) {
        return "home/main";
    }

    @RequestMapping(value = "/edit_content")
    public String editContent(@RequestParam(value = "content", required = false)String content,
                              @RequestParam(value = "title", required = false)String title,
                              @RequestParam(value = "type", required = false)String type,
                              @RequestParam(value = "id", required = false)Long id,
                              Model model) {
        ContentDescription res;
        if (id == null || id < 1) {
            res = createContent(title, content, type);
        }
        else {
            res = updateContent(id, title, content, type);
        }
        if (res != null) {
            model.addAttribute("content", res.getDescription());
            model.addAttribute("title", res.getTitle());
            model.addAttribute("type", res.getType());
            model.addAttribute("id", res.getId());
        }
        return "home/edit_content";
    }

    private ContentDescription updateContent(Long id, String title, String content, String type){
        try {
            if ((id == null || id < 1) || (title == null || content == null))
                return null;
            ContentDescription page = contentOfPagesService.getPageById(id);
            page.setDescription(content);
            page.setTitle(title);
            page.setType(type);
            contentOfPagesService.updatePage(page);
            return contentOfPagesService.getPageById(id);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return null;
    }

    private ContentDescription createContent(String title, String content, String type){
        Long id;
        try {
            id = contentOfPagesService.saveContentOfPage(title, content, type);
            if (id == null || id < 1)
                return null;
            return contentOfPagesService.getPageById(id);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return null;
    }

}
