package com.bb166.foruminternetowe.Entities;

import java.util.Date;

public class Post implements Comparable<Post> {
    private int id;
    private String text;
    private int number;
    private Topic topic;
    private User author;
    private String authorName;
    private Date date;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        this.authorName = author.getUsername();
    }

    public String getAuthorName() {
        return authorName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Post o) {
        return number - o.getNumber();
    }
}
