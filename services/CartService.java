/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.model.Cart;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author supundidinaya
 */

public class CartService {

    private Map<Integer, Cart> cartItems = new HashMap<>();

    // Add book to cart
    public void addToCart(Book book, int quantity) {
        if (cartItems.containsKey(book.getBookId())) {
            Cart existingItem = cartItems.get(book.getBookId());
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            Cart item = new Cart(book, quantity);
            cartItems.put(book.getBookId(), item);
        }
    }

    // Remove book from cart
    public void removeFromCart(int bookId) {
        cartItems.remove(bookId);
    }

    // Get all cart items
    public List<Cart> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    // Clear cart (optional utility)
    public void clearCart() {
        cartItems.clear();
    }

    // Calculate total (optional utility)
    public double getTotalAmount() {
        return cartItems.values().stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
    }
}