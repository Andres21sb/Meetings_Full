package com.am.preguntas_backend.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meeting implements Serializable{
    User owner;
    String id;
    String title;
    String date;
    String state;
    List<Contact> contacts;

    public Meeting() {
        this.owner = new User();
        this.contacts = new ArrayList<>();
    }

    public Meeting(User owner, String id, String title, String date, String state) {
        this.owner = owner;
        this.id = id;
        this.title = title;
        this.date = date;
        this.state=state;
        this.contacts = new ArrayList<>();
    }
    
    

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
