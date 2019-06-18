package User.Model;


import java.io.Serializable;
import java.util.ArrayList;


public class Match implements Serializable {
    private User user1;
    private User user2;
    private ArrayList<Mensaje> chat;


    public Match(User user1, User user2, ArrayList<Mensaje> chat) {
        this.user1 = user1;
        this.user2 = user2;
        this.chat = chat;
    }



    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }
    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }
    public ArrayList<Mensaje> getChat() { return chat; }
    public void setChat(ArrayList<Mensaje> chat) { this.chat = chat; }
}
