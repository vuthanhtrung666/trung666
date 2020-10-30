package com.example.chamcong.model;

public class DSBaocao {
    private int iManv;
    private String sTen;
    private String sEmail;
    private int sSang;
    private int sChieu;

    public DSBaocao(int iManv, String sEmail, int sSang, int sChieu) {
        this.iManv = iManv;
        this.sEmail = sEmail;
        this.sSang = sSang;
        this.sChieu = sChieu;
    }

    public DSBaocao(int iManv, String sTen, String sEmail, int sChieu) {
        this.iManv = iManv;
        this.sTen = sTen;
        this.sEmail = sEmail;
        this.sChieu = sChieu;
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

    public int getsSang() {
        return sSang;
    }

    public void setsSang(int sSang) {
        this.sSang = sSang;
    }

    public int getsChieu() {
        return sChieu;
    }

    public void setsChieu(int sChieu) {
        this.sChieu = sChieu;
    }
}
