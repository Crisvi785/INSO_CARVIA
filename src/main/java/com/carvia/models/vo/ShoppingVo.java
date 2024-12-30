package com.carvia.models.vo;

import java.time.LocalDate;

public class ShoppingVo {
    private int idShop;
    private int idVe;
    private int idPay;
    private int idUs;
    private LocalDate date; 

    public ShoppingVo(){

    }

    /*
    public ShoppingVo(int idShop, int idVe, int idPay, int idUs, String date) {
        this.idShop = idShop;
        this.idVe = idVe;
        this.idPay = idPay;
        this.idUs = idUs;
        this.date = date;
    }

    public ShoppingVo(int idVe, int idPay, int idUs, String date) {
        this.idVe = idVe;
        this.idPay = idPay;
        this.idUs = idUs;
        this.date = date;
    }
    */

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    public int getIdVe() {
        return idVe;
    }

    public void setIdVe(int idVe) {
        this.idVe = idVe;
    }

    public int getIdPay() {
        return idPay;
    }

    public void setIdPay(int idPay) {
        this.idPay = idPay;
    }

    public int getIdUs() {
        return idUs;
    }

    public void setIdUs(int idUs) {
        this.idUs = idUs;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
