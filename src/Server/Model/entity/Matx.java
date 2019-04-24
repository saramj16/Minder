package Server.Model.entity;

public class Matx {
    private String user1;
    private String user2;
    private boolean matx;
    private boolean vist;

    public Matx(String user1, String user2, boolean matx, boolean vist) {
        this.user1 = user1;
        this.user2 = user2;
        this.matx = matx;
        this.vist = vist;
    }


    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public boolean isMatx() {
        return matx;
    }

    public void setMatx(boolean matx) {
        this.matx = matx;
    }

    public boolean isVist() {
        return vist;
    }

    public void setVist(boolean vist) {
        this.vist = vist;
    }
}
