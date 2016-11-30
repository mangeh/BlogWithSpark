package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {

    String comment;
    private String author;
    private Date commentDate;

    public Comment(String comment,String author) {
        this.comment = comment;
        this.author = author;
        setCommentDate(new Date());
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }
}
