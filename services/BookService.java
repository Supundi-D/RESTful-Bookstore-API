/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

import com.supundi.bookstoresample.model.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author supundidinaya
 */

public class BookService {
    private static Map<Integer,Book> books = new HashMap<>();
    private static int bookId = 0;
    
    //Add a new book
    public Book addBook(Book book) {
        book.setBookId(bookId++);
        books.put(book.getBookId(),book);
        return book;
    }
    
    //Get all books
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }
    
    //Get book by ID
    public Book getBook(int bookId) {
        return books.get(bookId);
    }
    
    //Update a book
    public Book updateBook(int bookId, Book updatedBook) {
        if(books.containsKey(bookId)) {
            updatedBook.setBookId(bookId);
            books.put(bookId, updatedBook);
            return updatedBook;
        }
        return null;
    }
    
    //Delete a book
    public boolean deleteBook(int bookId) {
        return books.remove(bookId) != null;
    }
}