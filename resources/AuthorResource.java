/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.resources;

import com.supundi.bookstoresample.exception.AuthorNotFoundException;
import com.supundi.bookstoresample.model.Author;
import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.services.AuthorService;
import com.supundi.bookstoresample.services.BookService;
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
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthorResource {
    
    @GET
    public List<Author> getAllAuthors() {
        return new ArrayList<>(DataStore.authors.values());
    }
    
    @POST
    public Response createAuthor(Author auth) {
        int auth_id = DataStore.authors.size() + 1;
        auth.setAuth_id(auth_id);
        DataStore.authors.put(auth_id, auth);
        return Response.status(Response.Status.CREATED).entity(auth).build();
    }
    
    @GET
    @Path("/{auth_id}")
    public Author getAuthorById(@PathParam("auth_id") int auth_id) {
        Author auth = DataStore.authors.get(auth_id);
        if(auth == null) throw new AuthorNotFoundException();
        return auth;
    }
    
    @PUT
    @Path("/{auth_id}")
    public Author updateAuthor(@PathParam("auth_id") int auth_id, Author auth) {
        if(!DataStore.authors.containsKey(auth_id)) throw new AuthorNotFoundException();
        auth.setAuth_id(auth_id);
        DataStore.authors.put(auth_id, auth);
        return auth;
    }
    
    @DELETE
    @Path("/{auth_id}")
    public Response deleteAuthor(@PathParam("auth_id") int auth_id) {
        if (!DataStore.authors.containsKey(auth_id)) throw new AuthorNotFoundException();
        DataStore.authors.remove(auth_id);
        return Response.noContent().build();
    }
    
    @GET
    @Path("/{auth_id}/books")
    public Response getBooksByAuthor(@PathParam("auth_id") int auth_id) {
    if (!DataStore.authors.containsKey(auth_id)) {
        throw new AuthorNotFoundException();
    }
    BookService bookService = new BookService(); 
    AuthorService authService = new AuthorService(bookService); // or use dependency injection if setup
    List<Book> books = (List<Book>) authService.getBooksByAuthorId(auth_id);
    return Response.ok(books).build();
    }
    
}