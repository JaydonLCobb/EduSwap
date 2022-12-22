package EduSwapApplication.Reviews;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import EduSwapApplication.Users.User;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "reviewer")
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "reviewee")
    private User reviewee;

    private int rating;

    private String review;

    public Review() {
    }

    public Review(int id, String reviewer, String reviewee, int rating, String review) {
        this.id = id;
        this.rating = rating;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReveiwee() {
        return reviewee;
    }

    public void setReveiwee(User reviewee) {
        this.reviewee = reviewee;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
