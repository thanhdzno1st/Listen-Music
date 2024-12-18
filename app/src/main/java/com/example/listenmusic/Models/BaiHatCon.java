package com.example.listenmusic.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class BaiHatCon implements Parcelable {
    private int idBaihat;
    private String tenBaiHat;
    private String hinhBaiHat;
    private String tenNgheSi;

    // Constructor
    public BaiHatCon(int idBaihat, String tenBaiHat, String hinhBaiHat, String tenNgheSi) {
        this.idBaihat = idBaihat;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.tenNgheSi = tenNgheSi;
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

    // Phương thức để ghi đối tượng vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idBaihat);
        dest.writeString(tenBaiHat);
        dest.writeString(hinhBaiHat);
        dest.writeString(tenNgheSi);
    }

    // Phương thức để đọc đối tượng từ Parcel
    protected BaiHatCon(Parcel in) {
        idBaihat = in.readInt();
        tenBaiHat = in.readString();
        hinhBaiHat = in.readString();
        tenNgheSi = in.readString();
    }

    // Đối tượng CREATOR giúp Android biết cách tạo đối tượng từ Parcel
    public static final Creator<BaiHatCon> CREATOR = new Creator<BaiHatCon>() {
        @Override
        public BaiHatCon createFromParcel(Parcel in) {
            return new BaiHatCon(in);
        }

        @Override
        public BaiHatCon[] newArray(int size) {
            return new BaiHatCon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
