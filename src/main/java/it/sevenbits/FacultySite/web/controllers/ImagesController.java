package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.gallery.ImageDescriptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ImagesController {
    @Autowired
    ImageDescriptionService imageDescriptionService;

    private static Logger LOG = Logger.getLogger(ImagesController.class);

    @RequestMapping(value = "/gallery")
    public String gallery(Model model) {
        try {
            List<AlbumDescription> albums = imageDescriptionService.getAllAlbums();
            List<List<ImageFromAlbumDescriptionModel>> images = new ArrayList<>();
            for (AlbumDescription album : albums) {
                images.add(imageDescriptionService.getImagesFromAlbum(album.getId()));
            }
            List<String> pagination = new ArrayList<>();
            model.addAttribute("albums", albums);
            model.addAttribute("images", images);
            model.addAttribute("pagination", pagination);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return "home/gallery";
    }


    @RequestMapping(value="/edit_album")
    String editAlbum(){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        return "redirect:/updateAlbum";
    }

    @RequestMapping(value="/updateAlbum", method= RequestMethod.POST)
    public @ResponseBody
    String updateAlbum(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                       @RequestParam(value = "id", required = false) Long id,
                       @RequestParam(value = "isHead", required = false)List<Long> isHeadIDs,
                       @RequestParam(value = "toDelete", required = false)List<Long> toDeleteIDs,
                       @RequestParam(value = "title", required = false)String title,
                       @RequestParam(value = "description", required = false)String description){
        String toOut = "";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "<? header '/main';?>";
        AlbumDescription album = new AlbumDescription(id, title, description);
        if (album.getId() == null){
            try {
                imageDescriptionService.saveAlbum(album);
            }
            catch (Exception e){
                LOG.error(e.getMessage());
            }
        }
        for (MultipartFile file : files){
            if (file.getOriginalFilename().isEmpty()) {
                continue;
            }
            toOut += downloadImage(file, album.getId()) + "<p>";
        }
        toOut += "<? header ('/updateAlbum?id="+album.getId()+"');?>";
        return toOut;
    }

    @RequestMapping(value="/updateAlbum", method= RequestMethod.GET)
    public String handleFileUpload(@RequestParam(value = "id", required = false) Long id, Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "<? header '/main';?>";
        try{
            if (id == null || id < 1) {
                model.addAttribute("album", imageDescriptionService.getAlbumById(id));
                model.addAttribute("photos", imageDescriptionService.getImagesFromAlbum(id));
                return "home/edit-album";
            }
            AlbumDescription album = imageDescriptionService.getAlbumById(id);
            List<ImageFromAlbumDescriptionModel> images = imageDescriptionService.getImagesFromAlbum(id);
            model.addAttribute("album", album);
            model.addAttribute("photos", images);
            return "home/edit-album";
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return "home/edit-album";
    }

    public String downloadImage(MultipartFile file, Long albumId){
        String toOut = "";
        if (file != null && !file.isEmpty()) {
            try {
                String name = ContentController.generateName(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];

                byte[] bytes = file.getBytes();
                String bigi = "gallery-page-gallery-photo/bigi/";
                String mini = "gallery-page-gallery-photo/mini/";
                //String path = "/home/internship-2015-faculty-site/src/main/resources/public/img/";//for server
                String path = "src/main/resources/public/img/";
                File src = new File(path+bigi+name);
                File miniFile = new File(path+mini+name);
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(src));
                stream.write(bytes);
                stream.close();
                ImageDescriptionForm image = new ImageDescriptionForm();
                image.setLink(name);
                image.setAlbum(albumId);
                imageDescriptionService.saveImage(image);
                BufferedImage srcImg = ImageIO.read(src);
                BufferedImage miniImg = ImageDescriptionService.resizeImage(srcImg, null, null);
                if (miniFile.createNewFile()) {
                    ImageIO.write(miniImg, type, miniFile);
                }
//                toOut += "Ссылка на загруженную картинку в большом размере:<p> /img/"+bigi+name + "<p>";
//                toOut += "<img src='/img/"+bigi+name + "'></img>";
//                toOut += "Ссылка на миниатюру:<p> /img/"+mini+name + "<p>";
//                toOut += "<img src='/img/"+mini+name + "'></img>";
            } catch (Exception e) {
                toOut +=  ("Вам не удалось загрузить " + file.getName() + ": " + e.getMessage());
            }
        }
        return toOut;
    }

}
