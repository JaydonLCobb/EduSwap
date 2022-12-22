package com.example.demo;

import java.util.List;

/**
 * Simple interface for service implimentation
 * 
 * @author Hayden Northwick
 * @see LoginModelServiceImp
 */
public interface LoginModelService {
    public LoginModel save(LoginModel l);

    public LoginModel findById(long id) throws DataDoesNotExistException;

    public LoginModel findByUsername(String u) throws DataDoesNotExistException;

    public List<LoginModel> findAll();

    public boolean update(LoginModel l, long id);

    public void delete(long id) throws DataDoesNotExistException;
}
