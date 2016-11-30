package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Magnus on 2016-11-21.
 */
public class BlogDaoImpl implements BlogDao {
    List<BlogEntry> blogEntries;

    public BlogDaoImpl() {
        this.blogEntries = new ArrayList<>();
    }

    @Override
    public boolean addEntry(BlogEntry blogEntry) {
        blogEntries.add(blogEntry);
        return true;
    }

    @Override
    public boolean updateEntry(BlogEntry blogEntry) {
//        blogEntries.a
        return true;
    }

    @Override
    public boolean deleteEntry(BlogEntry blogEntry) {
        return blogEntries.remove(blogEntry);
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream().filter(entry -> entry.getSlug().equals(slug)).findFirst().orElseThrow(RuntimeException::new);
    }

    public List<BlogEntry> getBlogEntries() {
        return blogEntries;
    }

    public void setBlogEntries(List<BlogEntry> blogEntries) {
        this.blogEntries = blogEntries;
    }
}
