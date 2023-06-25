package com.am.preguntas_backend.logic;

import java.io.Serializable;

public class User extends Contact implements Serializable{
    String password;
    String rol;
    
    public User() {
        super();
    }

    public User(String email, String name,String password,String rol) {
        super(email, name);
        this.password = password;
        this.rol=rol;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
