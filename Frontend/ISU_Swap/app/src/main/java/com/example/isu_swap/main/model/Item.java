/**
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.model;

public class Item {
    private int id;
    private User lister;
    private String title;
    private String description;
    private String price;
    private String image;

    public Item() {}

    public Item(int id, String title, String price, int sellerId, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    /**
     * @return this Item's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the ID to set this Item with
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return this Item's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set this Item with
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return this Item's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set this Item with
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return this Item's price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set this Item with
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return this Item's image URL
     */
    public String getImage() {
        return image;
    }

    /**
     * @param images the image URL to set this Item with
     */
    public void setImage(String images) {
        this.image = images;
    }

    /**
     * @return this Item's lister username
     */
    public User getLister() { return lister; }

    /**
     * @param lister the lister username to set this Item with
     */
    public void setLister(User lister) { this.lister = lister; }
}