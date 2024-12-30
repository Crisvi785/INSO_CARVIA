package com.carvia.models.vo;
import java.time.LocalDate;

public class AdvertisementVo {

    private int idAd;
    private int idUs;
    private int idVe;
    private LocalDate date;
    private String description;
    private double price;
    private String images;
    private String province;


    public AdvertisementVo(){
    }

    //Getters
    public int getIdAd(){
        return this.idAd;
    }

    public int getIdUs(){
        return this.idUs;
    }

    public int getIdVe(){
        return this.idVe;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public String getDescription(){
        return this.description;
    }

    public double getPrice(){
        return this.price;
    }

    public String getImages(){
        return this.images;
    }

    public String getProvince(){
        return this.province;
    }

    //Setters
    public void setIdAd(int idAd){
        this.idAd = idAd;
    }

    public void setIdUs(int idUs){
        this.idUs = idUs;
    }

    public void setIdVe(int idVe){
        this.idVe = idVe;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setPrecio(double price){
        this.price = price;
    }

    public void getPriceBusq (String price){
        this.price = Double.parseDouble(price);
    }

    public void setImages(String images){
        this.images = images;
    }

    public void setProvince(String province){
        this.province = province;
    }
}
