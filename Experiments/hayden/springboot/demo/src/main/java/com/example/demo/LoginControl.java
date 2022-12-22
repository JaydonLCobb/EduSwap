package com.example.demo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Basic unsecured login controller.
 * 
 * @author Hayden Northwick
 * @see LoginModelService
 */
@RestController(value = "/api")
public class LoginControl {

    private final LoginModelService loginService;

    public LoginControl(LoginModelService service) {
        this.loginService = service;
    }

    /**
     * Takes credentials and compares them against the DB.
     * 
     * @param credentials username and password object
     * @return http status and an updated credentials (if log in successful)
     * @see LoginModel
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login success"),
            @ApiResponse(responseCode = "403", description = "Login failure") })
    @PostMapping(path = "/login")
    public ResponseEntity<LoginModel> attemptLogin(@Valid @RequestBody LoginModel credentials) {
        try {
            LoginModel attempt = loginService.findByUsername(credentials.getUsername());
            // success
            return new ResponseEntity<LoginModel>(attempt, HttpStatus.OK);
        } catch (Exception e) {
            // failed
            return new ResponseEntity<LoginModel>(credentials, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Creates a new user based on the credentials
     * 
     * @param credentials a username and password object
     * @return an http status and a string if successful
     * @see LoginModel
     */
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "403", description = "User creation failed") })
    @PostMapping(value = "/new-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody LoginModel credentials) {
        try {
            loginService.findByUsername(credentials.getUsername());
            // exists
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            // does not exist, create
            LoginModel newUser = loginService.save(credentials);
            return new ResponseEntity<String>(newUser.getUsername() + " created", HttpStatus.CREATED);
        }
    }

    /**
     * Returns all logins. For testing/demo purposes
     * 
     * @return a list of all login accounts
     * @see LoginModel
     */
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<LoginModel> all() {
        return loginService.findAll();
    }

    /**
     * Updates an entry for testing/demo purposes
     * 
     * @param login credentials to update to
     * @param id    id to update
     * @throws DataDoesNotExistException
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/update/{id}")
    public void update(@RequestBody LoginModel login, @PathVariable("id") long id) throws DataDoesNotExistException {
        if (!loginService.update(login, id)) {
            throw new DataDoesNotExistException(id);
        }
    }

    /**
     * Deletes an entry for testing/demo purposes
     * 
     * @param id id to delete
     * @throws DataDoesNotExistException
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") long id) throws DataDoesNotExistException {
        loginService.delete(id);
    }

}
