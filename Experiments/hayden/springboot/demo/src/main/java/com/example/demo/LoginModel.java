package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class that represents the credentials of a user.
 * 
 * @author Hayden Northwick
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "LOGINS", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class LoginModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
