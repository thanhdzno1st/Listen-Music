package com.example.listenmusic.Models;

import java.util.List;

public class NgheSiCha {
    private int idNgheSi;
    private String tenNgheSi;
    private String avartar;
    private List<BaiHatCon> baihat;

    // Getter và setter
    public int getIdNgheSi() {
        return idNgheSi;
    }

    public void setIdNgheSi(int idNgheSi) {
        this.idNgheSi = idNgheSi;
    }

    public String getTenNgheSi() {
        return tenNgheSi;
    }

    public void setTenNgheSi(String tenNgheSi) {
        this.tenNgheSi = tenNgheSi;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }

    public List<BaiHatCon> getBaihat() {
        return baihat;
    }

    public void setBaihat(List<BaiHatCon> baihat) {
        this.baihat = baihat;
    }
}


