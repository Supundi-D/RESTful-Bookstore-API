/**
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

import com.supundi.bookstoresample.model.Author;
import com.supundi.bookstoresample.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author supundidinaya
 */
public class AuthorService {
    private static Map<Integer,Author> authors = new HashMap<>();
    private static int authId = 0;
    private BookService bookService;
    
    public AuthorService(BookService bookService) {
        this.bookService = bookService;
    }
    
    //Add a new author
    public Author addAuthor(Author auth) {
        auth.setAuth_id(authId++);
        authors.put(auth.getAuth_id(), auth);
        return auth;
    }
    
    //Get all authors
    public List<Author> getAllAuthors() {
        return new ArrayList<>(authors.values());
    }
    
    //Get author by ID
    public Author getAuthor(int auth_id) {
        return authors.get(auth_id);
    }        
    
    //Update author
    public Author updateAuthor(int auth_id, Author updatedAuthor) {
        if (authors.containsKey(auth_id)) {
            updatedAuthor.setAuth_id(auth_id);
            authors.put(auth_id, updatedAuthor);
            return updatedAuthor;
        }
        return null;
    }
    
    //Delete author
    public boolean deleteAuthor(int auth_id) {
        return authors.remove(auth_id) != null;
    }
    
    //Get all the books written by a specific author
      public List<Book> getBooksByAuthorId(int auth_id) {
        return bookService.getAllBooks().stream()
                .filter(book -> book.getAuth_id() == auth_id)
                .collect(Collectors.toList());
    }
} 
