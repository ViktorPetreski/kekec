package com.example.kekec.model.web;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
    @RequestMapping(value = {"/admin"}, method = RequestMethod.GET)
    public String admin(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            UserDetails details= (UserDetails) authentication.getPrincipal();
        }
        model.addAttribute("pageFragment", "admin");
        return "index";
    }
}
