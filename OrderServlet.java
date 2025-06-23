/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample;

/**
 *
 * @author supundidinaya
 */

import com.supundi.bookstoresample.model.Order;
import com.supundi.bookstoresample.services.OrderService;
import com.supundi.bookstoresample.exception.OrderException;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


    @WebServlet("/order")
public class OrderServlet extends HttpServlet {
    
    private OrderService orderService = new OrderService();  // Ensure this is properly instantiated
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String orderIdParam = request.getParameter("id");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        if (orderIdParam != null) {
            // Get specific order by ID
            try {
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderService.getOrder(orderId);
                
                if (order == null) {
                    throw new OrderException("Order with ID " + orderId + " not found.");
                }
                
                out.println("<h1>Order Details</h1>");
                out.println("<pre>" + order.toString() + "</pre>");
                
            } catch (NumberFormatException e) {
                out.println("<h1>Invalid Order ID format</h1>");
            } catch (OrderException e) {
                out.println("<h1>" + e.getMessage() + "</h1>");
            }
        } else {
            // List all orders
            List<Order> orders = orderService.getAllOrders();
            out.println("<h1>All Orders</h1>");
            
            if (orders.isEmpty()) {
                out.println("<p>No orders available.</p>");
            } else {
                for (Order order : orders) {
                    out.println("<pre>" + order.toString() + "</pre><br><br>");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle new Order creation
        String customerName = request.getParameter("cus_name");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (customerName == null || customerName.isEmpty()) {
            out.println("<h1>Customer Name is required to create an order.</h1>");
            return;
        }

        Order order = new Order();
        order.setCus_name(customerName);

        orderService.addOrder(order);

        out.println("<h1>Order Created Successfully</h1>");
        out.println(order.toString());
    }
}
