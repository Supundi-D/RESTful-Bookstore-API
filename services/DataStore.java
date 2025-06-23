/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

import com.supundi.bookstoresample.model.Author;
import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.model.Cart;
import com.supundi.bookstoresample.model.Customer;
import com.supundi.bookstoresample.model.Order;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author supundidinaya
 */
public class DataStore {
    public static Map<Integer,Book> books = new HashMap<>();
    public static Map<Integer, Author> authors = new HashMap<>();
    public static Map<Integer, Customer> customers = new HashMap<>();
    public static Map<Integer, Cart> carts = new HashMap<>();
    public static Map<Integer, Order> orders = new HashMap<>();
}
    