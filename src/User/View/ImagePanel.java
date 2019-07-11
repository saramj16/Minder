package User.View;

import com.mysql.jdbc.Blob;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{
    private  BufferedImage image;
    //private Blob imatge;

    public Image ImagePanel(String imgPath) throws IOException {
        if (imgPath == null || imgPath.equals("")){
            image = ImageIO.read(new File("./src/User/Imatges/default.jpeg"));
        } else {
            image = ImageIO.read(new File(imgPath));
        }
       return image;
    }
}