package User.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public DemanarFoto() throws IOException {
        button = new JButton("Browse");
        button.setBounds(40, 300, 100, 40);

        button2 = new JButton("ADD");
        button2.setBounds(150,300,100,40);

        textName = new JTextField("Name");
        textName.setBounds(260,320,100,20);

        label = new JLabel();
        label.setBounds(10,10,400,250);

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
                        imatge = new ImagePanel().ImagePanel(path);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    icon = new ImageIcon(imatge.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH));
                    label.setIcon(icon);
                    //JFileChooser fotoGuardada = new JFileChooser("/src/User/Imatges");
                   // fotoGuardada.setCurrentDirectory(selectedFile);

                }
                else if(result == JFileChooser.CANCEL_OPTION){
                    System.out.println("No Data");
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("ENTREM");
                String dest = System.getProperty("user.dir") + "/src/User/Imatges/" + selectedFile.getName();
                Path desti = Paths.get(dest);

                String orig = selectedFile.getAbsolutePath();
                Path origen = Paths.get(orig);

                try {
                    System.out.println("AMAZING");
                    Files.copy(origen, desti, REPLACE_EXISTING);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
/*
                FileWriter escriu;
                fileChooser.showSaveDialog(null);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                try {
                    escriu = new FileWriter(selectedFile, true);
                    escriu.write(textName.getText());
                    escriu.close();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar, ponga nombre al archivo");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar, en la salida");
                }
*/

            }
        });


        add(label);
        add(textName);
        add(button);
        add(button2);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440,420);
        setVisible(true);
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
}
