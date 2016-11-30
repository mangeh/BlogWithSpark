package com.teamtreehouse.blog.model;

import java.util.List;

/**
 * Created by Magnus on 2016-11-21.
 */
public class Blog {

    List<BlogEntry> blogEntries;



    public List<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    public void setBlogEntries(List<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
    }
}
