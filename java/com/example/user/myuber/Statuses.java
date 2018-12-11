package com.example.user.myuber;

public class Statuses {
   private String Status,Date,pics;

    public Statuses(String status, String date, String pics) {
        this.Status = status;
       this.Date = date;
       this.pics=pics;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
