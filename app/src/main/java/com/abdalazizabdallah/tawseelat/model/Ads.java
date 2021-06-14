package com.abdalazizabdallah.tawseelat.model;

public class Ads {

    private String time;
    private String title;
    private String photoPath;
    private String price;


    public Ads(String time, String title, String photoPath, String price) {
        this.time = time;
        this.title = title;
        this.photoPath = photoPath;
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
