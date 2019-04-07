package Model.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Match {
    @Id
    private int id;
    private User user1;
    private User user2;
    private ArrayList<Mensaje> chat;


    public Match(User user1, User user2, int id, ArrayList<Mensaje> chat) {
        this.user1 = user1;
        this.user2 = user2;
        this.id = id;
        this.chat = chat;
    }



    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }
    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public ArrayList<Mensaje> getChat() { return chat; }
    public void setChat(ArrayList<Mensaje> chat) { this.chat = chat; }
}
