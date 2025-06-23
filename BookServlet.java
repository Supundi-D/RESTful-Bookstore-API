/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.services.DataStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author supundidinaya
 */


public class BookServlet extends HttpServlet {
   
    
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Set response type
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // Get the part of the URL after "/api/books/"
    String pathInfo = request.getPathInfo(); // e.g., "/1" if accessing /api/books/1

    ObjectMapper mapper = new ObjectMapper();

    if (pathInfo == null || pathInfo.equals("/")) {
        // List all books
        Map<String, Object> successResponse = new HashMap<>();
        successResponse.put("message", "Listing all books...");
        successResponse.put("books", DataStore.books.values()); // List of all books

        String jsonOutput = mapper.writeValueAsString(successResponse);
        response.getWriter().println(jsonOutput);
    } else {
        // Retrieve specific book
        String id = pathInfo.substring(1); // Remove "/" from "/1"
        try {
            int bookId = Integer.parseInt(id);
            Book book = DataStore.books.get(bookId);

            if (book != null) {
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("message", "Book retrieved successfully!");
                successResponse.put("book", book);

                String jsonOutput = mapper.writeValueAsString(successResponse);
                response.getWriter().println(jsonOutput);
            } else {
                // Book not found
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Book not found with ID: " + bookId);

                String jsonOutput = mapper.writeValueAsString(errorResponse);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println(jsonOutput);
            }
        } catch (NumberFormatException e) {
            // Invalid ID format
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid book ID format.");

            String jsonOutput = mapper.writeValueAsString(errorResponse);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(jsonOutput);
        }
    }
}

    
  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    // Set response type
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // Step 1: Read JSON input
    BufferedReader reader = request.getReader();
    StringBuilder jsonBuilder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line);
    }
    String jsonInput = jsonBuilder.toString();

    // Step 2: Convert JSON to Book object
    ObjectMapper mapper = new ObjectMapper();
    Book book = mapper.readValue(jsonInput, Book.class);

    // Step 3: Add book to datastore
    int bookId = DataStore.books.size() + 1;
    book.setBookId(bookId);
    DataStore.books.put(bookId, book);

    // Step 4: Create a success message
    Map<String, Object> successResponse = new HashMap<>();
    successResponse.put("message", "Book added successfully!");
    successResponse.put("book", book); // Also send back the created book info

    // Step 5: Convert success response to JSON and send back
    String jsonOutput = mapper.writeValueAsString(successResponse);
    response.getWriter().println(jsonOutput);
}


@Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Set response type
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String pathInfo = request.getPathInfo(); // e.g., "/1"
    ObjectMapper mapper = new ObjectMapper();

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Book ID is missing in the URL.\"}");
        return;
    }

    String bookIdStr = pathInfo.substring(1); // Remove leading "/"

    try {
        int bookId = Integer.parseInt(bookIdStr);
        Book existingBook = DataStore.books.get(bookId);

        if (existingBook != null) {
            // Read JSON input (new book data)
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            String jsonInput = jsonBuilder.toString();

            // Convert input JSON into a temporary Book object
            Book updatedBookData = mapper.readValue(jsonInput, Book.class);

            // Update existing book fields
            existingBook.setTitle(updatedBookData.getTitle());
            existingBook.setAuthor(updatedBookData.getAuthor());
            existingBook.setISBN(updatedBookData.getISBN());
            existingBook.setPublication_yr(updatedBookData.getPublication_yr());
            existingBook.setPrice(updatedBookData.getPrice());
            existingBook.setStock(updatedBookData.getStock());
            existingBook.setAuth_id(updatedBookData.getAuth_id());

            // Prepare success response
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("message", "Book updated successfully!");
            successResponse.put("book", existingBook);

            String jsonOutput = mapper.writeValueAsString(successResponse);
            response.getWriter().println(jsonOutput);
        } else {
            // Book not found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\": \"Book not found with ID: " + bookId + "\"}");
        }
    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Invalid book ID format.\"}");
    }
}


@Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String pathInfo = request.getPathInfo();  // e.g., /1 for /api/books/1
    response.setContentType("application/json");

    if (pathInfo != null && pathInfo.length() > 1) {
        try {
            int bookId = Integer.parseInt(pathInfo.substring(1)); // Convert ID part to integer

            Book removedBook = DataStore.books.remove(bookId); // Actually remove the book from memory

            if (removedBook != null) {
                // If a book was found and removed
                response.getWriter().println("{\"message\": \"Book with ID " + bookId + " was successfully deleted.\", \"deletedBook\": " + new ObjectMapper().writeValueAsString(removedBook) + "}");
            } else {
                // If no book with such ID
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                response.getWriter().println("{\"error\": \"Book with ID " + bookId + " not found.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
            response.getWriter().println("{\"error\": \"Invalid book ID format.\"}");
        }
    } else {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);  // 400 for bad request
        response.getWriter().println("{\"error\": \"Invalid book ID.\"}");
    }
}




    
   
}