package EduSwapApplication.Transactions;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import EduSwapApplication.Users.User;

@Entity
public class Transaction {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    private String timestamp;
    private int amount;

    public Transaction() {
    }

    public Transaction(int id, String timestamp, int amount) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
