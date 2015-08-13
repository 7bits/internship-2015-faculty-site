package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import it.sevenbits.FacultySite.web.service.gallery.ImageDescriptionService;
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
        return "redirect:/main";
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        List<ContentDescriptionModel> news = new ArrayList<>();
        List<ContentDescription> mainInfo = new ArrayList<>();
        try {
            news = contentOfPagesService.getPagesWhichContainTypeWithCountWithPublish("News:%", (long) 5, true);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        mainInfo.add(new ContentDescription("Институт математики и иформационных технологий", "<h4>ОмГУ им. Ф. М. Достоевского</h4><h1>Институт математики и иформационных технологий</h1><p>ИМИТ (ранее – математический факультет) для тех, кто отличает аксиому от теоремы, для тех, кто любит решать трудные задачи и не боится их.</p><p>На нашем факультете прием абитуриентов и обучение студентов ведётся по направлению: «Прикладная математика и информатика».На младших курсах даётся фундаментальная подготовка, а на старших обучение ведётся по широкому спектру специализаций: системное программирование; математические методы защиты информации;  информационное обеспечение экономической деятельности и т.д..</p><p>Выпускники факультета легко найдут себе работу по специальности не только в нашем регионе, но и за его пределами, в том числе в США, Великобритании, Канаде или Германии. Спасибо за это нужно сказать нашим бывшим сотрудникам, преподавателям, выпускникам, в свое время перебравшимися за рубеж, но поддерживающим связь с факультетом. Проблем с конвертацией диплома выпускники факультета не испытывают.</p><p>Что немаловажно, у нас целых 90 бюджетных, то есть  совершенно бесплатных (для абитуриентов!) мест и целых 20 бюджетных мест в магистратуре.</p><p>Поступай и учись бесплатно на лучшем факультете города!</p><p>Деканат находится по адресу: Проспект Мира 55/а (первый корпус ОмГУ), каб. 217</p><p>Телефоны:  (3812) 64-42-38, (3812) 22-56-96</p><p>Часы работы: пн-пт с 9:00 до 17:00</p>", "", ""));
        model.addAttribute("mainInfo", mainInfo);
        model.addAttribute("news", news);
        model.addAttribute("title", "Последние новости");
        return "home/main";
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


    @RequestMapping(value = "/graduates")
    public String graduates(Model model){
        try {
            model.addAttribute("content", contentOfPagesService.getPagesWhichContainTypeIsPublish("Graduates", true));
            model = ContentController.adminModelAttributes(model, "Graduates", null, null);
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        return "home/graduates";
    }

    @RequestMapping(value = "/partners")
    public String partners() {
        return "home/partners";
    }

    @RequestMapping(value = "/structure-imit")
    public String structureImit() {
        return "home/structure-imit";
    }

    @RequestMapping(value = "/admin")
    public String admin(@RequestParam(value="logout", required = false) boolean logout, Model model) {
        model.addAttribute("logout", logout);
        return "home/admin";
    }
}
