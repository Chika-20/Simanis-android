package com.simanisandroid.simanis.Model;

public class PerawatModel {
    public String email;
    public String username;
    public String password;

    public PerawatModel(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
