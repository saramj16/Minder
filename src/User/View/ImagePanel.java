package User.View;

import com.mysql.jdbc.Blob;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{
    private  BufferedImage image;
    //private Blob imatge;

    public Image ImagePath(String imgPath) throws IOException {
        if (imgPath == null || imgPath.equals("")){
            image = ImageIO.read(new File("./src/Server/Imatges/default.jpeg"));
        } else {
            try {
                image = ImageIO.read(new File(imgPath));
            }catch (IOException e){
                image = ImageIO.read(new File("./src/Server/Imatges/default.jpeg"));
            }
        }
       return image;
    }

    private Image image2;
    byte [] bImg;

    public Image ImageInput(InputStream img) throws IOException {
        if (img == null){
            System.out.println("BRUTAl");
            image2 = ImageIO.read(new File("./src/Server/Imatges/default.jpeg"));
        } else {
            bImg = img.readAllBytes();
            ImageIcon imageIcon = new ImageIcon(bImg);
            image2 = imageIcon.getImage();
        }
        return image2;
    }
}