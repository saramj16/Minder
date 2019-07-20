package Server.View;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe que permet la creació de la gràfica de barres
 */
public class GraficaBarres extends JPanel {
    private Map<Color, Integer> barres = new LinkedHashMap<Color, Integer>();

    /**
     * Afegeix una barra amb el color i valor desitjats
     * @param color
     * @param value
     */
    public void addBar(Color color, int value)
    {
        barres.put(color, value);
        repaint();
    }

    /**
     * Tot a 0
     */
    public void reset() {
        for (int i = 0; i < barres.size(); i++) {
            barres.remove(i);
        }
        barres.clear();
    }

    /**
     * Mitjançant Graphics, pinta una barra al JPanel sobre el que treballem
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        int max = Integer.MIN_VALUE;
        for (Integer value : barres.values()){
            max = Math.max(max, value);
        }
        int width = (getWidth() / barres.size()) - 2;
        int x = 1;
        for (Color c : barres.keySet())
        {
            int value = barres.get(c);
            int height = (int)( (getHeight() - 5) * ( (double)value / max));
            g.setColor(c);
            g.fillRect(x, getHeight() - height, width, height);
            g.setColor(Color.black);
            String valueStr = "" + value;
            g.drawString(valueStr, x + width/2 - 2,getHeight() - 10);
            g.drawRect(x, getHeight() - height, width, height);
            x += (width + 2);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(barres.size() * 10 + 2, 50);
    }

}


