package com.example.oogopslag_final.Model;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private String Row1, Row2, Row3, Row4, Row5, Row6;

    public Cell() {


    }

    public Cell(int one, int two, int three, int four, int five, int six){
        Row1 = "Row" + one;
        Row2 = "Row" + two;
        Row3 = "Row" + three;
        Row4 = "Row" + four;
        Row5 = "Row" + five;
        Row6 = "Row" + six;


    }

    public String getRow1() {
        return Row1;
    }

    public void setRow1(String row1) {
        Row1 = row1;
    }

    public String getRow2() {
        return Row2;
    }

    public void setRow2(String row2) {
        Row2 = row2;
    }

    public String getRow3() {
        return Row3;
    }

    public void setRow3(String row3) {
        Row3 = row3;
    }

    public String getRow4() {
        return Row4;
    }

    public void setRow4(String row4) {
        Row4 = row4;
    }

    public String getRow5() {
        return Row5;
    }

    public void setRow5(String row5) {
        Row5 = row5;
    }

    public String getRow6() {
        return Row6;
    }

    public void setRow6(String row6) {
        Row6 = row6;
    }
}
