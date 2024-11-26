package com.example.listenmusic.Models;

import java.io.Serializable;

public class User implements Serializable {
    private int idTaiKhoan;
    private String hoTen;
    private String sdt;
    private String email;
    private String pass;
    private String vaitro;

    public int getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(int idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    @Override
    public String toString() {
        return "User{" +
                "idTaiKhoan=" + idTaiKhoan +
                ", hoTen='" + hoTen + '\'' +
                ", sdt='" + sdt + '\'' +
                ", email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", vaitro='" + vaitro + '\'' +
                '}';
    }
}
