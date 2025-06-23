/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.model;

/**
 *
 * @author supundidinaya
 */
public class Cart {
    private int cartItemId;
    private Book book;
    private int quantity;

    public Cart() {
    }

    public Cart(int cartItemId, Book book, int quantity) {
        this.cartItemId = cartItemId;
        this.book = book;
        this.quantity = quantity;
    }
    
    public Cart(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }


    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        if (book != null) {
            return book.getPrice() * quantity;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + cartItemId +
                ", book=" + book +
                ", quantity=" + quantity +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}