package com.example.oogopslag_final.Model;

import com.example.oogopslag_final.R;

public class Kist {
    private String ras, maat, kwaliteit, cel, row, col, plaats, datum;
    public Kist(){}
    public Kist(String Ras, String Maat, String Kwaliteit, String Cel, String Row, String Col, String Plaats, String Datum){
        ras = Ras;
        maat = Maat;
        kwaliteit = Kwaliteit;
        cel = Cel;
        row = Row;
        col = Col;
        plaats = Plaats;
        datum = Datum;

        //lala

    }

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public String getMaat() {
        return maat;
    }

    public void setMaat(String maat) {
        this.maat = maat;
    }

    public String getKwaliteit() {
        return kwaliteit;
    }

    public void setKwaliteit(String kwaliteit) {
        this.kwaliteit = kwaliteit;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }
}
