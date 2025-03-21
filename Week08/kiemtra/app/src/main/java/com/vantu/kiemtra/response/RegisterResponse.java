package com.vantu.kiemtra.response;

public class RegisterResponse {
    private String userName;
    private String email;

    public RegisterResponse(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
