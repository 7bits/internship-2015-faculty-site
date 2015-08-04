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
    public String editContent(@RequestParam(value = "content", required = false)String content, @RequestParam(value = "title", required = false)String title, Model model) {
        LOG.info("Content: \n" + content);
        return "home/edit_content?editor="+content+"&title="+title;
    }


    @RequestMapping(value = "/save-data")
    public String saveData(@RequestParam(value="content", required = false) String content, Model model) {
        LOG.info("Content : \n" + content);
        return "home/main";
    }

}
