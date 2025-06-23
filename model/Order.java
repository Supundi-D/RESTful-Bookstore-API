/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author supundidinaya
 */
public class Order {
    private int orderId;
    private String cus_name;
    private Date orderDate;
    private List<Cart> items;
    private double total;

    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = new Date();
    }

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.cus_name = cus_name;
        this.items = new ArrayList<>();
        this.orderDate = new Date();
        calculateTotal();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public List<Cart> getItems() {
        return items;
    }

    public void setItems(List<Cart> items) {
        this.items = items;
        calculateTotal();
    }

    public void addItem(Cart item) {
        this.items.add(item);
        calculateTotal();
    }

    public double getTotal() {
        return total;
    }

    private void calculateTotal() {
        total = 0;
        for (Cart item : items) {
            total += item.getSubtotal();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + orderId +
                ", customerName='" + cus_name + '\'' +
                ", orderDate=" + orderDate +
                ", items=" + items +
                ", total=" + total +
                '}';
    }
}
