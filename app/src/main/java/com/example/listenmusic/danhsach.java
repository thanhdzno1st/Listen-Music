package com.example.listenmusic;

public class danhsach {
    private String tenbaihat;
    private String casi;
    private int Hinh;

    public danhsach(String tenbaihat, String casi,int hinh) {
        this.casi = casi;
        this.tenbaihat = tenbaihat;
        Hinh = hinh;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public int getHinh() {
        return Hinh;
    }

    public String getCasi() {
        return casi;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }
}
