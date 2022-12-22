package EduSwapApplication.AccountInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import EduSwapApplication.Users.User;
import EduSwapApplication.Users.UserRepository;

/**
 * 
 * @author TJ Thielen
 * 
 */

@RestController
public class AccountInfoController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountInfoRepository accountInfoRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Returns all Account Infos
     * 
     * @return List<AccountInfo>
     */
    @GetMapping(path = "/accountInfos")
    List<AccountInfo> getAllAccountInfos() {
        return accountInfoRepository.findAll();
    }

    /**
     * Returns Account info with id {id}
     * 
     * @param id
     * @return AccountInfo
     */
    @GetMapping(path = "/accountInfos/{id}")
    AccountInfo getAccountInfoById(@PathVariable int id) {
        return accountInfoRepository.findById(id);
    }

    /**
     * Creates Account info
     * 
     * @param accountInfo
     * @return String
     */
    @PostMapping(path = "/accountInfos")
    String createUser(@RequestBody AccountInfo accountInfo) {
        if (accountInfo == null)
            return failure;
        accountInfoRepository.save(accountInfo);
        return success;
    }

    /**
     * Changes Account info with id {id}
     * 
     * @param id
     * @param request
     * @return AccountInfo
     */
    @PutMapping("/accountInfos/{id}")
    AccountInfo updateAccountInfo(@PathVariable int id, @RequestBody AccountInfo request) {
        AccountInfo accountInfo = accountInfoRepository.findById(id);
        if (accountInfo == null)
            return null;
        accountInfoRepository.save(request);
        return accountInfoRepository.findById(id);
    }

    /**
     * Assigns account info with id {accountInfoId} to a user with id {userId}
     * 
     * @param accountInfoId
     * @param userId
     * @return String
     */
    @PutMapping("/AccountInfos/{accountInfoId}/users/{userId}")
    String assignUserToAccountInfo(@PathVariable int accountInfoId, @PathVariable int userId) {
        AccountInfo accountInfo = accountInfoRepository.findById(accountInfoId);
        User user = userRepository.findById(userId);
        if (user == null || accountInfo == null)
            return failure;
        user.setAccountInfo(accountInfo);
        accountInfo.setUser(user);
        accountInfoRepository.save(accountInfo);
        return success;
    }

    /**
     * Deletes account info with id {id}
     * 
     * @param id
     * @return String
     */
    @DeleteMapping(path = "/accountInfo/{id}")
    String deleteUser(@PathVariable int id) {
        accountInfoRepository.deleteById(id);
        return success;
    }
}
