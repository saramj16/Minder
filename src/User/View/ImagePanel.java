package User.View;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePanel extends JPanel{
    private  BufferedImage image;

    public Image ImagePanel(String imgPath) throws IOException {
        if (imgPath == null){
            image = ImageIO.read(new File("./src/Imatges/default.jpeg"));
        } else {
            image = ImageIO.read(new File(imgPath));
        }
       return image;
    }
}