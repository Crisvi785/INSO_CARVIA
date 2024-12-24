package com.carvia.models.vo;

import com.carvia.models.vo.UserVo;

public class VehicleVo {
    private String marca;          // Marca del vehículo
    private String modelo;         // Modelo del vehículo
    private int anio;             // Año del vehículo
    private String color;          // Color del vehículo
    private int kilometraje;       // Kilometraje del vehículo
    private String tipoCombustible; // Tipo de combustible
    private String transmision;    // Tipo de transmisión
    private String estado;         // Estado del vehículo (disponible, vendido, etc.)
    private String ubicacion;      // Ubicación del vehículo
    private int userId;           // ID del usuario asociado

    public VehicleVo(String marca, String modelo, int anio, String color, int kilometraje, 
                     String tipoCombustible, String transmision, double precio, 
                     String descripcion, String imagenUrl, String estado, 
                     String ubicacion, int userId) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.kilometraje = kilometraje;
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.userId = userId;
    }

    // Getters y setters
    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public String getColor() {
        return color;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public String getTipoCombustible() {
        return tipoCombustible;
    }

    public String getTransmision() {
        return transmision;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }
/*
        public int getUsername Id() {
            return userId;
        }
 * 
 * 
 */

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public void setTipoCombustible(String tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
/*
 * 
 * 
 public void setUser Id(int userId) {
     this.userId = userId;
 }
 * 
 */
}