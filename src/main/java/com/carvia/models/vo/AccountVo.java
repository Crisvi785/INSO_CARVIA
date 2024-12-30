package com.carvia.models.vo;

public class AccountVo {
    private int idAc;
    private int idUs;
    private String state;

    public AccountVo(){
        
    }
    /* 
    public AccountVo(int idAc, int idUs, String state) {
        this.idAc = idAc;
        this.idUs = idUs;
        this.state = state;
    }

    public AccountVo(int idUs, String state) {
        this.idUs = idUs;
        this.state = state;
    }
    */

    public int getIdAc() {
        return idAc;
    }

    public void setIdAc(int idAc) {
        this.idAc = idAc;
    }

    public int getIdUs() {
        return idUs;
    }

    public void setIdUs(int idUs) {
        this.idUs = idUs;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
