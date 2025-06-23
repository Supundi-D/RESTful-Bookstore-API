/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.exception;

import java.util.Collections;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author supundidinaya
 */
@Provider
public class CustomerNotFoundMapper implements ExceptionMapper<CustomerNotFoundException> {
    public Response toResponse(CustomerNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Collections.singletonMap("error", ex.getMessage()))
                .build();
    }
}