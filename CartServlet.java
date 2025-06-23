/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supundi.bookstoresample.model.Book;
import com.supundi.bookstoresample.model.Cart;
import com.supundi.bookstoresample.services.BookService;
import com.supundi.bookstoresample.services.CartService;
import java.io.IOException;
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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final Map<Integer, CartService> customerCarts = new HashMap<>();
    private final BookService bookService = new BookService();
    private final ObjectMapper objectMapper = new ObjectMapper(); // for JSON conversion

    private CartService getCartForCustomer(int customerId) {
        return customerCarts.computeIfAbsent(customerId, id -> new CartService());
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String pathInfo = request.getPathInfo(); // Should be /1/cart
    response.setContentType("application/json");

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Customer ID is required in the URL.\"}");
        return;
    }

    try {
        String[] pathParts = pathInfo.split("/");
        
        // Check if pathParts has enough parts
        if (pathParts.length < 3) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Invalid URL format. Expected /{customerId}/cart\"}");
            return;
        }

        // Check if the customer ID is a valid number
        int customerId;
        try {
            customerId = Integer.parseInt(pathParts[1]);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Customer ID must be a number.\"}");
            return;
        }

        String lastPart = pathParts[2];

        if (!"cart".equals(lastPart)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Expected 'cart' after customer ID.\"}");
            return;
        }

        // Assuming CartService and getOrCreateCartForCustomer are properly defined
        CartService cartService = getOrCreateCartForCustomer(customerId);
        List<Cart> cartItems = cartService.getCartItems();

        if (cartItems.isEmpty()) {
            response.getWriter().println("{\"message\": \"Cart is empty.\"}");
        } else {
            String json = objectMapper.writeValueAsString(cartItems);
            response.getWriter().println(json);
        }
    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().println("{\"error\": \"An unexpected error occurred.\"}");
    }
}


    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String pathInfo = request.getPathInfo(); // e.g., /1/cart/items
    response.setContentType("application/json");

    try {
        String[] pathParts = pathInfo.split("/");
        int customerId = Integer.parseInt(pathParts[1]);
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Ensure that a cart is created for the customer if it does not exist
        CartService cartService = getCartForCustomer(customerId);

        // Retrieve the book details
        Book book = bookService.getBook(bookId);
        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\": \"Book not found.\"}");
            return;
        }

        // Add the book to the cart
        cartService.addToCart(book, quantity);

        // Send response that the book was added to the cart
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.getWriter().println("{\"message\": \"Book added to cart.\"}");

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Invalid input parameters.\"}");
    }
}


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // e.g., /1/cart/items/10
        response.setContentType("application/json");

        try {
            String[] pathParts = pathInfo.split("/");
            int customerId = Integer.parseInt(pathParts[1]);
            int bookId = Integer.parseInt(pathParts[3]);
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Book book = bookService.getBook(bookId);
            if (book == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"error\": \"Book not found.\"}");
                return;
            }

            CartService cart = getCartForCustomer(customerId);
            cart.removeFromCart(bookId);
            cart.addToCart(book, quantity);

            response.getWriter().println("{\"message\": \"Cart updated.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Invalid input or path.\"}");
        }
    }

    @Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String pathInfo = request.getPathInfo(); // e.g., /customers/1/cart/items/10
    response.setContentType("application/json");

    try {
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Customer ID and Book ID are required in the URL.\"}");
            return;
        }

        // Split the path parts and extract the customerId and bookId
        String[] pathParts = pathInfo.split("/");

        // Ensure the correct number of parts
        if (pathParts.length < 4) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Invalid path. Expected format: /customers/{customerId}/cart/items/{bookId}\"}");
            return;
        }

        // Parse customerId and bookId
        int customerId = Integer.parseInt(pathParts[1]);  // /customers/{customerId}
        int bookId = Integer.parseInt(pathParts[3]);      // /cart/items/{bookId}

        // Check if the customer has a cart, if not, return error
        CartService cartService = getCartForCustomer(customerId);
        if (cartService == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("{\"error\": \"Customer cart not found.\"}");
            return;
        }

        // Remove the item from the cart
        cartService.removeFromCart(bookId);

        // Respond with success message
        response.getWriter().println("{\"message\": \"Item removed from cart.\"}");

    } catch (Exception e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Invalid input or path.\"}");
    }
}


    private CartService getOrCreateCartForCustomer(int customerId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}