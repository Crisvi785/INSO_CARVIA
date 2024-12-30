package com.carvia.models.vo;

import java.util.List;

public class VehicleVo {
    private int idVe;
    private String make;
    private String model;
    private int year;
    private int kilometers;
    private String combustion;
    private String shifter;
    private String color;

    // Constructor vacío
    public VehicleVo() {
    }

    // Getters y Setters
    public int getIdVe(){
        return this.idVe;
    }

    public void setIdVe(int idVe){
        this.idVe = idVe;
    }
    
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getKilometers() {
        return this.kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public String getCombustion() {
        return this.combustion;
    }

    public void setCombustion(String combustion) {
        this.combustion = combustion;
    }

    public String getShifter() {
        return this.shifter;
    }

    public void setShifter(String shifter) {
        this.shifter = shifter;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
