/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.resources;

import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.model.Cart;
import com.supundi.bookstoresample.services.BookService;
import com.supundi.bookstoresample.services.CartService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author supundidinaya
 */
@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    // Simulate customer carts using a Map<customerId, CartService>
    private static Map<Integer, CartService> customerCarts = new ConcurrentHashMap<>();

    private BookService bookService = new BookService(); // Dummy service for demo

    private CartService getCartForCustomer(int customerId) {
        return customerCarts.computeIfAbsent(customerId, id -> new CartService());
    }

    @GET
    public List<Cart> getCart(@PathParam("customerId") int customerId) {
        return getCartForCustomer(customerId).getCartItems();
    }

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") int customerId,
                                  @QueryParam("bookId") int bookId,
                                  @QueryParam("quantity") int quantity) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        getCartForCustomer(customerId).addToCart(book, quantity);
        return Response.status(Response.Status.CREATED).entity("Book added to cart").build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateItemQuantity(@PathParam("customerId") int customerId,
                                       @PathParam("bookId") int bookId,
                                       @QueryParam("quantity") int quantity) {
        Book book = bookService.getBook(bookId);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Book not found").build();
        }
        CartService cart = getCartForCustomer(customerId);
        cart.removeFromCart(bookId);
        cart.addToCart(book, quantity);
        return Response.ok().entity("Cart updated").build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response deleteCartItem(@PathParam("customerId") int customerId,
                                   @PathParam("bookId") int bookId) {
        getCartForCustomer(customerId).removeFromCart(bookId);
        return Response.ok().entity("Item removed from cart").build();
    }
}