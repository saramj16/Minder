package User.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

class ImagePanel extends JPanel{

    Image ImagePanel(String imgPath) throws IOException {
        BufferedImage image;
        if (imgPath == null || imgPath.equals("")){
            image = ImageIO.read(new File("C:\\Users\\javog\\OneDrive\\Escritorio\\universidad\\Asignaturas\\18-19\\DPOO\\Minder-master\\src\\Imatges\\default.jpeg"));
        } else {
            image = ImageIO.read(new File(imgPath));
        }
       return image;
    }
}