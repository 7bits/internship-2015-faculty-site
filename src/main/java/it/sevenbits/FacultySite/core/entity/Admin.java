package it.sevenbits.FacultySite.core.entity;

/**
 * Created by igodyaev on 28.07.15.
 */
//package com.devcolibri.secure.entity;

public class Admin {

    private String login;
    private String password;

    public Admin(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Admin() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}