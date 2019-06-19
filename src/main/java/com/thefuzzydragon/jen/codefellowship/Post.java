package com.thefuzzydragon.jen.codefellowship;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    AppUser creator;

    String body;

    @DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    Date createdAt;

    public Post(){}

    public Post(String body, AppUser creator) {
        this.creator = creator;
        this.body = body;
        this.createdAt = new Date();
    }

}
