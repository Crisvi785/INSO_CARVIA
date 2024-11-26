package com.carvia.models.vo;

public class VehicleVo {
    private String marca;
    private String modelo;
    private int ano;
    private int kilometraje;
    private double precio;

    public VehicleVo(String marca, String modelo, int ano, int kilometraje, double precio) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.kilometraje = kilometraje;
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public double getPrecio() {
        return precio;
    }
}
