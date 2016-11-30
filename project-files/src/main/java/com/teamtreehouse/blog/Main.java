package com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.BlogDaoImpl;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import org.eclipse.jetty.http.MetaData;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.webserver.JettyHandler;

import javax.xml.ws.RequestWrapper;
import java.util.*;

import static javafx.scene.input.KeyCode.M;
import static spark.Spark.*;
import static spark.route.HttpMethod.get;
import static spark.route.HttpMethod.post;

/**
 * Created by Magnus on 2016-11-21.
 */
public class Main {


    public static void main(String[] args) {

        BlogDao blogDao = new BlogDaoImpl();
        staticFileLocation("/public");

        List<BlogEntry> defaultEntries = new ArrayList<>(Arrays.asList(new BlogEntry("My test post1", "My test title1"), new BlogEntry("My test post2", "My test title2"), new BlogEntry("My test post3", "My test title3")));
        blogDao.setBlogEntries(defaultEntries);


        before((req, res) -> {
            if (req.session().attribute("username") != null) {
                req.attribute("username", req.cookie("username"));
            }
        });

        before("/new", (req, res) -> {
            if (req.session().attribute("username") == null) {
                res.redirect("auth/login?uri=new");
                halt();
            }
        });

        before("/:slug/edit", (req, res) -> {
            String uri = req.params("slug") + "/edit";
            if (req.session().attribute("username") == null) {
                res.redirect("/auth/login?uri=" + uri);
                halt();
            }
        });

        before("/:slug/delete", (req, res) -> {
            String uri = req.params("slug") + "/delete";
            if (req.session().attribute("username") == null) {
                res.redirect("/auth/login?uri=" + uri);
                halt();
            }
        });

        post("/auth/login", (req, res) -> {
            String origin = req.headers("Origin");
            String user = req.queryParams("username");

            String uri = req.queryParams("uri");
            String password = req.queryParams("password");
            if (user.equals("admin") && password.equals("admin")) {
                res.cookie("username", "admin");
                req.session().attribute("username", "admin");
                req.attribute("username", "admin");
            }
            //TODO why doesnt it redirect to previous page
            String url = origin + "/" + uri;
            res.redirect(url);
            halt();
            return null;
        });

        get("/auth/login", (req, res) -> {
            String uri = req.queryParams("uri");
            Map<String, String> model = new HashMap<String, String>();
            model.put("uri", uri);
            return new ModelAndView(model, "login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/", (req, res) -> {
            Map<String, Object> blogPosts = new HashMap<>();

            blogPosts.put("blogposts", blogDao.findAllEntries());
            return new ModelAndView(blogPosts, "index.hbs");

        }, new HandlebarsTemplateEngine());

        get("/new", (req, res) -> {
            Map<String, Object> blogPosts = new HashMap<>();

            blogPosts.put("blogposts", blogDao.findAllEntries());
            return new ModelAndView(blogPosts, "new.hbs");

        }, new HandlebarsTemplateEngine());

        get("/:slug", (req, res) -> {
            BlogEntry entry = blogDao.findEntryBySlug(req.params("slug"));
            Map<String, Object> model = new HashMap<>();
            model.put("entry", entry);
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/:slug/edit", (req, res) -> {
            BlogEntry entry = blogDao.findEntryBySlug(req.params("slug"));
            Map<String, Object> model = new HashMap<>();
            model.put("entry", entry);
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/:slug/edit", (req, res) -> {
            BlogEntry entry = blogDao.findEntryBySlug(req.params("slug"));
            String title = req.queryParams("title");
            String body = req.queryParams("body");
            entry.setTitle(title);
            entry.setBody(body);
            Map<String, Object> model = new HashMap<>();
            model.put("entry", entry);
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/:slug/delete", (req, res) -> {
            BlogEntry entry = blogDao.findEntryBySlug(req.params("slug"));
            blogDao.deleteEntry(entry);
            res.redirect("/");
            return null;
        });

        post("/new", (req, res) -> {
            Map<String, Object> blogPosts = new HashMap<>();
            String title = req.queryParams("title");
            String body = req.queryParams("body");
            BlogEntry blogEntry = new BlogEntry(body, title);
            blogDao.addEntry(blogEntry);
            blogPosts.put("blogposts", blogDao.findAllEntries());
            return new ModelAndView(blogPosts, "index.hbs");

        }, new HandlebarsTemplateEngine());

        post(":slug/comment", (req, res) -> {
            BlogEntry entry = blogDao.findEntryBySlug(req.params("slug"));
            Map<String, Object> blogPosts = new HashMap<>();
            String name = req.queryParams("name");
            String text = req.queryParams("comment");
            Comment comment = new Comment(text, name);
            entry.addComment(comment);
            blogPosts.put("entry", entry);
            res.redirect("/" + req.params("slug"));
            return null;
        });

    }
}
