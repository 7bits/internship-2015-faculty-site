package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.contentOfPages.ContentDescription;
import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.web.domain.contentOfPages.ContentDescriptionModel;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.contentOfPages.ContentOfPagesService;
import it.sevenbits.FacultySite.web.service.gallery.ImageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    ContentOfPagesService contentOfPagesService;
    @Autowired
    ImageService imagesService;

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        List<ContentDescriptionModel> news = new ArrayList<>();
        List<ContentDescription> mainInfo = new ArrayList<>();
        List<AlbumDescription> albums = new ArrayList<>();
        List<List<ImageFromAlbumDescriptionModel>> images = new ArrayList<>();
        try {
            Integer sumOfNews = contentOfPagesService.getSumOfPages("News:%", true).intValue();
            Long start = NewsController.calculateStartFromTheEnd(sumOfNews, 1, 3);
            Long end = NewsController.calculateEndFromTheEnd(start, sumOfNews, 3);
            news = NewsController.getContentByType("News:%", true, start, end, contentOfPagesService);
            albums = imagesService.getAllAlbums();
            for (AlbumDescription album : albums) {
                images.add(imagesService.getImagesFromAlbum(album.getId()));
            }
        }
        catch (Exception e){
            LOG.error(e.getMessage());
        }
        mainInfo.add(new ContentDescription("Институт математики и иформационных технологий", "<p>Омский государственный институт им. Ф.М. Достоевского</p><p>&nbsp;</p><h1>Институт Математики и Информационных Технологий</h1><p><p>ИМИТ (ранее &ndash; математический факультет) для тех, кто отличает аксиому от теоремы, для тех, кто любит решать трудные задачи и не боится их. На нашем факультете прием абитуриентов и обучение студентов ведётся по направлению: &laquo;Прикладная математика и информатика&raquo;. На младших курсах даётся фундаментальная подготовка, а на старших обучение ведётся по широкому спектру специализаций: системное программирование; математические методы защиты информации; &nbsp;информационное обеспечение экономической деятельности и т.д.</p><p><br />Выпускники факультета легко найдут себе работу по специальности не только в нашем регионе, но и за его пределами, в том числе в США, Великобритании, Канаде или Германии. Спасибо за это нужно сказать нашим бывшим сотрудникам, преподавателям, выпускникам, в свое время перебравшимися за рубеж, но поддерживающим связь с факультетом. Проблем с конвертацией диплома выпускники факультета не испытывают.Что немаловажно, у нас целых 90 бюджетных, то есть &nbsp;совершенно бесплатных (для абитуриентов!) мест и целых 20 бюджетных мест в магистратуре.Поступай и учись бесплатно на лучшем факультете города!</p><p><br />Деканат находится по адресу: Проспект Мира 55/а (первый корпус ОмГУ), каб. 217<br />Телефоны: &nbsp;(3812) 64-42-38, (3812) 22-56-96<br />Часы работы: пн-пт с 9:00 до 17:00</p>", "", ""));
        model.addAttribute("mainInfo", mainInfo);
        model.addAttribute("content", news);
        model.addAttribute("albums", albums);
        model.addAttribute("images", images);
        model.addAttribute("title", "Последние новости");
        return "home/main";
    }

    @RequestMapping(value = "/contacts")
    public String contacts() {
        return "home/contacts";
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
    @RequestMapping(value= "/schedule")
    public String schedule(){
        return "home/schedule";
    }

}
