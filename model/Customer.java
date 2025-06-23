/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author supundidinaya
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    private int cusId;
    private String cus_name;
    private String email;
    private String pswrd;
    
    
    public Customer(int cusId, String cus_name, String email, String pswrd) {
        this.cusId = cusId;
        this.cus_name = cus_name;
        this.email = email;
        this.pswrd = pswrd;
    }
    
    public Customer() {}
    
    public int getCusId() {
        return cusId;
    }
    public void setCusId(int cusId) {
        this.cusId = cusId;
    }
    
    public String getCus_name() {
        return cus_name;
    }
    public void setCus_name(String name) {
        this.cus_name = name;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPswrd() {
        return pswrd;
    }
    public void setPswrd(String pswrd) {
        this.pswrd = pswrd;
    }
}
