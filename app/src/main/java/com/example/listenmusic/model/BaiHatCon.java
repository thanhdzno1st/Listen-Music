package com.example.listenmusic.model;

public class BaiHatCon {
    private int idBaihat;
    private String tenBaiHat;
    private String hinhBaiHat;
    private String tenNgheSi;  // Thêm thuộc tính tenNgheSi

    // Constructor
    public BaiHatCon(int idbaihat, String tenBaiHat, String hinhBaiHat, String tenNgheSi) {
        this.idBaihat = idbaihat;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.tenNgheSi = tenNgheSi;  // Gán giá trị cho tenNgheSi
    }

    // Getter và setter
    public int getIdbaihat() {
        return idBaihat;
    }

    public void setIdbaihat(int idbaihat) {
        this.idBaihat = idbaihat;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getHinhBaiHat() {
        return hinhBaiHat;
    }

    public void setHinhBaiHat(String hinhBaiHat) {
        this.hinhBaiHat = hinhBaiHat;
    }

    public String getTenNgheSi() {
        return tenNgheSi;
    }

    public void setTenNgheSi(String tenNgheSi) {
        this.tenNgheSi = tenNgheSi;
    }
}
