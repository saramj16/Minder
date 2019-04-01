public class Mensaje {
    private int id; //num de linea
    private String mensaje;
    private User userSend;

    public Mensaje(int id, String mensaje, User userSend) {
        this.id = id;
        this.mensaje = mensaje;
        this.userSend = userSend;
    }



    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public User getUserSend() { return userSend; }
    public void setUserSend(User userSend) { this.userSend = userSend; }
}
