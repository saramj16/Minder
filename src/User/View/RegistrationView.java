package User.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class RegistrationView extends JFrame{

    private JPanel mainpanel;
    private TextField userName;
    private TextField edat;
    private boolean premium;
    private TextField correo;
    private TextField contraseña;
    private TextField repetirContraseña;
    private TextField urlFoto;
    private TextField lenguaje;
    private TextField descripción;
    private JButton register;

    public RegistrationView() throws IOException {
        configuraVentana();
        configuraCentro();
    }

    private void configuraVentana(){
        this.setSize(400, 300);
        this.setTitle("MINDER");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void configuraCentro(){
        mainpanel = new JPanel(new GridLayout(14,1));
        userName = new TextField();
        edat = new TextField();
        correo = new TextField();
        contraseña = new TextField();
        repetirContraseña = new TextField();
        urlFoto = new TextField();
        lenguaje = new TextField();
        descripción = new TextField();
        register = new JButton("Registrate!");

        mainpanel.add(new JLabel("Username: "));
        mainpanel.add(userName);
        mainpanel.add(new JLabel("Edad: "));
        mainpanel.add(edat);
        mainpanel.add(new JLabel("Correo: "));
        mainpanel.add(correo);
        mainpanel.add(new JLabel("Contraseña: "));
        mainpanel.add(contraseña);
        mainpanel.add(new JLabel("Repite tu contraseña: "));
        mainpanel.add(repetirContraseña);
        mainpanel.add(new JLabel("URL de tu fotografía: "));
        mainpanel.add(urlFoto);
        mainpanel.add(new JLabel("Lenguaje: "));
        mainpanel.add(lenguaje);
        mainpanel.add(new JLabel("Descripción: "));
        mainpanel.add(descripción);
        mainpanel.add(register);
        mainpanel.setVisible(true);

        this.add(mainpanel);
    }

    public void autenticationController(ActionListener controller){
        register.addActionListener(controller);
        register.setActionCommand("Register");
    }




    public JPanel getMainpanel() { return mainpanel; }
    public void setMainpanel(JPanel mainpanel) { this.mainpanel = mainpanel; }
    public TextField getUserName() { return userName; }
    public void setUserName(TextField userName) { this.userName = userName; }
    public TextField getEdat() { return edat; }
    public void setEdat(TextField edat) { this.edat = edat; }
    public boolean isPremium() { return premium; }
    public void setPremium(boolean premium) { this.premium = premium; }
    public TextField getCorreo() { return correo; }
    public void setCorreo(TextField correo) { this.correo = correo; }
    public TextField getContraseña() { return contraseña; }
    public void setContraseña(TextField contraseña) { this.contraseña = contraseña; }
    public TextField getRepetirContraseña() { return repetirContraseña; }
    public void setRepetirContraseña(TextField repetirContraseña) { this.repetirContraseña = repetirContraseña; }
    public TextField getUrlFoto() { return urlFoto; }
    public void setUrlFoto(TextField urlFoto) { this.urlFoto = urlFoto; }
    public TextField getLenguaje() { return lenguaje; }
    public void setLenguaje(TextField lenguaje) { this.lenguaje = lenguaje; }
    public TextField getDescripción() { return descripción; }
    public void setDescripción(TextField descripción) { this.descripción = descripción; }
    public JButton getRegister() { return register; }
    public void setRegister(JButton register) { this.register = register; }
}

