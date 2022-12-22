/**
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int type;
    private String email;
    private boolean isVerified;
    private String creationDate;
    private String lastLogin;
    private boolean isEnabled;

    /**
     * @return this User's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return this User's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return this User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return this User's account type
     */
    public int getType() {
        return type;
    }

    /**
     * @return this User's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return this User's verified status
     */
    public boolean isVerified() {
        return isVerified;
    }

    /**
     * @return this User's creation date
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * @return this User's last login date
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * @return this User's account status
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * @param id the ID to set this User with
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param username the username to set this User with
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @param password the password to set this User with
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param type the account type to set this User with
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param email the email address to set this User with
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param verified the verified status to set this User with
     */
    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    /**
     * @param creationDate the creation date to set this User with
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @param lastLogin the last login date to set this User with
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * @param enabled the account status to set this User with
     */
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
