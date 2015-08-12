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
    public String enrollee(Model model) {
        model = adminModelAttributes(model, "Graduates:", (long)0, (long)0);
        return "home/enrollee";
    }

    private Model adminModelAttributes(Model model, String type, Long redactId, Long deleteId){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("root")) {
            model.addAttribute("createType", type);
            model.addAttribute("canRedact", true);
            model.addAttribute("canDelete", true);
            model.addAttribute("redactId", redactId);
            model.addAttribute("deleteId", deleteId);
        }
        return model;
    }

}
