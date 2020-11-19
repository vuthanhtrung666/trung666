package com.example.chamcong.model;

public class DSBaocao {
    private int iManv;
    private String sTen;
    private String sEmail;
    private int sBuoi;


    public DSBaocao() {
    }

    public DSBaocao(int iManv, String sTen, String sEmail, int sBuoi) {
        this.iManv = iManv;
        this.sTen = sTen;
        this.sEmail = sEmail;
        this.sBuoi = sBuoi;
    }

    public int getiManv() {
        return iManv;
    }

    public void setiManv(int iManv) {
        this.iManv = iManv;
    }

    public String getsTen() {
        return sTen;
    }

    public void setsTen(String sTen) {
        this.sTen = sTen;
    }

    public String getsEmail() {
        return sEmail;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public int getsBuoi() {
        return sBuoi;
    }

    public void setsBuoi(int sBuoi) {
        this.sBuoi = sBuoi;
    }
}
