package com.carvia.models.vo;
import java.time.LocalDate;

import javafx.scene.image.Image;

public class AnuncioVo {
    //Fecha, desc, precio, fotos
    public int id;
    public LocalDate fecha;
    public String descripcion;
    public double precio;
    public String urlFoto;
    private int idVehiculo; // ID del vehículo (clave foránea)
    private int idUsuario;

    public AnuncioVo(){

    }
    
    public AnuncioVo(double precio){
        this.precio = precio; //ESTO ES UN VTO
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

    public void getPrecioBusq (String precio){
        this.precio = Double.parseDouble(precio);
    }

    public void setUrl(String url){
        this.urlFoto = url;
    }

    public int getIdVehiculo() {
        return idVehiculo; // Getter para idVehiculo
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo; // Setter para idVehiculo
    }

    public int getIdUsuario(){
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }
}
