package com.supundi.bookstoresample.model;

import java.util.List;

public class Author {
    private int auth_id;
    private String auth_name;
    private String biography;
    

    public Author(int auth_id, String auth_name, String biography) {
        this.auth_id = auth_id;
        this.auth_name = auth_name;
        this.biography = biography;
    }

    public int getAuth_id() {
        return auth_id;
    }
    public void setAuth_id(int auth_id) {
        this.auth_id = auth_id;
    }

    public String getAuth_name() {
        return auth_name;
    }
    public void setAuth_name(String name) {
        this.auth_name = name;
    }

    public String getBiography() {
        return biography;
    }
    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Book> getBooks() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getAuthId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
