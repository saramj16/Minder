package Server.Model.entity;



import User.Model.User;

import java.util.Date;

/**
 * Missatge amb contingut, data, emissor i receptor
 */
public class Missatge {
    private String missatge;
    private String userSend;
    private String userReceive;
    private Date dataMessage;

    /**
     * constructor
     * @param missatge
     * @param userSend
     * @param userReceive
     * @param dataMessage
     */
    public Missatge(String missatge, String userSend, String userReceive, Date dataMessage) {
        this.missatge = missatge;
        this.userSend = userSend;
        this.userReceive = userReceive;
        this.dataMessage = dataMessage;
    }



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

    public Date getDataMessage() {
        return dataMessage;
    }

    public void setDataMessage(Date dataMessage) {
        this.dataMessage = dataMessage;
    }
}
