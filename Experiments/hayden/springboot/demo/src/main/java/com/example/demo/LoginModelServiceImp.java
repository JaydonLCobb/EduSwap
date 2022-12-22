package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that manages a login repo
 * 
 * @author Hayden Northwick
 * @see LoginModelRepo
 * @see LoginModel
 */
@Service
public class LoginModelServiceImp implements LoginModelService {
    @Autowired
    private LoginModelRepo loginRepo;

    @Override
    public LoginModel save(LoginModel l) {
        return loginRepo.save(l);
    }

    @Override
    public LoginModel findById(long id) throws DataDoesNotExistException {
        Optional<LoginModel> result = loginRepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new DataDoesNotExistException(id);
        }
    }

    @Override
    // bad way to do this
    public LoginModel findByUsername(String u) throws DataDoesNotExistException {
        Iterable<LoginModel> all = loginRepo.findAll();
        for (LoginModel l : all) {
            if (l.getUsername().compareTo(u) == 0) {
                return l;
            }
        }
        throw new DataDoesNotExistException(u);
    }

    public List<LoginModel> findAll() {
        return loginRepo.findAll();
    }

    @Override
    public boolean update(LoginModel l, long id) {
        try {
            // lazy way
            LoginModel toUpdate = loginRepo.findById(id).get();
            toUpdate.setUsername(l.getUsername());
            toUpdate.setPassword(l.getPassword());
            loginRepo.save(toUpdate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void delete(long id) throws DataDoesNotExistException {
        loginRepo.deleteById(id);
    }

}
