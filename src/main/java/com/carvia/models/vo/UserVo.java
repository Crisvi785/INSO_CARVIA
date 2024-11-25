package com.carvia.models.vo;



public class UserVo{
    private int id;
    private String username;
    private String password;

    public UserVo() {

    }

    public UserVo(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
    }

}
