package com.example.listenmusic.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Song implements Parcelable {

    @SerializedName("idBaiHat")
    @Expose
    private String idBaiHat;
    @SerializedName("idDanhMuc")
    @Expose
    private String idDanhMuc;
    @SerializedName("tenBaiHat")
    @Expose
    private String tenBaiHat;
    @SerializedName("ngayPhatHanh")
    @Expose
    private String ngayPhatHanh;
    @SerializedName("hinhBaiHat")
    @Expose
    private String hinhBaiHat;
    @SerializedName("linkBaiHat")
    @Expose
    private String linkBaiHat;
    @SerializedName("idNgheSi")
    @Expose
    private String idNgheSi;
    @SerializedName("tenNgheSi")
    @Expose
    private String tenNgheSi;
    // Public constructor to allow external initialization
    public Song(String idBaiHat, String tenBaiHat, String hinhBaiHat, String tenNgheSi, String ngayPhatHanh, String linkBaiHat, String idDanhMuc, String idNgheSi) {
        this.idBaiHat = idBaiHat;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.tenNgheSi = tenNgheSi;
        this.ngayPhatHanh = ngayPhatHanh;
        this.linkBaiHat = linkBaiHat;
        this.idDanhMuc = idDanhMuc;
        this.idNgheSi = idNgheSi;
    }
    protected Song(Parcel in) {
        idBaiHat = in.readString();
        idDanhMuc = in.readString();
        tenBaiHat = in.readString();
        ngayPhatHanh = in.readString();
        hinhBaiHat = in.readString();
        idNgheSi = in.readString();
        linkBaiHat = in.readString();
        tenNgheSi = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getIdDanhMuc() {
        return idDanhMuc;
    }

    public void setIdDanhMuc(String idDanhMuc) {
        this.idDanhMuc = idDanhMuc;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getHinhBaiHat() {
        return hinhBaiHat;
    }

    public void setHinhBaiHat(String hinhBaiHat) {
        this.hinhBaiHat = hinhBaiHat;
    }

    public String getLinkBaiHat() {
        return linkBaiHat;
    }

    public void setLinkBaiHat(String linkBaiHat) {
        this.linkBaiHat = linkBaiHat;
    }

    public String getIdNgheSi() {
        return idNgheSi;
    }

    public void setIdNgheSi(String idNgheSi) {
        this.idNgheSi = idNgheSi;
    }

    public String getTenNgheSi() {
        return tenNgheSi;
    }

    public void setTenNgheSi(String tenNgheSi) {
        this.tenNgheSi = tenNgheSi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(idBaiHat);
        parcel.writeString(idDanhMuc);
        parcel.writeString(tenBaiHat);
        parcel.writeString(ngayPhatHanh);
        parcel.writeString(hinhBaiHat);
        parcel.writeString(idNgheSi);
        parcel.writeString(linkBaiHat);
        parcel.writeString(tenNgheSi);
    }

    @Override
    public String toString() {
        return "Song{" +
                "idBaiHat='" + idBaiHat + '\'' +
                ", idDanhMuc='" + idDanhMuc + '\'' +
                ", tenBaiHat='" + tenBaiHat + '\'' +
                ", ngayPhatHanh='" + ngayPhatHanh + '\'' +
                ", hinhBaiHat='" + hinhBaiHat + '\'' +
                ", linkBaiHat='" + linkBaiHat + '\'' +
                ", idNgheSi='" + idNgheSi + '\'' +
                ", tenNgheSi='" + tenNgheSi + '\'' +
                '}';
    }
}
