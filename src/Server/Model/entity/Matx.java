package Server.Model.entity;

import java.util.Date;

public class Matx {
    private String user1;
    private String user2;
    private boolean matx;
    private boolean accept;
    private boolean vist;
    private Date dataMatch;

    public Matx(String user1, String user2, boolean matx, boolean accept, boolean vist, Date dataMatch) {
        this.user1 = user1;
        this.user2 = user2;
        this.matx = matx;
        this.accept = accept;
        this.vist = vist;
        this.dataMatch = dataMatch;
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

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
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

    public Date getDataMatch() {
        return dataMatch;
    }

    public void setDataMatch(Date dataMatch) {
        this.dataMatch = dataMatch;
    }
}
