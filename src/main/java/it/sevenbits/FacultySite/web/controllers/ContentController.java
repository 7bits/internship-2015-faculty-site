package it.sevenbits.FacultySite.web.controllers;

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
        if (content == null)
            content = "";
        if (title == null)
            title = "";
        if (type == null)
            type = "";
        if (!content.isEmpty() && !title.isEmpty())
            try {
                id = contentOfPagesService.saveContentOfPage(title, content, type);
            }
            catch (Exception e){
                LOG.error(e.getMessage(), e);
            }
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        return "home/edit_content?id=#{id}";
    }


    @RequestMapping(value = "/save-data")
    public String saveData(@RequestParam(value="content", required = false) String content, Model model) {
        LOG.info("Content : \n" + content);
        return "home/main";
    }

}
