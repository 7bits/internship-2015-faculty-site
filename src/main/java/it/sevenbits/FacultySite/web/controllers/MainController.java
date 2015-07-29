package it.sevenbits.FacultySite.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by igodyaev on 28.07.15.
 */
/*@RequestMapping(value = "/undergraduate")
    public String undergraduate() {
        return "home/undergraduate";
    }*/

@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String start(Model model){
        return "home/main";
    }

}