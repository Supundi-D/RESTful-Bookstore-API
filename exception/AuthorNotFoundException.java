/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.supundi.bookstoresample.exception;

/**
 *
 * @author supundidinaya
 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException() {
        super("Author not found");
    }
    
}
