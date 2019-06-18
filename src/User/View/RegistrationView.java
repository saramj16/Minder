package User.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrationView extends JFrame{

    private JPanel mainpanel;
    private TextField userName;
    private TextField edat;
    private boolean premium;
    private TextField correo;
    private JPasswordField contraseña;
    private JPasswordField repetirContraseña;
    private TextField urlFoto;
    private TextField lenguaje;
    private TextField descripción;
    private JButton register, demanarFoto;


    public RegistrationView(){
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
        //mainpanel = new JPanel(new GridLayout(14,1));
        mainpanel = new JPanel(new BorderLayout());
        userName = new TextField();
        edat = new TextField();
        correo = new TextField();
        contraseña = new JPasswordField();
        repetirContraseña = new JPasswordField();
        demanarFoto = new JButton("Escoger foto");
        lenguaje = new TextField();
        descripción = new TextField();
        register = new JButton("Registrate!");

        JPanel jpMid= new JPanel(new GridLayout(9,1));
        jpMid.add(new JLabel("          Username: "));
        jpMid.add(userName);
        jpMid.add(new JLabel("          Edad: "));
        jpMid.add(edat);
        jpMid.add(new JLabel("          Correo: "));
        jpMid.add(correo);
        jpMid.add(new JLabel("          Contraseña: "));
        jpMid.add(contraseña);
        jpMid.add(new JLabel("          Repite tu contraseña: "));
        jpMid.add(repetirContraseña);
        jpMid.add(new JLabel("          URL de tu fotografía: "));
        jpMid.add(demanarFoto);
        jpMid.add(new JLabel("          Lenguaje: "));
        jpMid.add(lenguaje);
        jpMid.add(new JLabel("          Descripción: "));
        jpMid.add(descripción);

        JLabel jlTitol = new JLabel("   Registro");
        jlTitol.setFont (jlTitol.getFont ().deriveFont (30.0f));
        mainpanel.add(jlTitol, BorderLayout.NORTH);
        mainpanel.add(jpMid, BorderLayout.CENTER);
        mainpanel.add(register,BorderLayout.SOUTH);

        mainpanel.setVisible(true);
        this.add(mainpanel);
    }

    public void autenticationController(ActionListener controller){
        register.addActionListener(controller);
        register.setActionCommand("Register");

        demanarFoto.addActionListener(controller);
        demanarFoto.setActionCommand("DemanarFoto");
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
    public JPasswordField getContraseña() { return contraseña; }
    public void setContraseña(JPasswordField contraseña) { this.contraseña = contraseña; }
    public JPasswordField getRepetirContraseña() { return repetirContraseña; }
    public void setRepetirContraseña(JPasswordField repetirContraseña) { this.repetirContraseña = repetirContraseña; }
    public TextField getLenguaje() { return lenguaje; }
    public void setLenguaje(TextField lenguaje) { this.lenguaje = lenguaje; }
    public TextField getDescripción() { return descripción; }
    public void setDescripción(TextField descripción) { this.descripción = descripción; }
    public JButton getDemanarFoto() { return demanarFoto; }
    public void setDemanarFoto(JButton demanarFoto) { this.demanarFoto = demanarFoto; }
    public JButton getRegister() { return register; }
    public void setRegister(JButton register) { this.register = register; }
}

