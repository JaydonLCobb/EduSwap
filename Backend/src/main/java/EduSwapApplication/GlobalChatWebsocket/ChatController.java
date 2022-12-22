package EduSwapApplication.GlobalChatWebsocket;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{username}") // this is Websocket url
public class ChatController {

  // cannot autowire static directly (instead we do it by the below
  // method
  private static ChatRepository chatRepository;

  /*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context. This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
   */
  @Autowired
  public void setChatRepository(ChatRepository repo) {
    chatRepository = repo; // we are setting the static variable
  }

  // Store all socket session and their corresponding username.
  private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
  private static Map<String, Session> usernameSessionMap = new Hashtable<>();

  private final Logger logger = LoggerFactory.getLogger(ChatController.class);

  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username)
      throws IOException {

    logger.info("Entered into Open");

    // store connecting user information
    sessionUsernameMap.put(session, username);
    usernameSessionMap.put(username, session);

    // Send chat history to the newly connected user
    sendMessageToPArticularUser(username, getChatHistory());

    // broadcast that new user joined
    String message = "User:" + username + " has Joined the Chat";
    broadcast(message);
  }

  @OnMessage
  public void onMessage(Session session, String chat) throws IOException {

    // Handle new messages
    logger.info("Entered into Chat: Got Chat:" + chat);
    String username = sessionUsernameMap.get(session);
    broadcast(username + ": " + chat);
    // Saving chat history to repository
    String time = java.time.LocalDateTime.now().toString();
    Chat chat1 = new Chat(time, chat);
    chatRepository.save(chat1);
  }

  @OnClose
  public void onClose(Session session) throws IOException {
    logger.info("Entered into Close");

    // remove the user connection information
    String username = sessionUsernameMap.get(session);
    sessionUsernameMap.remove(session);
    usernameSessionMap.remove(username);

    // broadcase that the user disconnected
    String message = username + " disconnected";
    broadcast(message);
  }

  @OnError
  public void onError(Session session, Throwable throwable) {
    // Do error handling here
    logger.info("Entered into Error");
    throwable.printStackTrace();
  }

  private void broadcast(String message) {
    sessionUsernameMap.forEach((session, username) -> {
      try {
        session.getBasicRemote().sendText(message);
      } catch (IOException e) {
        logger.info("Exception: " + e.getMessage().toString());
        e.printStackTrace();
      }

    });

  }

  private void sendMessageToPArticularUser(String username, String message) {
    try {
      usernameSessionMap.get(username).getBasicRemote().sendText(message);
    } catch (IOException e) {
      logger.info("Exception: " + e.getMessage().toString());
      e.printStackTrace();
    }
  }

  // Gets the Chat history from the repository
  private String getChatHistory() {
    List<Chat> chats = chatRepository.findAll();

    // convert the list to a string
    StringBuilder sb = new StringBuilder();
    if (chats != null && chats.size() != 0) {
      for (Chat chat : chats) {
        sb.append(chat.getChatter() + ": " + chat.getChat() + "\n");
      }
    }
    return sb.toString();
  }

} // end of Class
