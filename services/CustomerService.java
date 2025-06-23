/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.services;

import com.supundi.bookstoresample.model.Customer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author supundidinaya
 */

public class CustomerService {
    private static Map<Integer,Customer> customers = new HashMap<>();
    private static int cusId = 0;
    
    //Add a new customer
    public Customer addCustomer(Customer cus) {
        cus.setCusId(cusId++);
        customers.put(cus.getCusId(), cus);
        return cus;
    }
    
    //Get all customers
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }
    
    //Get customer by ID
    public Customer getCustomer(int cusId) {
        return customers.get(cusId);
    }
    
    //Update a customer
    public Customer updateCustomer(int cusId, Customer updatedCustomer) {
        if (customers.containsKey(cusId)) {
            updatedCustomer.setCusId(cusId);
            customers.put(cusId, updatedCustomer);
            return updatedCustomer;
        }
        return null;
    }
    
    //Delete a customer
    public boolean deleteCustomer(int cusId) {
        return customers.remove(cusId) != null;
    }
}
