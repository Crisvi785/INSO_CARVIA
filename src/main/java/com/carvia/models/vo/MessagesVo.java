package com.carvia.models.vo;

import java.time.LocalDate;

public class MessagesVo {
    private int idMes;
    private int idUs;
    private String text;
    private LocalDate date; 

    public MessagesVo(){

    }
    /* 
    public MessagesVo(int idMes, int idUs, String text, String date) {
        this.idMes = idMes;
        this.idUs = idUs;
        this.text = text;
        this.date = date;
    }

    public MessagesVo(int idUs, String text, String date) {
        this.idUs = idUs;
        this.text = text;
        this.date = date;
    }
    */

    public int getIdMes() {
        return idMes;
    }

    public void setIdMes(int idMes) {
        this.idMes = idMes;
    }

    public int getIdUs() {
        return idUs;
    }

    public void setIdUs(int idUs) {
        this.idUs = idUs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

