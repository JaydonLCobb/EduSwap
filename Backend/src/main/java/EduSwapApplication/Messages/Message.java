package EduSwapApplication.Messages;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import EduSwapApplication.Users.User;

@Entity
public class Message {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String timestamp;
    private String subject;

    public Message() {
    }

    /**
     * Constructor for Message
     * 
     * @param id
     * @param timestamp
     * @param subject
     */
    public Message(int id, String timestamp, String subject) {
        this.id = id;
        this.timestamp = timestamp;
        this.subject = subject;
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
     * Returns the user that sent the message
     * 
     * @return User
     */
    public User getSender() {
        return sender;
    }

    /**
     * Set the User that sent the message
     * 
     * @param sender
     */
    public void setSender(User sender) {
        this.sender = sender;
    }

    /**
     * Get the User that received the message
     * 
     * @return User
     */
    public User getReceiver() {
        return receiver;
    }

    /**
     * Set the user that received the message
     * 
     * @param receiver
     */
    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    /**
     * Get the Timestamp
     * 
     * @return String
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * set the Timestamp
     * 
     * @param timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Get the subject
     * 
     * @return String
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set the subject
     * 
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
