package EduSwapApplication.Listings;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import EduSwapApplication.Users.User;

@Entity
public class Listing {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "lister")
    private User lister;

    private String title;
    private String description;
    private String price;
    private String image;

    public Listing() {
    }

    /**
     * Constructor for Listing
     * 
     * @param id
     * @param title
     * @param description
     * @param price
     * @param image
     */
    public Listing(int id, String title, String description, String price, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    /**
     * returns the Id
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * sets the Id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the description
     * 
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the image
     * 
     * @return String
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image
     * 
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns the price
     * 
     * @return String
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the Price
     * 
     * @param price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Returns the title
     * 
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the lister
     * 
     * @param lister
     */
    public void setLister(User lister) {
        this.lister = lister;
    }

    /**
     * Gets the Lister
     * 
     * @return User
     */
    public User getLister() {
        return lister;
    }

}
