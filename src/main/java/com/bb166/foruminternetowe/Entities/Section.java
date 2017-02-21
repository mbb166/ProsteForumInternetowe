package com.bb166.foruminternetowe.Entities;

import java.util.Set;

public class Section {
    private int id;
    private String name;
    private String nameWithoutFloors;
    private Set<Section> sectionSet;
    private Section section;
    private Set<Topic> topics;
    private int topicsSize;

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.replace(' ','_');
        this.nameWithoutFloors = name.replace('_',' ');
    }

    public String getNameWithoutFloors() {
        return nameWithoutFloors;
    }

    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
        this.topicsSize = topics.size();
    }

    public int getTopicsSize() {
        return topicsSize;
    }

}
