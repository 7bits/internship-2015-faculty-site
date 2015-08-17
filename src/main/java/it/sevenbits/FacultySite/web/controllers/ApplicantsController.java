package it.sevenbits.FacultySite.web.controllers;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicantsController {
    @RequestMapping(value = "/applicants")
    public String applicants(Model model) {
        return "home/applicants";
    }

    @RequestMapping(value = "/undergraduate")
    public String undergraduate() {
        return "home/undergraduate";
    }

    @RequestMapping(value = "/enrollee")
    public String enrollee() {
        return "home/enrollee";
    }


}
