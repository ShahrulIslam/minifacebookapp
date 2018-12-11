package com.example.user.myuber;

public class Friends {
  private   String email,pict,name;

    public Friends(String email, String name, String pict) {
        this.email = email;
        this.pict = pict;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
