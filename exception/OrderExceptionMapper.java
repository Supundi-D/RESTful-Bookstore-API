/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.exception;

/**
 *
 * @author supundidinaya
 */

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OrderExceptionMapper implements ExceptionMapper<OrderException> {

    @Override
    public Response toResponse(OrderException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                       .entity(exception.getMessage())
                       .build();
    }
}
