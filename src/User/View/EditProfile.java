package User.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class EditProfile extends JFrame {

    JPanel jpSuperior, jpCenter, jpInferior, jpCentreEsquerra,jpCentreDreta ;
    JButton jbChangeImage, jbSave;
    Image imatge;
    JCheckBox jcbPremium;
    JRadioButton jrbJava, jrbC;
    JTextField jtfCorreu,jtfPassword, jtfDescription;
    JSpinner jsEdat;
    SpinnerModel value;
    JLabel jlPerfil, jlNom, jlEdat, jlCorreu, jlPassword, jlDescription, jlLanguage, jlPremium, jlImage;

    public EditProfile(){
        configuraFinestra();
    }


    private void configuraFinestra(){
        this.setSize(600, 400);
        this.setTitle("MINDER");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public void dadesEditaPrefil(String nom, int edat, String correu, String password, String descripcio,  String lenguage, boolean premium, String pathImage) throws IOException, URISyntaxException {

        jlNom = new JLabel(nom);

        imatge = new ImagePanel().ImagePanel(pathImage);
        jlImage = new JLabel(new ImageIcon(imatge.getScaledInstance(300, 250, Image.SCALE_SMOOTH)));
        jbChangeImage = new JButton("Change Image");

        jlEdat = new JLabel("Edat: ");
        value = new SpinnerNumberModel(edat, //initial value
                        0, //minimum value
                        100, //maximum value
                        1); //step
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
        if (lenguage.equals("C")){
            jrbC = new JRadioButton("C", true);
            jrbJava = new JRadioButton("Java", false);
        } else {
            jrbC = new JRadioButton("C", false);
            jrbJava = new JRadioButton("Java", true);
        }

        jbSave = new JButton("Save");

        configuraElements();
    }

    private void configuraElements() {
        this.setLayout(new BorderLayout());

        jpSuperior = new JPanel();
        jpInferior = new JPanel();
        jpCentreDreta = new JPanel();
        jpCentreEsquerra = new JPanel();
        jpCenter = new JPanel();

        jpSuperior.setLayout(new FlowLayout());
        jlPerfil = new JLabel("Perfil / ");
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



}
