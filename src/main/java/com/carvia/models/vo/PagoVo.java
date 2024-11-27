package com.carvia.models.vo;

public class PagoVo {
    int id;
    double cantidad;

    public PagoVo(int id, double cantidad){
        this.id = id;
        this.cantidad = cantidad;
    }

    //Getters
    public int getId(){
        return id;
    }

    public double getCantidad(){
        return cantidad;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setCantidad(double cantidad){
        this.cantidad = cantidad;
    }
}
