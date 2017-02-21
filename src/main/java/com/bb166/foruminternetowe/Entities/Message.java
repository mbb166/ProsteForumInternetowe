package com.bb166.foruminternetowe.Entities;

import java.util.Date;

public class Message implements Comparable<Message> {
    private int id;
    private String title;
    private String text;
    private User owner;
    private String author;
    private Date date;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public int compareTo(Message o) {
        return date.compareTo(o.getDate());
    }
}