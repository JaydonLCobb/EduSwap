package EduSwapApplication.GlobalChatWebsocket;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Chat {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;

    private String chatter;
    private String timestamp;
    private String chat;

    public Chat() {
    }

    public Chat(String timestamp, String chat) {
        this.timestamp = timestamp;
        this.chat = chat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChatter() {
        return chatter;
    }

    public void setChatter(String chatter) {
        this.chatter = chatter;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
