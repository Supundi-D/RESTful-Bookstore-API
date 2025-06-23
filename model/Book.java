/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.model;

/**
 *
 * @author supundidinaya
 */
public class Book {
    private int bookId;
    private String title;
    private String author;
    private String ISBN;
    private String publication_yr;
    private Double price;
    private int stock;
    private int auth_id;
    
    public Book() {}
    
    public Book(int bookId, String title, String author, String ISBN, String publication_yr, Double price, int stock) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publication_yr = publication_yr;
        this.price = price;
        this.stock = stock;
    }
    
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    
    public String getPublication_yr() {
        return publication_yr;
    }
    public void setPublication_yr(String publication_yr) {
        this.publication_yr = publication_yr;
    }
    
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public int getAuth_id() {
        return auth_id;
    }
    public void setAuth_id(int auth_id) {
        this.auth_id = auth_id;
    }
}