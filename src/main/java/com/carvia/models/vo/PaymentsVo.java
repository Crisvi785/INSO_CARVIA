package com.carvia.models.vo;


public class PaymentsVo {
    private int idPay;
    private String type;
    private double amount;

    public PaymentsVo(int id, double cantidad){
        this.idPay = id;
        this.amount = cantidad;
    }

    //Getters
    public int getIdPay(){
        return this.idPay;
    }

    public String getType(){
        return this.type;
    }

    public double getAmount(){
        return this.amount;
    }

    //Setters
    public void setIdPay(int idPay){
        this.idPay = idPay;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
}
