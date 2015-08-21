package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageFromAlbumDescription;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.gallery.ImageService;
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
    ImageService imageDescriptionService;

    private static Logger LOG = Logger.getLogger(ImagesController.class);


    final static String bigi = "gallery-page-gallery-photo/bigi/";
    final static String mini = "gallery-page-gallery-photo/mini/";
    final static String path = "/home/internship-2015-faculty-site/src/main/resources/public/img/";//for server
    //final static String path = "src/main/resources/public/img/";

    @RequestMapping(value = "/gallery")
    public String gallery(Model model,
                          @RequestParam(value = "deleteId", required = false) Long deleteId) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            if (deleteId != null && deleteId >0){
                try {
                    List<ImageFromAlbumDescriptionModel> imagesFromDeletingAlbum = imageDescriptionService.getImagesFromAlbum(deleteId);
                    for (ImageFromAlbumDescriptionModel tmpImg : imagesFromDeletingAlbum)
                        try{
                            imageDescriptionService.removeImage(tmpImg.getId());
                        }
                        catch (Exception e){
                            LOG.error(e.getMessage());
                        }
                    imageDescriptionService.removeAlbum(deleteId);
                }
                catch (Exception e){
                    LOG.error(e.getMessage());
                }
            }
        }
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
    public String updateAlbum(@RequestParam(value = "files", required = false) List<MultipartFile> files,
                       @RequestParam(value = "id", required = false) Long id,
                       @RequestParam(value = "isHead", required = false)List<Long> isHeadIDs,
                       @RequestParam(value = "toDelete", required = false)List<Long> toDeleteIDs,
                       @RequestParam(value = "title", required = false)String title,
                       @RequestParam(value = "description", required = false)String description,
                       @RequestParam(value = "relation-side-width", required = false) Integer relationSideWidth,
                       @RequestParam(value = "relation-side-height", required = false) Integer relationSideHeight){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";

        Double relationSide = null;
        try{
            relationSide = ((double)relationSideWidth)/relationSideHeight;
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        AlbumDescription album = new AlbumDescription(id, title, description);
        if (album.getId() == null){
            try {
                imageDescriptionService.saveAlbum(album);
            }
            catch (Exception e){
                LOG.error(e.getMessage());
            }
        }
        if (toDeleteIDs != null) {
            for (Long toDeleteId : toDeleteIDs) {
                try {
                    ImageDescription image = imageDescriptionService.getImageById(toDeleteId);
                    if (!image.getAlbum().equals(album.getId()))
                        continue;
                    deleteFile(path+bigi+image.getLink());
                    deleteFile(path+mini+image.getLink());
                    imageDescriptionService.removeImage(toDeleteId);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        try {
            List<ImageFromAlbumDescriptionModel> images = imageDescriptionService.getImagesFromAlbum(album.getId());
            for (ImageFromAlbumDescriptionModel currentImage : images){
                try{
                    Boolean isHead = false;
                    if (isHeadIDs != null) {
                        for (Long isHeadTmpId : isHeadIDs) {
                            if (isHeadTmpId.equals(currentImage.getId())) {
                                isHead = true;//если присутствует в списке - значит, должен быть заглавной
                                break;
                            }
                        }
                    }
                    ImageDescription image = new ImageDescription(currentImage.getId(),
                            album.getId(),
                            currentImage.getTitle(),
                            currentImage.getDescription(),
                            currentImage.getCreating_date(),
                            currentImage.getCreating_time(),
                            currentImage.getLink(),
                            isHead);
                    imageDescriptionService.changeImage(image);
                }
                catch (Exception e){
                    LOG.getAppender(e.getMessage());
                }
            }
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        for (MultipartFile file : files){
            if (file.getOriginalFilename().isEmpty()) {
                continue;
            }
            downloadImage(file, album.getId(), relationSide);
        }
        return "redirect:/updateAlbum?id="+album.getId();
    }

    public static Boolean deleteFile(String path){
        File src = new File(path);
        Boolean res = src.delete();
        if (!res)
            LOG.error("Can't delete file: "+path);
        return res;
    }

    @RequestMapping(value="/updateAlbum", method= RequestMethod.GET)
    public String handleFileUpload(@RequestParam(value = "id", required = false) Long id, Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
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

    public String downloadImage(MultipartFile file, Long albumId, Double relationSide){
        String toOut = "";
        if (file != null && !file.isEmpty()) {
            try {
                String name = ContentController.generateName(file.getOriginalFilename());
                String parts[] = name.split("\\.");
                String type = parts[parts.length-1];

                byte[] bytes = file.getBytes();
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
                BufferedImage miniImg = ImageService.resizeImage(srcImg, null, null, relationSide);
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
