package com.example.listenmusic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Playlist {
    @SerializedName("idPlaylist")
    @Expose
    private String idPlaylist;
    @SerializedName("tenPlayList")
    @Expose
    private String tenPlayList;
    @SerializedName("idBaiHat")
    @Expose
    private String idBaiHat;
    @SerializedName("hoTen")
    @Expose
    private String hoTen;
    @SerializedName("hinhPlayList")
    @Expose
    private String hinhPlayList;

    public String getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(String idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getTenPlayList() {
        return tenPlayList;
    }

    public void setTenPlayList(String tenPlayList) {
        this.tenPlayList = tenPlayList;
    }

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
    public String getHinhPlayList() {
        return hinhPlayList;
    }

    public void setHinhPlayList(String hinhPlayList) {
        this.hinhPlayList = hinhPlayList;
    }
}
