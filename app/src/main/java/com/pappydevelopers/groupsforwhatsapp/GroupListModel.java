package com.pappydevelopers.groupsforwhatsapp;

/**
 * Created by yash on 17/9/17.
 */

public class GroupListModel {

    String title;
    String image;
    String link;

    public GroupListModel(String title,String image,String link) {

        this.title=title;
        this.image=image;
        this.link = link;
    }


    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }
}