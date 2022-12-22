package EduSwapApplication.AccountInfo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import EduSwapApplication.Users.User;

/**
 * 
 * @author TJ Thielen
 */

@Entity
public class AccountInfo {

    /*
     * The annotation @ID marks the field below as the primary key for the table
     * created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in
     * this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fname;
    private String lname;
    private int pictureId;
    private String birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    /**
     * Constructor for Account Info
     * 
     * @param id
     * @param fname
     * @param lname
     * @param pictureId
     * @param birthDate
     */
    public AccountInfo(int id, String fname, String lname, int pictureId, String birthDate) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.pictureId = pictureId;
        this.birthDate = birthDate;
    }

    public AccountInfo() {
    }

    /**
     * Returns the User
     * 
     * @return User
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the User
     * 
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the Id
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the Id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the birth date
     * 
     * @return String
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date
     * 
     * @param birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns the first name
     * 
     * @return String
     */
    public String getFname() {
        return fname;
    }

    /**
     * Sets the first name
     * 
     * @param fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Returns the last name
     * 
     * @return String
     */
    public String getLname() {
        return lname;
    }

    /**
     * Sets the last name
     * 
     * @param lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Returns the picture Id
     * 
     * @return int
     */
    public int getPictureId() {
        return pictureId;
    }

    /**
     * Sets the picture Id
     * 
     * @param pictureId
     */
    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

}
