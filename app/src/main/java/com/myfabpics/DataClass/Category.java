package com.myfabpics.DataClass;

/**
 * Created by root on 24/7/16.
 */
public class Category {
    int id;
    String title;
    String navIcon;
    String image;

    public Category(int id, String title, String image, String navIcon) {
        this.id = id;
        this.title = title;
        this.navIcon = navIcon;
        this.image = image;
    }
    public Category() {

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNavIcon() {
        return this.navIcon;
    }

    public void setNavIcon(String navIcon) {
        this.navIcon = navIcon;
    }

    public String getImage() {
        return this.title;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
