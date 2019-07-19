package User.View;

import Server.Model.configReader.Configuracio;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DemanarFoto extends JFrame {
    JButton button;
    JButton button2;
    JLabel label;
    JTextField textName;
    Image imatge;
    ImageIcon icon;
    JFileChooser fileChooser = new JFileChooser();
    File selectedFile;
    Path pathUsuari;
    InputStream is;

    public DemanarFoto() throws IOException {
        button = new JButton("Browse");
        button.setBounds(40, 300, 100, 40);

        button2 = new JButton("ADD");
        button2.setBounds(150,300,100,40);

        textName = new JTextField("Name");
        textName.setBounds(260,320,100,20);

        label = new JLabel();
        label.setBounds(10,10,400,250);

        setLayout(null);
        setSize(440,420);
        setLocationRelativeTo(null);
        setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png","jpeg");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION){
                    selectedFile = fileChooser.getSelectedFile();
                    String path = selectedFile.getAbsolutePath();
                    System.out.println("path = " + path);
                    try {
                        imatge = new ImagePanel().ImagePath(path);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    icon = new ImageIcon(imatge.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH));
                    label.setIcon(icon);

                }
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No Data");
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            /*
                String extension = "";

                int i = selectedFile.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = selectedFile.getName().substring(i+1);
                }
                String dest = System.getProperty("user.dir") + "/src/User/Imatges/" + textName.getText() + "." + extension;
                Path desti = Paths.get(dest);

                String orig = selectedFile.getAbsolutePath();
                Path origen = Paths.get(orig);

                try {
                    Files.copy(origen, desti, REPLACE_EXISTING);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                setPathUsuari(desti);
                setVisible(false);*/
                String s = selectedFile.getAbsolutePath();

                String url = "", user = "", password = "";
                try{

                    //Totxaco per llegir del Json
                    Configuracio config;
                    Gson gson = new Gson();
                    JsonReader jReader;
                    try {
                        jReader = new JsonReader(new FileReader("data/config.json"));
                        config = gson.fromJson(jReader, Configuracio.class);                       //Llegeix el fitxer Json
                        url = config.getConfigServer().getIp_bbdd();
                        user = config.getConfigServer().getUsername_bbdd();
                        password = config.getConfigServer().getPassword_bbdd();
                    } catch (FileNotFoundException error) {         //Catch per si no podem obrir l'arxiu Json
                        System.out.println("Error: Fitxer no trobat.");
                    }

                    Connection con = DriverManager.getConnection(url, user, password);
                    PreparedStatement ps = con.prepareStatement("insert into Usuari(foto) values(?)");
                    is = new FileInputStream(new File(s));
                    ps.setBlob(1,is);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Inserted");
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });


        add(label);
        add(textName);
        add(button);
        add(button2);
    }

    public JButton getButton() {
        return button;
    }
    public void setButton(JButton button) {
        this.button = button;
    }
    public JButton getButton2() {
        return button2;
    }
    public void setButton2(JButton button2) {
        this.button2 = button2;
    }
    public JLabel getLabel() {
        return label;
    }
    public void setLabel(JLabel label) {
        this.label = label;
    }
    public JTextField getTextName() {
        return textName;
    }
    public void setTextName(JTextField textName) {
        this.textName = textName;
    }
    public Image getImatge() {
        return imatge;
    }
    public void setImatge(Image imatge) {
        this.imatge = imatge;
    }
    public Path getPathUsuari() {
        return pathUsuari;
    }
    public void setPathUsuari(Path pathUsuari) {
        this.pathUsuari = pathUsuari;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
