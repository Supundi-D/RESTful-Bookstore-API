/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.resources;

import com.supundi.bookstoresample.exception.BookNotFoundException;
import com.supundi.bookstoresample.model.Book;
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
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookResource {
    
    @GET
    public List<Book> getAllBooks() {
        return new ArrayList<>(DataStore.books.values());
    }
    
    @POST
    public Response createBook(Book book) {
        int bookId = DataStore.books.size() + 1;
        book.setBookId(bookId);
        DataStore.books.put(bookId, book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @GET
    @Path("/{bookId}")
    public Book getBook(@PathParam("bookId") int bookId) {
        Book book = DataStore.books.get(bookId);
        if(book == null) throw new BookNotFoundException();
        return book;
    }
    
    @PUT
    @Path("/{bookId}")
    public Book updateBook(@PathParam("bookId") int bookId, Book book) {
        if(!DataStore.books.containsKey(bookId)) throw new BookNotFoundException();
        book.setBookId(bookId);
        DataStore.books.put(bookId, book);
        return book;
    }
    
    @DELETE
    @Path("/{bookId}")
    public Response deleteBook(@PathParam("bookId") int bookId) {
        if(!DataStore.books.containsKey(bookId)) throw new BookNotFoundException();
        DataStore.books.remove(bookId);
        return Response.noContent().build();
    }
}
