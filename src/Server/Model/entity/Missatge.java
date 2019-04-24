package Server.Model.entity;



import User.Model.User;

public class Missatge {
    private int id; //num de linea
    private String missatge;
    private String userSend;
    private String userReceive;

    public Missatge(int id, String missatge, String userSend, String userReceive) {
        this.id = id;
        this.missatge = missatge;
        this.userSend = userSend;
        this.userReceive = userReceive;
    }



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMissatge() { return missatge; }
    public void setMissatge(String mensaje) { this.missatge = missatge; }
    public String getUserSend() { return userSend; }
    public void setUserSend(String userSend) { this.userSend = userSend; }

    public String getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(String userReceive) {
        this.userReceive = userReceive;
    }
}
