package User.View;

import User.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class EditProfile extends JFrame {

    private JButton jbChangeImage, jbSave;
    private JCheckBox jcbPremium;
    private JRadioButton jrbJava, jrbC;
    private JTextField jtfCorreu,jtfPassword, jtfDescription;
    private JSpinner jsEdat;
    private JLabel jlNom;
    private JLabel jlEdat;
    private JLabel jlCorreu;
    private JLabel jlPassword;
    private JLabel jlDescription;
    private JLabel jlLanguage;
    private JLabel jlPremium;
    private JLabel jlImage;

    public EditProfile(User user) throws IOException {
        configuraFinestra();
        dadesEditaPrefil(user.getUserName(), user.getEdat(), user.getCorreo(), user.getPassword(), user.getDescription(), user.getLenguaje(), user.isPremium(), user.getUrlFoto());
    }


    private void configuraFinestra(){
        this.setSize(600, 400);
        this.setTitle("MINDER");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void dadesEditaPrefil(String nom, int edat, String correu, String password, String descripcio,  String lenguage, boolean premium, String pathImage) throws IOException {

        jlNom = new JLabel(nom);

        Image imatge = new ImagePanel().ImagePanel(pathImage);
        jlImage = new JLabel(new ImageIcon(imatge.getScaledInstance(300, 250, Image.SCALE_SMOOTH)));
        jbChangeImage = new JButton("Change Image");

        jlEdat = new JLabel("Edat: ");
        SpinnerModel value = new SpinnerNumberModel(edat, //initial value
                0, //minimum value
                100, //maximum value
                1);
        jsEdat = new JSpinner(value);
        jlCorreu = new JLabel("Correu: ");
        jtfCorreu = new JTextField(correu);
        jlPassword = new JLabel("Contrasenya: ");
        jtfPassword = new JTextField(password);
        jlDescription = new JLabel("Descripció: ");
        jtfDescription = new JTextField(descripcio);
        jlPremium = new JLabel("Premium: ");
        jcbPremium = new JCheckBox(" ", premium);
        jlLanguage = new JLabel("Llenguatge: ");
        if (lenguage == null || lenguage.equals("")){
            jrbC = new JRadioButton("C", false);
            jrbJava = new JRadioButton("Java", false);
        }else {
            if (lenguage.equals("C")){
                jrbC = new JRadioButton("C", true);
                jrbJava = new JRadioButton("Java", false);
            } else {
                jrbC = new JRadioButton("C", false);
                jrbJava = new JRadioButton("Java", true);
            }
        }

        jbSave = new JButton("Save");

        configuraElements();
    }

    private void configuraElements() {
        this.setLayout(new BorderLayout());

        JPanel jpSuperior = new JPanel();
        JPanel jpInferior = new JPanel();
        JPanel jpCentreDreta = new JPanel();
        JPanel jpCentreEsquerra = new JPanel();
        JPanel jpCenter = new JPanel();

        jpSuperior.setLayout(new FlowLayout());
        JLabel jlPerfil = new JLabel("Perfil / ");
        jpSuperior.add(jlPerfil);
        jpSuperior.add(jlNom);
        this.add(jpSuperior, BorderLayout.PAGE_START);

        jpCenter.setLayout(new GridLayout(1,2));
        jpCentreEsquerra.setLayout(new BoxLayout(jpCentreEsquerra,BoxLayout.PAGE_AXIS));

        jpCentreEsquerra.add(jlImage);
        jpCentreEsquerra.add(jbChangeImage);
        jpCenter.add(jpCentreEsquerra);

        jpCentreDreta.setLayout(new GridLayout(6,2));
        jpCentreDreta.add(jlEdat);
        jpCentreDreta.add(jsEdat);
        jpCentreDreta.add(jlCorreu);
        jpCentreDreta.add(jtfCorreu);
        jpCentreDreta.add(jlPassword);
        jpCentreDreta.add(jtfPassword);
        jpCentreDreta.add(jlDescription);
        jpCentreDreta.add(jtfDescription);
        jpCentreDreta.add(jlPremium);
        jpCentreDreta.add(jcbPremium);
        jpCentreDreta.add(jlLanguage);
        JPanel jpCheck = new JPanel();
        jpCheck.setLayout(new FlowLayout());
        jpCheck.add(jrbC);
        jpCheck.add(jrbJava);
        jpCentreDreta.add(jpCheck);
        jpCenter.add(jpCentreDreta);
        this.add(jpCenter, BorderLayout.CENTER);

        jpInferior.add(jbSave);
        this.add(jpInferior, BorderLayout.PAGE_END);
    }

    public void autenticationController(ActionListener controller){
        jbSave.addActionListener(controller);
        jbSave.setActionCommand("SaveEditProfile");
    }



}
