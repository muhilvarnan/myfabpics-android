package com.myfabpics.DataClass;

/**
 * Created by root on 13/9/16.
 */
public class Photo {
    int id;
    int categoryId;
    String imageUrl;
    String title;

    public Photo(int id, int categoryId, String title, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.categoryId = categoryId;
    }

    public Photo() {

    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getCategoryId() {
        return this.categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return this.imageUrl;
    }

}
