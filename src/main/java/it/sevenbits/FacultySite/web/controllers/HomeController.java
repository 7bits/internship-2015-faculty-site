package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.domain.gallery.ImageDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import it.sevenbits.FacultySite.web.service.gallery.ImageDescriptionService;
import it.sevenbits.FacultySite.web.service.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import it.sevenbits.FacultySite.web.domain.gallery.ImageDescriptionForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    ImageDescriptionService imageDescriptionService;

    @Autowired
    ContentOfPagesService contentOfPagesService;

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/edit_content";
    }

    @RequestMapping(value = "/news")
    public String news(@RequestParam(value="NewsType", required = false) String newsType, @RequestParam(value="NewsId", required = false) String newsId, @ModelAttribute ImageDescriptionForm form, Model model) {
        LOG.info("News type param: " + newsType);
        LOG.info("News id param: " + newsId);

        if (newsType == null)
            newsType = "All-news";
        model.addAttribute("newsType", newsType);
        try {
            if (newsId == null || Integer.parseInt(newsId) < 1)
                newsId = "0";
        }
        catch (Exception e){
            LOG.info("NewsId is incorrect: " + newsId);
            newsId = "0";
        }
        model.addAttribute("newsId", newsId);
        return "home/news";
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        return "home/main";
    }

    @RequestMapping(value = "/edit_content")
    public String editContent(Model model) {
        return "home/edit_content";
    }

    @RequestMapping(value = "/applicants")
    public String applicants() {
        return "home/applicants";
    }

    @RequestMapping(value = "/gallery")
    public String gallery(Model model) {
        try {
            List<AlbumDescription> albums = imageDescriptionService.getAllAlbumsWithUniqueLink();
            model.addAttribute("albums", albums);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return "home/gallery";
    }

    @RequestMapping(value = "/contacts")
    public String contacts() {
        return "home/contacts";
    }

    @RequestMapping(value = "/undergraduate")
    public String undergraduate() {
        return "home/undergraduate";
    }

    @RequestMapping(value = "/photo-from-albums")
    public String photo_from_albums(@RequestParam(value="albumId", required = false) Long albumId, Model model) {
        try {
            List<ImageFromAlbumDescriptionModel> images = imageDescriptionService.getImagesFromAlbum(albumId);
            AlbumDescription album = imageDescriptionService.getAlbumById(albumId);
            album.setLength((long)images.size());
            model.addAttribute("images", images);
            model.addAttribute("album", album);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }

        return "home/photo-from-albums";
    }

    @RequestMapping(value = "/enrollee")
    public String enrolle() {
        return "home/enrollee";
    }


    @RequestMapping(value = "/partners")
    public String partners() {
        return "home/partners";
    }

    @RequestMapping(value = "/structure-imit")
    public String structureImit() {
        return "home/structure-imit";
    }
}
