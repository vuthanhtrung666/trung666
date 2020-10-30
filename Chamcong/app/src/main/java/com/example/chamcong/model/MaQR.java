package com.example.chamcong.model;

public class MaQR {
    String sBuoi;
    String sQR;

    public MaQR() {
    }

    public MaQR(String sBuoi, String sQR) {
        this.sBuoi = sBuoi;
        this.sQR = sQR;
    }

    public String getsBuoi() {
        return sBuoi;
    }

    public void setsBuoi(String sBuoi) {
        this.sBuoi = sBuoi;
    }

    public String getsQR() {
        return sQR;
    }

    public void setsQR(String sQR) {
        this.sQR = sQR;
    }
}
