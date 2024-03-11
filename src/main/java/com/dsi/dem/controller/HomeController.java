package com.dsi.dem.controller;

import com.dsi.dem.model.User;
import com.dsi.dem.repository.UserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class HomeController {
    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @ModelAttribute
    public User getUser(Principal principal) {
        if(principal != null) {
            return userRepository.getUserByEmail(principal.getName());
        }
        return null;
    }
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        User user = (User) model.getAttribute("user");
        System.out.println(user);
        if (user != null ) {

            return "redirect:/";

        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        System.out.println("/logout url hitted----------------------------------------");
        return "logout";
    }

    @GetMapping("/k")
    @ResponseBody
    public String Amike(Principal principal) {
        return principal.toString();
    }
}
