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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AppUser getCreator() {
        return creator;
    }

    public void setCreator(AppUser creator) {
        this.creator = creator;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
