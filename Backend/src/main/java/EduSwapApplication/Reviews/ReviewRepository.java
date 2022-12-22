package EduSwapApplication.Reviews;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import EduSwapApplication.Users.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findById(int id);

    List<Review> findByReviewee(User reviewee);

    List<Review> findByReviewer(User reviewer);
}
