package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.core.service.ServiceException;
import it.sevenbits.FacultySite.core.service.gallery.ImageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ImagesController {
    @Autowired
    ImageService imageDescriptionService;

    private static Logger LOG = Logger.getLogger(ImagesController.class);



    @RequestMapping(value = "/gallery")
    public String gallery(Model model,
                          @RequestParam(value = "deleteId", required = false) Long removeAlbumId) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("root", true);
            if (removeAlbumId != null && removeAlbumId >0){
                try {
                    imageDescriptionService.removeAlbum(removeAlbumId);
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
                       @RequestParam(value = "relation-side-width", required = false) Double relationSideWidth,
                       @RequestParam(value = "relation-side-height", required = false) Double relationSideHeight){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
//        try {
//            id = imageDescriptionService.updateAlbum(relationSideWidth, relationSideHeight, id, title, description, toDeleteIDs, isHeadIDs, files);
//        }
//        catch (ServiceException e){
//            LOG.error(e);
//        }
        return "redirect:/updateAlbum?id="+id;
    }


    @RequestMapping(value="/updateAlbum", method= RequestMethod.GET)
    public String handleFileUpload(@RequestParam(value = "id", required = false) Long id, Model model){
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("root"))
            return "redirect:/main";
//        try{
//            if (id == null || id < 1) {
//                model.addAttribute("album", imageDescriptionService.getAlbumById(id));
//                model.addAttribute("photos", imageDescriptionService.getImagesFromAlbum(id));
//                return "home/edit-album";
//            }
//            AlbumDescription album = imageDescriptionService.getAlbumById(id);
//            List<ImageFromAlbumDescriptionModel> images = imageDescriptionService.getImagesFromAlbum(id);
//            model.addAttribute("album", album);
//            model.addAttribute("photos", images);
//            return "home/edit-album";
//        }
//        catch (Exception e){
//            LOG.error(e.getMessage());
//        }
        return "home/edit-album";
    }



}
