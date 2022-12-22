/**
 *
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.model;

public class Image {
    private int id;
    private String link;

    /**
     * @return this Image's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return this Image's link
     */
    public String getLink() {
        return link;
    }
}

/*
    Id (BIGINT F. key)
    Link (VARCHAR 255) link/url to image stored on server
 */
