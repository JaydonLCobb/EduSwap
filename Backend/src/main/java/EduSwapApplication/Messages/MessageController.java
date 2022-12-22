package EduSwapApplication.Messages;

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
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    
    /** 
     * Returns all messages
     * @return List<Message>
     */
    @GetMapping(path = "/messages")
    List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    
    /** 
     * returns a message with id {id}
     * @param id
     * @return Message
     */
    @GetMapping(path = "/messages/{id}")
    Message getMessageById(@PathVariable int id) {
        return messageRepository.findById(id);
    }

    
    /** 
     * Creates a message using the senders id {senderId} and receivers id {receiverId}
     * @param message
     * @param senderId
     * @param receiverId
     * @return String
     */
    @PostMapping(path = "/messages/{senderId}/{receiverId}")
    String createMessage(@RequestBody Message message, @PathVariable int senderId, @PathVariable int receiverId) {
        if (message == null)
            return failure;
        User sender = userRepository.findById(senderId);
        User receiver = userRepository.findById(receiverId);
        if (sender == null || receiver == null)
            return failure;
        message.setSender(sender);
        message.setReceiver(receiver);
        messageRepository.save(message);
        return success;
    }

    
    /** 
     * Change a message with id {id}
     * @param id
     * @param request
     * @return Message
     */
    @PutMapping("/messages/{id}")
    Message updateMessage(@PathVariable int id, @RequestBody Message request) {
        Message message = messageRepository.findById(id);
        if (message == null)
            return null;
        messageRepository.save(request);
        return messageRepository.findById(id);
    }

    
    /** 
     * Get all messages with the sender {username}
     * @param username
     * @return List<Message>
     */
    @GetMapping(path = "/messages/sender/{username}")
    List<Message> getMessageBySender(@PathVariable String username) {
        User sender = userRepository.findByUsername(username);
        return messageRepository.findBySender(sender);
    }

    
    /** 
     * get all messages with the receiver {username}
     * @param username
     * @return List<Message>
     */
    @GetMapping(path = "/messages/receiver/{username}")
    List<Message> getMessageByReceiver(@PathVariable String username) {
        User receiver = userRepository.findByUsername(username);
        return messageRepository.findByReceiver(receiver);
    }

}
