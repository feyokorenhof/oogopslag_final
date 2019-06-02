package com.example.oogopslag_final.Model;

import java.util.Date;

public class User {

    private String Name;
    private String Password;
    private String Role;
    private String Birthday;

    public User(){


    }

    public User(String name, String password, String role, String birthday){

        Name = name;
        Password = password;
        Role = role;
        Birthday = birthday;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getBirthday(){
        return Birthday;
    }

    public void setBirthday(String birthday){
        Birthday = birthday;

    }
}
