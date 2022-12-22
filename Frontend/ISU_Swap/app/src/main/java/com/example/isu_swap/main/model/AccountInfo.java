/**
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.model;

public class AccountInfo {
    private int id;
    private String fname;
    private String lname;
    private int pictureId;
    private String birthDate;

    /**
     * @return this AccountInfo's id
     */
    public int getId() {
        return id;
    }

    /**
     * @return this AccountInfo's first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * @return this AccountInfo's last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * @return this AccountInfo's picture ID
     */
    public int getPictureId() {
        return pictureId;
    }

    /**
     * @return this AccountInfo's birth date
     */
    public String getBirthDate() {
        return birthDate;
    }
}