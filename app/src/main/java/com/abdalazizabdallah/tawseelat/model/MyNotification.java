package com.abdalazizabdallah.tawseelat.model;

public class MyNotification {


    private String titleNotification;
    private String bodyNotification;

    public MyNotification(String titleNotification, String bodyNotification) {
        this.titleNotification = titleNotification;
        this.bodyNotification = bodyNotification;
    }

    public String getTitleNotification() {
        return titleNotification;
    }

    public void setTitleNotification(String titleNotification) {
        this.titleNotification = titleNotification;
    }

    public String getBodyNotification() {
        return bodyNotification;
    }

    public void setBodyNotification(String bodyNotification) {
        this.bodyNotification = bodyNotification;
    }
}
