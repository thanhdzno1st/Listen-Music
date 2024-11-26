package com.example.listenmusic.model;

public class NgheSi {
    private  String idNgheSi;
    private  String tenNgheSi;
    private  String avartar;


    public NgheSi(String idNgheSi, String tenNgheSi, String avartar) {
        this.idNgheSi = idNgheSi;
        this.tenNgheSi = tenNgheSi;
        this.avartar = avartar;
    }


    public  String getIdNgheSi(){return idNgheSi;}

    public  String getAvartar() {
        return avartar;
    }

    public  String getTenNgheSi(){return tenNgheSi;}
}
