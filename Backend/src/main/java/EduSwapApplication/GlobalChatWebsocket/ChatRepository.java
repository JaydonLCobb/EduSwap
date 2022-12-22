package EduSwapApplication.GlobalChatWebsocket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import EduSwapApplication.Users.User;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findById(int id);

    List<Chat> findByChatter(User chatter);

}
