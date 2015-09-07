package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.service.ServiceException;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import it.sevenbits.FacultySite.web.service.gallery.ImageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

@Controller
public class ContentController {
    private static Logger LOG = Logger.getLogger(ContentController.class);


    @Autowired
    ContentOfPagesService contentOfPagesService;


    @RequestMapping(value="/upload", method=RequestMethod.GET)
    String handleFileUpload(){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        return "home/upload";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam(value = "files", required = false) List<MultipartFile> files, Model model){
        String toOut = "";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files){
            try {
                fileNames.add(contentOfPagesService.uploadFile(file));
            }
            catch (ServiceException e){
                LOG.error(e);
            }
        }
        model.addAttribute("request", toOut);
        return "home/upload";
    }



    public String editContentAction(Boolean create,
                                    Boolean redact,
                                    Boolean delete,
                                    Long redactId,
                                    Long deleteId,
                                    String createType,
                                    Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        if (create != null && create) {
            model.addAttribute("type", createType);
            return "home/edit_content";
        }
        if (redact != null && redact && redactId>0){
            try{
                ContentDescription res = contentOfPagesService.getPageById(redactId);
                model.addAttribute("content", res.getDescription());
                model.addAttribute("title", res.getTitle());
                model.addAttribute("type", res.getType());
                model.addAttribute("miniContent", res.getMiniContent());
                model.addAttribute("imageLink", res.getImageLink());
                model.addAttribute("publish", res.getPublish());
                model.addAttribute("id", res.getId());
                LOG.info("Record: " + res.toString());
                return "home/edit_content";
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        if (delete != null && delete && deleteId > 0){
            try{
                ContentDescription res = contentOfPagesService.getPageById(deleteId);
                LOG.info("Record: " + res.toString());
                String type = res.getType();
                contentOfPagesService.removePageById(res.getId());
                if (type.contains("News"))
                    return "redirect:/news";
                else
                    return "redirect:/gallery";
            }
            catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        return "home/edit_content";
    }

    @RequestMapping(value = "/edit_content")
    public String editContent(@RequestParam(value = "content", required = false)String content,
                              @RequestParam(value = "title", required = false)String title,
                              @RequestParam(value = "type", required = false)String type,
                              @RequestParam(value = "mini-content", required = false)String miniContent,
                              @RequestParam(value = "image-link", required = false)String imageLink,
                              @RequestParam(value = "id", required = false)Long id,
                              @RequestParam(value = "publish", required = false, defaultValue = "false")Boolean publish,
                              @RequestParam(value = "create", required = false)Boolean create,
                              @RequestParam(value = "redact", required = false)Boolean redact,
                              @RequestParam(value = "delete", required = false)Boolean delete,
                              @RequestParam(value = "redactId", required = false)Long redactId,
                              @RequestParam(value = "deleteId", required = false)Long deleteId,
                              @RequestParam(value = "createType", required = false)String createType,
                              Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        if (imageLink != null && imageLink.isEmpty())
            imageLink = "/img/lost-page.png";
        if ((create != null && create) || (redact != null && redact) || (delete != null && delete))
            return editContentAction(create, redact, delete, redactId, deleteId, createType, model);
        ContentDescription res = new ContentDescription();
        if (id == null || id < 1) {
            try {
                res = contentOfPagesService.createContent(title, content, miniContent, imageLink, type, publish);
            }
            catch (ServiceException e){
                LOG.error(e);
            }
        }
        else {
            try {
                res = contentOfPagesService.updateContent(id, title, content, miniContent, imageLink, type, publish);
            }
            catch (ServiceException e){
                LOG.error(e);
            }
        }
        if (res != null) {
            content = res.getDescription();
            title = res.getTitle();
            type = res.getType();
            miniContent = res.getMiniContent();
            imageLink = res.getImageLink();
            publish = res.getPublish();
            id = res.getId();
            LOG.info("Record: " + res.toString());
        }
        model.addAttribute("content", content);
        model.addAttribute("title", title);
        model.addAttribute("type", type);
        model.addAttribute("miniContent", miniContent);
        model.addAttribute("imageLink", imageLink);
        model.addAttribute("publish", publish);
        model.addAttribute("id", id);
        return "home/edit_content";
    }



    @RequestMapping(value = "/hidden_content")
    public String hiddenContent(@RequestParam(value="current", required = false) Integer current,
                                @RequestParam(value="page", required = false) String page,
                                Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        model.addAttribute("title", "Скрытые записи");
        model = NewsController.constructData(current, page, null, false, model, contentOfPagesService);
        return "home/news";
    }

    @RequestMapping(value = "/visible_content")
    public String visibleContent(@RequestParam(value="current", required = false) Integer current,
                                 @RequestParam(value="page", required = false) String page,
                                 Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        model.addAttribute("title", "Все записи");
        model = NewsController.constructData(current, page, null, null, model, contentOfPagesService);
        return "home/news";
    }


    public static Model adminModelAttributes(Model model, String type, Long redactId, Long deleteId){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            model.addAttribute("createType", type);
            model.addAttribute("canCreate", true);
            if (redactId != null && redactId > 0) {
                model.addAttribute("redactId", redactId);
                model.addAttribute("canRedact", true);
            }
            if (redactId != null && redactId > 0) {
                model.addAttribute("deleteId", deleteId);
                model.addAttribute("canDelete", true);
            }
        }
        return model;
    }

}
