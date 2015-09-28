package it.sevenbits.FacultySite.web.controllers;

import it.sevenbits.FacultySite.core.domain.content.Content;
import it.sevenbits.FacultySite.core.domain.gallery.AlbumDescription;
import it.sevenbits.FacultySite.core.service.content.ContentService;
import it.sevenbits.FacultySite.web.domain.content.ContentForm;
import it.sevenbits.FacultySite.core.service.gallery.ImageService;
import it.sevenbits.FacultySite.web.domain.gallery.ImageFromAlbumDescriptionModel;
import it.sevenbits.FacultySite.web.service.NewsService;
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

//    @Autowired
//    ContentOfPagesService contentOfPagesService;
    @Autowired
    ImageService imagesService;
    @Autowired
    NewsService newsService;
//    @Autowired
//    NewsService newsService;

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/main";
    }

    @RequestMapping(value = "/main")
    public String main(Model model) {
        List<ContentForm> news = newsService.constructNewsPage(1);
        model.addAttribute("content", news);

//--------------------------ОСТОРОЖНО!!! Костыли
        try {
            List<AlbumDescription> albums =  imagesService.getAllAlbums();
            model.addAttribute("albums", albums);
            List<List<ImageFromAlbumDescriptionModel>> images = new ArrayList<>();
            for (AlbumDescription album : albums){
                List<ImageFromAlbumDescriptionModel> imagesOfThisAlbum =
                        imagesService.getImagesFromAlbum(album.getId());
                images.add(imagesOfThisAlbum);
            }
            model.addAttribute("images", images);
        }
        catch (Exception e){

        }
        Content mainContent = new Content();
        mainContent.setTitle("Институт математики и иформационных технологий");
        mainContent.setDescription("<p>Омский государственный институт им. Ф.М. Достоевского</p><p>&nbsp;</p><h1>Институт Математики и Информационных Технологий</h1><p><p>ИМИТ (ранее &ndash; математический факультет) для тех, кто отличает аксиому от теоремы, для тех, кто любит решать трудные задачи и не боится их. На нашем факультете прием абитуриентов и обучение студентов ведётся по направлению: &laquo;Прикладная математика и информатика&raquo;. На младших курсах даётся фундаментальная подготовка, а на старших обучение ведётся по широкому спектру специализаций: системное программирование; математические методы защиты информации; &nbsp;информационное обеспечение экономической деятельности и т.д.</p><p><br />Выпускники факультета легко найдут себе работу по специальности не только в нашем регионе, но и за его пределами, в том числе в США, Великобритании, Канаде или Германии. Спасибо за это нужно сказать нашим бывшим сотрудникам, преподавателям, выпускникам, в свое время перебравшимися за рубеж, но поддерживающим связь с факультетом. Проблем с конвертацией диплома выпускники факультета не испытывают.Что немаловажно, у нас целых 90 бюджетных, то есть &nbsp;совершенно бесплатных (для абитуриентов!) мест и целых 20 бюджетных мест в магистратуре.Поступай и учись бесплатно на лучшем факультете города!</p><p><br />Деканат находится по адресу: Проспект Мира 55/а (первый корпус ОмГУ), каб. 217<br />Телефоны: &nbsp;(3812) 64-42-38, (3812) 22-56-96<br />Часы работы: пн-пт с 9:00 до 17:00</p>");
        List<Content> mainInfo = new ArrayList<>();
        mainInfo.add(mainContent);
        model.addAttribute("mainInfo", mainInfo);
        model.addAttribute("title", "Последние новости");
        return "home/main";
    }

    @RequestMapping(value = "/contacts")
    public String contacts() {
        return "home/contacts";
    }



    @RequestMapping(value = "/graduates")
    public String graduates(Model model){
//        try {
//            model.addAttribute("content", contentOfPagesService.getPagesWhichContainTypeIsPublish("Graduates", true));
//            model = ContentController.adminModelAttributes(model, "Graduates", null, null);
//        }
//        catch (Exception e){
//            LOG.error(e.getMessage());
//        }
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
