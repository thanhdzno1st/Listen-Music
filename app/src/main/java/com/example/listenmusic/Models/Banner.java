package com.example.listenmusic.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Banner implements Serializable {

        @SerializedName("idBanner")
        @Expose
        private String idBanner;
        @SerializedName("hinhAnhBanner")
        @Expose
        private String hinhAnhBanner;
        @SerializedName("noiDung")
        @Expose
        private String noiDung;
        @SerializedName("idBaiHat")
        @Expose
        private String idBaiHat;
        @SerializedName("tenBaiHat")
        @Expose
        private String tenBaiHat;
        @SerializedName("hinhBaiHat")
        @Expose
        private String hinhBaiHat;

        public String getIdBanner() {
        return idBanner;
        }

        public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
        }

        public String getHinhAnhBanner() {
        return hinhAnhBanner;
        }

        public void setHinhAnhBanner(String hinhAnhBanner) {
        this.hinhAnhBanner = hinhAnhBanner;
        }

        public String getNoiDung() {
        return noiDung;
        }

        public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
        }

        public String getIdBaiHat() {
        return idBaiHat;
        }

        public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
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

}