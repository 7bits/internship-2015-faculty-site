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
                              @RequestParam(value = "miniContent", required = false)String miniContent,
                              @RequestParam(value = "id", required = false)Long id,
                              Model model) {
        ContentDescription res;
        if (id == null || id < 1) {
            res = createContent(title, content, miniContent, type);
        }
        else {
            res = updateContent(id, title, content, miniContent, type);
        }
        if (res != null) {
            model.addAttribute("content", res.getDescription());
            model.addAttribute("title", res.getTitle());
            model.addAttribute("type", res.getType());
            model.addAttribute("miniContent", res.getMiniContent());
            model.addAttribute("id", res.getId());
            LOG.info("Record: " + res.toString());
        }
        return "home/edit_content";
    }

    private ContentDescription updateContent(Long id, String title, String content, String miniContent, String type){
        try {
            if ((id == null || id < 1) || (title == null || content == null))
                return null;
            ContentDescription page = contentOfPagesService.getPageById(id);
            if (!type.isEmpty() && !content.isEmpty()) {
                page.setDescription(content);
                page.setMiniContent(miniContent);
                page.setTitle(title);
                page.setType(type);
            }
            contentOfPagesService.updatePage(page);
            return page;
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return null;
    }

    private ContentDescription createContent(String title, String content, String type, String miniContent){
        Long id;
        try {
            if (title == null || content == null || type == null)
                return null;
            id = contentOfPagesService.saveContentOfPage(title, content, miniContent, type);
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
