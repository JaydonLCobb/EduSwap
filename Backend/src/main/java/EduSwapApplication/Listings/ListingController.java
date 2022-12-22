package EduSwapApplication.Listings;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EduSwapApplication.Users.User;
import EduSwapApplication.Users.UserRepository;

@RestController
public class ListingController {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"listing\":\"success\"}";
    private String failure = "{\"listing\":\"failure\"}";

    
    /** 
     * Gets all listings
     * @return List<Listing>
     */
    @GetMapping(path = "/listings")
    List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    
    /** 
     * creates a listing from the user with username {username}
     * @param listing
     * @param username
     * @return String
     */
    @PostMapping(path = "/listings/users/{username}")
    String createListing(@RequestBody Listing listing, @PathVariable String username) {
        if (listing == null)
            return failure;
        User lister = userRepository.findByUsername(username);
        if (lister == null)
            return failure;
        listing.setLister(lister);
        listingRepository.save(listing);
        return success;
    }

    
    /** 
     * Updates the listing with id {id}
     * @param id
     * @param request
     * @return Listing
     */
    @PutMapping("/listings/{id}")
    Listing updateListing(@PathVariable int id, @RequestBody Listing request) {
        Listing listing = listingRepository.findById(id);
        if (listing == null)
            return null;
        listingRepository.save(request);
        return listingRepository.findById(id);
    }

    
    /** 
     * Gets the listing by a user with username {username}
     * @param username
     * @return List<Listing>
     */
    @GetMapping(path = "/listings/users/{username}")
    List<Listing> getListingsByLister(@PathVariable String username) {
        User lister = userRepository.findByUsername(username);
        return listingRepository.findByLister(lister);
    }

    
    /** 
     * Gets all listings by user with id {id}
     * @param id
     * @return Listing
     */
    @GetMapping(path = "/listings/{id}")
    Listing getListingsByLister(@PathVariable int id) {
        return listingRepository.findById(id);
    }

    
    /** 
     * Deletes a listing with id {id}
     * @param id
     * @return String
     */
    @DeleteMapping(path = "/listings/{id}")
    String deleteListing(@PathVariable int id) {
        listingRepository.deleteById(id);
        return success;
    }
}
