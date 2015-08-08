package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

@Controller
public class ContentController {
    private static Logger LOG = Logger.getLogger(ContentController.class);

    @Autowired
    ContentOfPagesService contentOfPagesService;


    static public final double relationSide = 1/1;
    static public final double miniImgWidth = 240;
    static public final double miniImgHeight = miniImgWidth/relationSide;

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    String handleFileUpload(){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        return "home/upload";
    }

    public static BufferedImage resizeImage(BufferedImage src, Double destWidth, Double destHeight){
        src = cutImageToSquare(src, null, null, null, null);
        src = scaleToSize(src, destWidth, destHeight);
        return src;
    }

    public static BufferedImage cutImageToSquare(BufferedImage src, Double startX, Double startY, Double cutW, Double cutH){
        double w = src.getWidth();
        double h = src.getHeight();
        if (cutW == null) {
            cutW = w;
        }
        if (cutH == null){
            cutH = h;
        }
        if (cutW>cutH)
            cutW = cutH;
        else
            cutH = cutW;
        if (startX == null) {
            startX = (w - cutW) / 2;
        }
        if (startY == null){
            startY = (h-cutH)/2;
        }
        src = src.getSubimage(startX.intValue(), startY.intValue(), cutW.intValue(), cutH.intValue());
        return src;
    }

    public static BufferedImage scaleToSize(BufferedImage src, Double w, Double h){
        BufferedImage res = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        AffineTransform scales = new AffineTransform();
        scales.scale(w/src.getWidth(), h/src.getHeight());
        AffineTransformOp resScale = new AffineTransformOp(scales, AffineTransformOp.TYPE_BILINEAR);
        res = resScale.filter(src, res);
        res = cutImageToSquare(res, 0.0, 0.0, w, h);
        return res;
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam(value = "file", required = false) MultipartFile file){
        String toOut = "";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "<? header '/main';?>";
        if (file != null && !file.isEmpty()) {
            try {
                String name = generateName(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];

                byte[] bytes = file.getBytes();
                String bigi = "bigi/";
                String mini = "mini/";
                //String path = "/home/internship-2015-faculty-site/src/main/resources/public/img/";//for server
                String path = "src/main/resources/public/img/";
                File src = new File(path+bigi+name);
                File miniFile = new File(path+mini+name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(src));
                stream.write(bytes);
                stream.close();
                BufferedImage srcImg = ImageIO.read(src);
                BufferedImage miniImg = resizeImage(srcImg, miniImgWidth, miniImgHeight);
                if (miniFile.createNewFile()) {
                    ImageIO.write(miniImg, type, miniFile);
                }
                toOut += "Ссылка на загруженную картинку в большом размере:<p> /img/"+bigi+name + "<p>";
                toOut += "<img src='/img/"+bigi+name + "'></img>";
                toOut += "Ссылка на миниатюру:<p> /img/"+mini+name + "<p>";
                toOut += "<img src='/img/"+mini+name + "'></img>";
            } catch (Exception e) {
                toOut +=  ("Вам не удалось загрузить " + file.getName() + ": " + e.getMessage());
            }
        }
        toOut += "<p><a href='/upload'>Загрузить ещё</a>";
        return toOut;
    }

    private String generateName(String input){
        String name = input;
        String partsOfName[] = name.split("\\.");
        name = "." + partsOfName[partsOfName.length-1];
        name = UUID.randomUUID().toString() + name;
        return name;
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
        ContentDescription res;
        if (id == null || id < 1) {
            res = createContent(title, content, miniContent, imageLink, type, publish);
        }
        else {
            res = updateContent(id, title, content, miniContent, imageLink, type, publish);
        }
        if (res != null) {
            model.addAttribute("content", res.getDescription());
            model.addAttribute("title", res.getTitle());
            model.addAttribute("type", res.getType());
            model.addAttribute("miniContent", res.getMiniContent());
            model.addAttribute("imageLink", res.getImageLink());
            model.addAttribute("publish", res.getPublish());
            model.addAttribute("id", res.getId());
            LOG.info("Record: " + res.toString());
        }
        else{
            model.addAttribute("content", content);
            model.addAttribute("title", title);
            model.addAttribute("type", type);
            model.addAttribute("miniContent", miniContent);
            model.addAttribute("imageLink", imageLink);
            model.addAttribute("publish", publish);
            model.addAttribute("id", id);
        }
        return "home/edit_content";
    }

    private ContentDescription updateContent(Long id, String title, String content, String miniContent, String imageLink, String type, Boolean publish){
        try {
            if ((id == null || id < 1) || (title == null || content == null || content.isEmpty() ))
                return null;
            ContentDescription page = contentOfPagesService.getPageById(id);
            if (!type.isEmpty() && !content.isEmpty()) {
                page.setDescription(content);
                page.setMiniContent(miniContent);
                page.setTitle(title);
                page.setType(type);
                page.setPublish(publish);
                page.setImageLink(imageLink);
            }
            contentOfPagesService.updatePage(page);
            return page;
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/hidden_content")
    public String hiddenContent(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        model.addAttribute("title", "Скрытые записи");
        return NewsController.constructNews("All", null, null, false, model, contentOfPagesService);
    }

    @RequestMapping(value = "/visible_content")
    public String visibleContent(Model model) {
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        model.addAttribute("title", "Все записи");
        return NewsController.constructNews("All", null, null, null, model, contentOfPagesService);
    }

    private ContentDescription createContent(String title, String content, String miniContent, String imageLink, String type, Boolean publish){
        Long id;
        try {
            if (title == null || content == null || content.isEmpty() || type == null || miniContent == null) {
                LOG.error("Some of this is null: title || content || type || miniContent");
                return null;
            }
            id = contentOfPagesService.saveContentOfPage(title, content, imageLink, miniContent, type, publish);
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
