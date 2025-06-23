package com.supundi.bookstoresample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supundi.bookstoresample.model.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/customer/*")
public class CustomerServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        if (getServletContext().getAttribute("customerList") == null) {
            getServletContext().setAttribute("customerList", new ArrayList<Customer>());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Customer> customerList = (List<Customer>) getServletContext().getAttribute("customerList");

        // Read incoming JSON
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Customer newCustomer = objectMapper.readValue(sb.toString(), Customer.class);

        // Add customer to shared list
        customerList.add(newCustomer);

        response.setContentType("application/json");
        response.getWriter().write("{\"message\": \"Customer successfully added!\", \"customer\": " + objectMapper.writeValueAsString(newCustomer) + "}");
    }

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    List<Customer> customerList = (List<Customer>) getServletContext().getAttribute("customerList");

    response.setContentType("application/json");
    ObjectMapper objectMapper = new ObjectMapper();

    String pathInfo = request.getPathInfo(); // Get path after "/customers"
    
    if (pathInfo == null || pathInfo.equals("/")) {
        // No ID given — list all customers
        if (customerList.isEmpty()) {
            response.getWriter().write("{\"message\": \"No customers available.\"}");
        } else {
            response.getWriter().write(objectMapper.writeValueAsString(customerList));
        }
    } else {
        // ID is given — find customer by ID
        String idStr = pathInfo.substring(1); // remove starting "/"
        try {
            int id = Integer.parseInt(idStr);

            Customer foundCustomer = null;
            for (Customer customer : customerList) {
                if (customer.getCusId() == id) {
                    foundCustomer = customer;
                    break;
                }
            }

            if (foundCustomer != null) {
                response.getWriter().write(objectMapper.writeValueAsString(foundCustomer));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"Customer not found.\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid customer ID.\"}");
        }
    }
}


    
    @Override
protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    List<Customer> customerList = (List<Customer>) getServletContext().getAttribute("customerList");
    ObjectMapper objectMapper = new ObjectMapper();

    String pathInfo = request.getPathInfo(); // /customers/{id}

    response.setContentType("application/json");

    if (pathInfo == null || pathInfo.equals("/")) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"message\": \"Customer ID missing in URL.\"}");
        return;
    }

    String idStr = pathInfo.substring(1); // Remove starting "/"
    try {
        int id = Integer.parseInt(idStr);

        Customer updatedCustomer = objectMapper.readValue(request.getReader(), Customer.class);

        boolean updated = false;
        for (Customer customer : customerList) {
            if (customer.getCusId() == id) {
                // Update existing customer details
                customer.setCus_name(updatedCustomer.getCus_name());
                customer.setEmail(updatedCustomer.getEmail());
                customer.setPswrd(updatedCustomer.getPswrd());
                updated = true;
                break;
            }
        }

        if (updated) {
            response.getWriter().write("{\"message\": \"Customer with ID " + id + " updated.\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"message\": \"Customer with ID " + id + " not found.\"}");
        }

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"message\": \"Invalid customer ID.\"}");
    }
}

@Override
protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    List<Customer> customerList = (List<Customer>) getServletContext().getAttribute("customerList");

    String pathInfo = request.getPathInfo();  // e.g., /1
    String cusIdStr = pathInfo != null ? pathInfo.substring(1) : null;

    response.setContentType("application/json");

    if (cusIdStr != null) {
        try {
            int cusId = Integer.parseInt(cusIdStr);

            boolean found = false;
            Iterator<Customer> iterator = customerList.iterator();
            while (iterator.hasNext()) {
                Customer customer = iterator.next();
                if (customer.getCusId() == cusId) {
                    iterator.remove();  // Actually remove the customer
                    found = true;
                    break;
                }
            }

            if (found) {
                response.getWriter().println("{\"message\": \"Customer with ID " + cusId + " deleted.\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println("{\"error\": \"Customer with ID " + cusId + " not found.\"}");
            }

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("{\"error\": \"Invalid customer ID format.\"}");
        }
    } else {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Customer ID missing in URL.\"}");
    }
}


    private Customer getCustomerById(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private List<Customer> getAllCustomers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class customerList {

        private static boolean isEmpty() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public customerList() {
        }
    }


    
   
}