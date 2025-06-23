/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

/**
 *
 * @author supundidinaya
 */

import com.supundi.bookstoresample.model.Order;
import com.supundi.bookstoresample.exception.OrderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles business logic for Orders.
 */

public class OrderService {
    private Map<Integer, Order> orders = new HashMap<>();
    private int nextOrderId = 1;

    // Add a new Order
    public Order addOrder(Order order) {
        order.setOrderId(nextOrderId++);
        orders.put(order.getOrderId(), order);
        return order;
    }

    // Get all Orders
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    // Get a single Order by ID
    public Order getOrder(int orderId) throws OrderException {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new OrderException("Order with ID " + orderId + " not found.");
        }
        return order;
    }

    // Delete an Order by ID
    public void deleteOrder(int orderId) throws OrderException {
        if (!orders.containsKey(orderId)) {
            throw new OrderException("Order with ID " + orderId + " not found.");
        }
        orders.remove(orderId);
    }
}
