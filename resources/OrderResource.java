/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.resources;

/**
 *
 * @author supundidinaya
 */

import com.supundi.bookstoresample.model.Order;
import com.supundi.bookstoresample.services.OrderService;
import com.supundi.bookstoresample.exception.OrderException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * RESTful API for Order operations.
 */
@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService orderService = new OrderService();

    @GET
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") int id) throws OrderException {
        return orderService.getOrder(id);
    }

    @POST
    public Response createOrder(Order order) {
        Order createdOrder = orderService.addOrder(order);
        return Response.status(Response.Status.CREATED).entity(createdOrder).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) throws OrderException {
        orderService.deleteOrder(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
