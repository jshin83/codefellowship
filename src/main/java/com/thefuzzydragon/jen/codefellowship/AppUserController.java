package com.thefuzzydragon.jen.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getDinosaurs(Principal p, Model m) {
//        System.out.println(p.getName());
//        m.addAttribute("principal", p);
        return "index";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        m.addAttribute("principal", p);

        //converts principal object to my Model object
//        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();

        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);

        return "myprofile";
    }


    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView createUser(String username, String password, String firstName, String lastName, String bio, Date dateOfBirth) {
        AppUser newUser = new AppUser(username, bCryptPasswordEncoder.encode(password), firstName, lastName, bio, dateOfBirth);
        appUserRepository.save(newUser);

        //get id
//        AppUser dbUser = appUserRepository.findByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/myprofile");
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

}