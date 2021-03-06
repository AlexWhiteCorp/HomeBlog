package com.alexcorp.homeblog.rest;

public class RegistrationInfo {

    private String email;
    private String nickname;
    private String password;
    private String password2;
    private String invite;

    public RegistrationInfo() {
    }

    public RegistrationInfo(String email, String nickname, String password, String password2, String invite) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.password2 = password2;
        this.invite = invite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getInvite() {
        return invite;
    }

    public void setInvite(String invite) {
        this.invite = invite;
    }
}
