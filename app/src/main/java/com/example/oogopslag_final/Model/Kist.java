package com.example.oogopslag_final.Model;

public class Kist {
    private String ras, maat, kwaliteit, cel, datum;
    public Kist(){};
    public Kist(String Ras, String Maat, String Kwaliteit, String Cel, String Datum){
        ras = Ras;
        maat = Maat;
        kwaliteit = Kwaliteit;
        cel = Cel;
        datum = Datum;

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
}
