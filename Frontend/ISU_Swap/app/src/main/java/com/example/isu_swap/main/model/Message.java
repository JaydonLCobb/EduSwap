/**
 *
 * @author Jose Medina Mani
 */
package com.example.isu_swap.main.model;

public class Message {
    private int id;
    private String sender;
    private String receiver;
    private String timestamp;
    private String subject;
    private String message;

    /**
     * @return this Message's ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return this Message's sender username
     */
    public String getSender() {
        return sender;
    }

    /**
     * @return this Message's receiver username
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @return this Message's timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @return this Message's subject header
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @return this Message's body
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param id the ID to set this Message with
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param sender the sender username to set this Message with
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @param receiver the receiver username to set this Message with
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @param timestamp the timestamp to set this Message with
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param subject the subject header to set this Message with
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param message the body to set this Message with
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
