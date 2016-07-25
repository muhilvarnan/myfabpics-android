package com.myfabpics.DataClass;

/**
 * Created by root on 24/7/16.
 */
public class NavItem {
    int id;
    String name;
    int imageIcon;

    public NavItem(int id, String name, int imageIcon) {
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

    public int getImageIcon() {
        return this.imageIcon;
    }

    public void setImageIcon(int imageIcon) {
        this.imageIcon = imageIcon;
    }

}
