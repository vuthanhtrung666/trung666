package com.example.chamcong.model;

public class Chamcong {
    int iManv;
    String sNgay;
    String sSang;
    String sChieu;

    public Chamcong() {
    }

    public Chamcong(int iManv, String sNgay, String sSang, String sChieu) {
        this.iManv = iManv;
        this.sNgay = sNgay;
        this.sSang = sSang;
        this.sChieu = sChieu;
    }

    public int getiManv() {
        return iManv;
    }

    public void setiManv(int iManv) {
        this.iManv = iManv;
    }

    public String getsNgay() {
        return sNgay;
    }

    public void setsNgay(String sNgay) {
        this.sNgay = sNgay;
    }

    public String getsSang() {
        return sSang;
    }

    public void setsSang(String sSang) {
        this.sSang = sSang;
    }

    public String getsChieu() {
        return sChieu;
    }

    public void setsChieu(String sChieu) {
        this.sChieu = sChieu;
    }
}
