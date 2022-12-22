package EduSwapApplication.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import EduSwapApplication.AccountInfo.AccountInfo;
import EduSwapApplication.AccountInfo.AccountInfoRepository;

/**
 * 
 * @author TJ Thielen
 * 
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    
    /** 
     * Get a list of all users
     * @return List<User>
     */
    @GetMapping(path = "/users")
    List<User> getAllUsers() {
        return userRepository.findAll();
    }

    
    /** 
     * Get a specific user with id {id}
     * @param id
     * @return User
     */
    @GetMapping(path = "/users/{id}")
    User getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    
    /** 
     * Get a specific user with username {username}
     * @param username
     * @return User
     */
    @GetMapping(path = "/users/username/{username}")
    User getUserById(@PathVariable String username) {
        return userRepository.findByUsername(username);
    }

    
    /** 
     * Create a user
     * @param user
     * @return String
     */
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user) {
        if (user == null)
            return failure;
        userRepository.save(user);
        return success;
    }

    
    /** 
     * update a user with the id {id}
     * @param id
     * @param request
     * @return User
     */
    @PutMapping(path = "/users/{id}")
    User updateUser(@PathVariable int id, @RequestBody User request) {
        User user = userRepository.findById(id);
        if (user == null)
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }

    
    /** 
     * delete the user with id {id}
     * @param id
     * @return String
     */
    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id) {

        // Check if there is an object depending on user and then remove the dependency
        AccountInfo accountInfo = accountInfoRepository.findByuserId(id);
        accountInfo.setUser(null);
        accountInfoRepository.save(accountInfo);

        // delete the laptop if the changes have not been reflected by the above
        // statement
        userRepository.deleteById(id);
        return success;
    }
}
