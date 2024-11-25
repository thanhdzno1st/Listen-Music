package com.example.listenmusic.model;

public class Trend {
    private  String stt;
    private  String tenBaiHat;
    private  String hinhAnh;
    private  String tenNgheSi;


    public Trend(String stt, String tenBaiHat, String hinhAnh, String tenNgheSi) {
        this.stt = stt;
        this.tenBaiHat = tenBaiHat;
        this.hinhAnh = hinhAnh;
        this.tenNgheSi = tenNgheSi;
    }



    public  String getStt() {
        return stt;
    }

    public  String getTenBaiHat(){return tenBaiHat;}

    public  String getHinhAnh() {
        return hinhAnh;
    }

    public  String getTenNgheSi(){return tenNgheSi;}
}

