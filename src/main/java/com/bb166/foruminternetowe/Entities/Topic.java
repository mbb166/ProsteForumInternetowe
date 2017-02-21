package com.bb166.foruminternetowe.Entities;

import java.util.Set;

public class Topic{
    private int id;
    private String title;
    private User author;
    private Set<Post> postSet;
    private Section section;
    private int postSize;
    private String authorName;
    private String titleWithoutFloors;

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
        this.title = title.replace(' ','_');
        this.titleWithoutFloors = title.replace('_',' ');
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
        this.authorName = author.getUsername();
    }

    public String getTitleWithoutFloors() {
        return titleWithoutFloors;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Set<Post> getPostSet() {
        return postSet;
    }

    public void setPostSet(Set<Post> postSet) {
        this.postSet = postSet;
        this.postSize = postSet.size();
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getPostSize() {
        return postSize;
    }
}
