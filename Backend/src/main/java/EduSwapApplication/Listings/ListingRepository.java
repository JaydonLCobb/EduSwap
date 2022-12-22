package EduSwapApplication.Listings;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import EduSwapApplication.Users.User;

public interface ListingRepository extends JpaRepository<Listing, Long> {
    Listing findById(int id);

    List<Listing> findByLister(User sender);

    @Transactional
    void deleteById(int id);

}
