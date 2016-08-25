package com.myfabpics.DataClass;

/**
 * Created by root on 24/7/16.
 */
public class NavItem {
    int id;
    String name;
    String imageIcon;

    public NavItem(int id, String name, String imageIcon) {
        this.id = id;
        this.name = name;
        this.imageIcon = imageIcon;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageIcon() {
        return this.imageIcon;
    }

    public void setImageIcon(String imageIcon) {
        this.imageIcon = imageIcon;
    }

}
