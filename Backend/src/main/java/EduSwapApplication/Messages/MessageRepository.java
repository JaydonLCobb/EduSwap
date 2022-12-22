package EduSwapApplication.Messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import EduSwapApplication.Users.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findById(int id);

    List<Message> findBySender(User sender);

    List<Message> findByReceiver(User receiver);
}
