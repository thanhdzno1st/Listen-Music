package com.example.listenmusic.model;

public class BaiHat {
    private  String tenBaiHat;
    private  String hinhAnh;
    private  String tenNgheSi;


    public BaiHat(String tenBaiHat, String hinhAnh, String tenNgheSi) {
        this.tenBaiHat = tenBaiHat;
        this.hinhAnh = hinhAnh;
        this.tenNgheSi = tenNgheSi;
    }


    public  String getTenBaiHat(){return tenBaiHat;}

    public  String getHinhAnh() {
        return hinhAnh;
    }

    public  String getTenNgheSi(){return tenNgheSi;}
}
