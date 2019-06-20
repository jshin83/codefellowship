package com.thefuzzydragon.jen.codefellowship;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/post")
    public String createNewPost(){
        return "post";
    }

    @PostMapping("/post")
    public RedirectView createPost(Principal p, String body) {
        AppUser creator = appUserRepository.findByUsername(p.getName());
        Post newPost = new Post(body, creator);
        postRepository.save(newPost);

        return new RedirectView("/myprofile");
    }

    @GetMapping("/feed")
    public String getFeed(Principal p, Model m) {
        AppUser currentUser = appUserRepository.findByUsername(p.getName());
        List<AppUser> following = new ArrayList<>(currentUser.following);

        m.addAttribute(following);
        return "/feed";
    }
}
