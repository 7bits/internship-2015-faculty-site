package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
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
            List<AlbumDescription> albums = imageDescriptionService.getAllAlbumsWithUniqueLink();
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

    @RequestMapping(value="/uploadImage", method=RequestMethod.GET)
    String handleFileUpload(){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
        return "home/uploadImage";
    }

    @RequestMapping(value="/uploadImage", method= RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(@RequestParam(value = "files", required = false) List<MultipartFile> files){
        String toOut = "";
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "<? header '/main';?>";
        for (MultipartFile file : files){
            toOut += downloadImage(file) + "<p>";
        }
        toOut += "<p><a href='/upload'>Загрузить ещё</a>";
        return toOut;
    }

    public String downloadImage(MultipartFile file){
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
