package com.example.oogopslag_final.Model;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private String cName;


    public Cell() {


    }

    public Cell(String name){
        cName = name;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}