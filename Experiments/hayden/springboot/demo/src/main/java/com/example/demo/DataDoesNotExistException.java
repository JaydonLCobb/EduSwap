package com.example.demo;

/**
 * basic error
 * 
 * @author Hayden Northwick
 */
public class DataDoesNotExistException extends Exception {
    DataDoesNotExistException(long id) {
        super("No data found matching search at id: " + id);
    }

    DataDoesNotExistException(String s) {
        super("No data found matching search for username: " + s);
    }
}
