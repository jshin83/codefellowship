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
import java.util.List;

@Controller
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getWelcomePag() {
        return "index";
    }

    @GetMapping("/myprofile")
    public String getMyProfile(Principal p, Model m) {
        m.addAttribute("principal", p);

        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        m.addAttribute("currentUser", currentUser);

        //get all users
        Iterable<AppUser> users = appUserRepository.findAll();
        List<AppUser> allUsers = new ArrayList<>();

        users.forEach(allUsers::add);
        allUsers.remove(currentUser);

        m.addAttribute("allUsers", allUsers);

        return "myprofile";
    }

    @PostMapping("/add/{username}")
    public RedirectView follow(@PathVariable String username, Principal p) {

        AppUser userToAdd = appUserRepository.findByUsername(username);

        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        currentUser.following.add(userToAdd);
        appUserRepository.save(currentUser);

        return new RedirectView("/myprofile");
    }

    @GetMapping("/myprofile/{username}")
    public String getFollowingProfile(@PathVariable String username, Model m, Principal p) {

        m.addAttribute("principal", false);

        AppUser loggedIn = appUserRepository.findByUsername(p.getName());

        //converts principal object to my Model object
//        AppUser currentUser = (AppUser)((UsernamePasswordAuthenticationToken) p).getPrincipal();

        AppUser currentUser = appUserRepository.findByUsername(username);
        m.addAttribute("currentUser", currentUser);

        if(loggedIn.following.contains(currentUser)) {
            m.addAttribute("alreadyFollowing", true);
        } else {
            m.addAttribute("alreadyFollowing", false);
        }

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