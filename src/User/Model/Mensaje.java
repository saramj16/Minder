package User.Model;


import java.io.Serializable;

/**
 * Classe missatge. Conté informació necessària per a l'implementació del chat
 */
public class Mensaje implements Serializable {
    private String mensaje;
    private User userSend;
    private User userReceive;

    public Mensaje(String mensaje, User userSend, User userReceive) {
        this.mensaje = mensaje;
        this.userSend = userSend;
        this.userReceive = userReceive;
    }


    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public User getUserSend() { return userSend; }
    public void setUserSend(User userSend) { this.userSend = userSend; }
}
