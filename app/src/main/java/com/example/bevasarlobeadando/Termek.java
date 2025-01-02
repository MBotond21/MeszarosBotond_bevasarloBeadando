package com.example.bevasarlobeadando;

public class Termek {
    private String nev;
    private int egyseg_ar;
    private double mennyiseg;
    private String mertekegyseg;
    private double brutto_ar;

    public Termek(String nev, int egyseg_ar, double mennyiseg, String mertekegyseg) {
        this.nev = nev;
        this.egyseg_ar = egyseg_ar;
        this.mennyiseg = mennyiseg;
        this.mertekegyseg = mertekegyseg;
        setBrutto_ar();
    }

    public Termek() {
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getEgyseg_ar() {
        return egyseg_ar;
    }

    public void setEgyseg_ar(int egyseg_ar) {
        this.egyseg_ar = egyseg_ar;
    }

    public double getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(double mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public void setMertekegyseg(String mertekegyseg) {
        this.mertekegyseg = mertekegyseg;
    }

    public double getBrutto_ar() {
        return brutto_ar;
    }

    public void setBrutto_ar() {
        this.brutto_ar = Math.round(egyseg_ar*mennyiseg * 100.0) / 100.0;
    }
}
