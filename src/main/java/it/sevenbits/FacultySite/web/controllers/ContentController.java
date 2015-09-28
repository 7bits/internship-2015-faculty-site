package it.sevenbits.FacultySite.web.controllers;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContentController {
    private static Logger LOG = Logger.getLogger(ContentController.class);




    @RequestMapping(value="/upload", method=RequestMethod.GET)
    String handleFileUpload(){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        return "redirect:/main";//return "home/upload";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam(value = "files", required = false) List<MultipartFile> files, Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
//
//        List<String> toOut = new ArrayList<>();
//        try{
//            toOut = contentOfPagesService.uploadFiles(files);
//        }
//        catch (ServiceException e){
//            LOG.error(e);
//        }
//        model.addAttribute("paths", toOut);
        return "redirect:/main";//return "home/upload";
    }





    @RequestMapping(value = "/edit_content")
    public String editContent(@RequestParam(value = "content", required = false)String content,
                              @RequestParam(value = "title", required = false)String title,
                              @RequestParam(value = "type", required = false)String type,
                              @RequestParam(value = "mini-content", required = false)String miniContent,
                              @RequestParam(value = "image-link", required = false)String imageLink,
                              @RequestParam(value = "id", required = false) Long id,
                              @RequestParam(value = "publish", required = false, defaultValue = "false")Boolean publish,
                              @RequestParam(value = "create", required = false) Boolean create,
                              @RequestParam(value = "redact", required = false) Boolean redact,
                              @RequestParam(value = "delete", required = false) Boolean delete,
                              @RequestParam(value = "redactId", required = false) Long redactId,
                              @RequestParam(value = "deleteId", required = false) Long deleteId,
                              @RequestParam(value = "createType", required = false)String createType,
                              @RequestParam(value = "deleteType", required = false)String deleteType,
                              Model model) {
//        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
//            return "redirect:/main";
//        if (redact != null && redact){
//            deleteId = null;
//            id = redactId;
//        }
//        if (create != null && create){
//            id = null;
//            deleteId = null;
//            type = createType;
//        }
//        ContentDescription res = contentOfPagesService.editContentAction(imageLink,
//                deleteId,
//                type,
//                id,
//                title,
//                content,
//                miniContent,
//                publish);
//        if (res == null) {
//            if (deleteType.contains("News"))
//                return "redirect:/news";
//            else
//                return "redirect:/gallery";
//        }
//        model.addAttribute("content", res.getDescription());
//        model.addAttribute("title", res.getTitle());
//        model.addAttribute("type", res.getType());
//        model.addAttribute("miniContent", res.getMiniContent());
//        model.addAttribute("imageLink", res.getImageLink());
//        model.addAttribute("publish", res.getPublish());
//        model.addAttribute("id", res.getId());
        return "redirect:/main";//return "home/edit_content";
    }



    @RequestMapping(value = "/hidden_content")
    public String hiddenContent(@RequestParam(value="current", required = false) Long current,
                                @RequestParam(value="page", required = false) String page,
                                Model model) {
//        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
//            return "redirect:/main";
//        model.addAttribute("title", "Скрытые записи");
//        try {
//            model = newsService.constructData(current, page, null, false, NewsController.countOnPage, model);
//        }
//        catch (ServiceException e){
//            LOG.error(e.getMessage(), e);
//        }
        return "home/news";
    }

    @RequestMapping(value = "/visible_content")
    public String visibleContent(@RequestParam(value="current", required = false) Long current,
                                 @RequestParam(value="page", required = false) String page,
                                 Model model) {
//        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
//            return "redirect:/main";
//        model.addAttribute("title", "Все записи");
//        try{
//            model = newsService.constructData(current, page, null, null, NewsController.countOnPage, model);
//        }
//        catch (ServiceException e){
//            LOG.error(e.getMessage(), e);
//        }
        return "home/news";
    }

}
