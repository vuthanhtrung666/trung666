package com.example.chamcong.model;

import java.io.Serializable;

public class Nhanvien implements Serializable {
    private int iManv;
    private String sTen;
    private String sEmail;
    private String sChucvu;
    private String sUsername;
    private String sPassname;
    private String sSang;

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

    private String sChieu;

    public Nhanvien() {
    }

    public Nhanvien(int iManv, String sTen, String sEmail, String sSang, String sChieu) {
        this.iManv = iManv;
        this.sTen = sTen;
        this.sEmail = sEmail;
        this.sSang = sSang;
        this.sChieu = sChieu;
    }

    public Nhanvien(int iManv, String sTen, String sEmail, String sChucvu, String sUsername, String sPassname) {
        this.iManv = iManv;
        this.sTen = sTen;
        this.sEmail = sEmail;
        this.sChucvu = sChucvu;
        this.sUsername = sUsername;
        this.sPassname = sPassname;
    }

    public Nhanvien(int iManv, String sTen, String sEmail, String sChucvu) {
        this.iManv = iManv;
        this.sTen = sTen;
        this.sEmail = sEmail;
        this.sChucvu = sChucvu;
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

    public String getsChucvu() {
        return sChucvu;
    }

    public void setsChucvu(String sChucvu) {
        this.sChucvu = sChucvu;
    }

    public String getsUsername() {
        return sUsername;
    }

    public void setsUsername(String sUsername) {
        this.sUsername = sUsername;
    }

    public String getsPassname() {
        return sPassname;
    }

    public void setsPassname(String sPassname) {
        this.sPassname = sPassname;
    }

    @Override
    public String toString() {
        return "Mã nhân viên: " + iManv + "\nTên nhân viên: " + sTen  + "\nĐịa chỉ Email: " + sEmail
                + "\nChức vụ" + sChucvu + "\nUsername: " + sUsername  + "\nPassword: " + sPassname;
    }
}
