/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.resources;

import com.supundi.bookstoresample.exception.CustomerNotFoundException;
import com.supundi.bookstoresample.model.Customer;
import com.supundi.bookstoresample.services.DataStore;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author supundidinaya
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CustomerResource {
    
    @GET
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(DataStore.customers.values());
    }
    
    @POST
    public Response createCustomer(Customer cus) {
        int cusId = DataStore.customers.size() + 1;
        cus.setCusId(cusId);
        DataStore.customers.put(cusId, cus);
        return Response.status(Response.Status.CREATED).entity(cus).build();
    }
    
    @GET
    @Path("/{cusId}")
    public Customer getCustomer(@PathParam("cusId") int cusId) {
        Customer cus = DataStore.customers.get(cusId);
        if (cus == null) throw new CustomerNotFoundException();
        return cus;
    }
    
    @PUT
    @Path("/{cusId}")
    public Customer updateCustomer(@PathParam("cusId") int cusId, Customer cus) {
        if (!DataStore.customers.containsKey(cusId)) throw new CustomerNotFoundException();
        cus.setCusId(cusId);
        DataStore.customers.put(cusId, cus);
        return cus;
    }
    
    @DELETE
    @Path("/{cusId}")
    public Response deleteCustomer(@PathParam("cusId") int cusId) {
        if (!DataStore.customers.containsKey(cusId)) throw new CustomerNotFoundException();
        DataStore.customers.remove(cusId);
        return Response.noContent().build();
    }
}