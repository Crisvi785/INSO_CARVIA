package com.carvia.models.vo;

public class UserVo {
    private int idUs;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String address;

    public UserVo() {}

    public UserVo(int id, String username, String password) {
        this.idUs = id;
        this.username = username;
        this.password = password;
    }


    public UserVo(int id, String username, String fullName, String email, String password) {
        this.idUs = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public UserVo(String username, String fullName, String email, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return idUs;
    }

    public void setId(int id) {
        this.idUs = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + idUs + ", username=" + username + ", fullName=" + fullName + ", email=" + email + ", password=" + password + "]";
    }
}
