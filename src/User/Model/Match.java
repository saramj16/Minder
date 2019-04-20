package User.Model;


import java.util.ArrayList;


public class Match {
    private String id;
    private User user1;
    private User user2;
    private ArrayList<Mensaje> chat;


    public Match(User user1, User user2, String id) {
        this.user1 = user1;
        this.user2 = user2;
        this.id = id;
        this.chat = new ArrayList<>();
    }



    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }
    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public ArrayList<Mensaje> getChat() { return chat; }
    public void setChat(ArrayList<Mensaje> chat) { this.chat = chat; }
}
