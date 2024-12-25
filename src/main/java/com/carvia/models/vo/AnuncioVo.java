package com.carvia.models.vo;
import java.time.LocalDate;

public class AnuncioVo {
    //Fecha, desc, precio, fotos
    public int id;
    public LocalDate fecha;
    public String descripcion;
    public double precio;
    public String urlFoto;

    public AnuncioVo(){
    }

    //Getters
    public int getId(){
        return id;
    }

    public LocalDate getFecha(){
        return fecha;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public double getPrecio(){
        return precio;
    }

    public String getUrlFoto(){
        return urlFoto;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public void setUrl(String url){
        this.urlFoto = url;
    }
}
