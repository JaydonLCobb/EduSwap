package EduSwapApplication.Users;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import EduSwapApplication.AccountInfo.AccountInfo;
import EduSwapApplication.Listings.Listing;
import EduSwapApplication.Messages.Message;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
    @OneToOne
    @JsonIgnore
    private AccountInfo accountInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private List<Message> sentMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Message> receivedMessages;

    @JsonIgnore
    @OneToMany(mappedBy = "lister")
    private List<Listing> listings;

    @Id
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
     * Constructor for User
     * 
     * @param id
     * @param username
     * @param password
     * @param type
     * @param email
     * @param isVerified
     * @param creationDate
     * @param lastLogin
     * @param isEnabled
     */
    public User(int id, String username, String password, int type, String email, boolean isVerified,
            String creationDate, String lastLogin, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
        this.isVerified = isVerified;
        this.creationDate = creationDate;
        this.lastLogin = lastLogin;
        this.isEnabled = isEnabled;
    }

    public User() {
    }

    /**
     * Returns the Account info
     * 
     * @return AccountInfo
     */
    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    /**
     * Sets the account info
     * 
     * @param accountInfo
     */
    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    /**
     * Returns the id
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the username
     * 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets the username
     * 
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * returns the password
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the type
     * 
     * @return int
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns isVerified
     * 
     * @return boolean
     */
    public boolean getIsVerified() {
        return isVerified;
    }

    /**
     * sets isVerified
     * 
     * @param isVerified
     */
    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    /**
     * Returns the creation date
     * 
     * @return String
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * sets the creation date
     * 
     * @param creationDate
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns the time of the last login
     * 
     * @return String
     */
    public String getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the time of the last login
     * 
     * @param lastLogin
     */
    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Returns isEnabled
     * 
     * @return boolean
     */
    public boolean getIsEnabled() {
        return isEnabled;
    }

    /**
     * Sets isEnabled
     * 
     * @param isEnabled
     */
    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Sets the recieved messages
     * 
     * @param receivedMessages
     */
    public void setReceivedMessages(List<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    /**
     * returns the received messages
     * 
     * @return List<Message>
     */
    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    /**
     * Sets the sent messages
     * 
     * @param sentMessages
     */
    public void setSentMessages(List<Message> sentMessages) {
        this.sentMessages = sentMessages;
    }

    /**
     * Returns the sent Messages
     * 
     * @return List<Message>
     */
    public List<Message> getSentMessages() {
        return sentMessages;
    }

    /**
     * Add a sent message
     * 
     * @param sentMessage
     */
    public void addSentMessages(Message sentMessage) {
        this.sentMessages.add(sentMessage);
    }

    /**
     * Add a received message
     * 
     * @param receivedMessage
     */
    public void addReceivedMessages(Message receivedMessage) {
        this.receivedMessages.add(receivedMessage);
    }
}
