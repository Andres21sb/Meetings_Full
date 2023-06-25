/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.am.preguntas_backend.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Darkh
 */
public class Cliente {
    private String nombre;
    private String id;
    private String clave;
    private List<Pregunta> preguntas;

    public Cliente(String nombre, String id, String clave) {
        preguntas = new ArrayList<>(); // Inicializar la lista vacía
        this.nombre = nombre;
        this.id = id;
        this.clave = clave;
    }

    public Cliente() {
        preguntas = new ArrayList<>(); // Inicializar la lista vacía
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", id='" + id + '\'' +
                ", clave='" + clave + '\'' +
                '}';
    }

    public boolean iniciarSesion(String id, String clave) {
        return this.id.equals(id) && this.clave.equals(clave);
    }
}
