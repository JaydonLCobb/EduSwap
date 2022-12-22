package EduSwapApplication.Reviews;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EduSwapApplication.Users.User;
import EduSwapApplication.Users.UserRepository;

@RestController
public class ReviewController {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/reviews")
    List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping(path = "/reviews/{id}")
    Review getReviewById(@PathVariable int id) {
        return reviewRepository.findById(id);
    }

    @PostMapping(path = "/reviews/{revieweeId}/{reviewerId}")
    String createReview(@RequestBody Review review, @PathVariable int revieweeId, @PathVariable int reviewerId) {
        if (review == null)
            return failure;
        User reviewee = userRepository.findById(revieweeId);
        User reviewer = userRepository.findById(reviewerId);
        if (reviewee == null || reviewer == null)
            return failure;
        review.setReveiwee(reviewee);
        review.setReviewer(reviewer);
        reviewRepository.save(review);
        return success;
    }

    @PutMapping("/reviews/{id}")
    Review updateReview(@PathVariable int id, @RequestBody Review request) {
        Review review = reviewRepository.findById(id);
        if (review == null)
            return null;
        reviewRepository.save(request);
        return reviewRepository.findById(id);
    }

    @GetMapping(path = "/reviews/reviewee/{username}")
    List<Review> getReviewBySender(@PathVariable String username) {
        User reviewee = userRepository.findByUsername(username);
        return reviewRepository.findByReviewee(reviewee);
    }

    @GetMapping(path = "/reviews/reviewer/{username}")
    List<Review> getReviewByReceiver(@PathVariable String username) {
        User reviewer = userRepository.findByUsername(username);
        return reviewRepository.findByReviewer(reviewer);
    }

}
