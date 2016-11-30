package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogEntry {

    private List<Comment> entryComments;

    private String slug;
    private String body;
    private String title;
    private Date date;

    public BlogEntry(String body, String title) {
        entryComments = new ArrayList<>();
        this.body = body;
        this.title = title;
        setDate(new Date());
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addComment(Comment comment) {
        entryComments.add(comment);
        return true;
    }

    public List<Comment> getEntryComments() {
        return entryComments;
    }

    public void setEntryComments(List<Comment> entryComments) {
        this.entryComments = entryComments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
