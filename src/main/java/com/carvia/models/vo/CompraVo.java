package com.carvia.models.vo;
import java.time.LocalDate;

public class CompraVo {
    int id;
    LocalDate fecha;
    
    public CompraVo(int id, LocalDate fecha){
        this.id = id;
        this.fecha = fecha;
    }

    //Getters
    public int getId(){
        return id;
    }

    public LocalDate getFecha(){
        return fecha;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setFecha(LocalDate fecha){
        this.fecha = fecha;
    }
}
