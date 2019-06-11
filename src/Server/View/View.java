package Server.View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final int WIDTH = 400;  //Amplada
    private final int HEIGHT = 300; //Alçada
    private JTabbedPane tabbedPane;

    private JLabel jlTop1;
    private JLabel jlTop2;
    private JLabel jlTop3;
    private JLabel jlTop4;
    private JLabel jlTop5;

    private JLabel jlTop1NCops;
    private JLabel jlTop2NCops;
    private JLabel jlTop3NCops;
    private JLabel jlTop4NCops;
    private JLabel jlTop5NCops;


    public View() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Finestra Servidor");

        tabbedPane = new JTabbedPane();
        //tabbedPane.setBounds(0,0, WIDTH, HEIGHT);

        tabbedPane.add("Evolució", getJpEvolucio());
        tabbedPane.add("Top 5 Usuaris", getJpTop5Users());
        //tabbedPane.add("Top 5 Users", getTop5());
        //pack();

        add(tabbedPane);
        setVisible(true);
    }

    public JPanel getJpEvolucio() {
        JPanel jpChart = new JPanel();
        jpChart.setLayout(new BorderLayout());

        JLabel jlEvolucio = new JLabel("Evolució del nombre de matches respecte el temps:");
        jpChart.add(jlEvolucio, BorderLayout.NORTH);

        BarChart chart = new BarChart();
        chart.addBar(Color.red, 100);
        chart.addBar(Color.green, 14);
        chart.addBar(Color.blue, 54);
        chart.addBar(Color.black, 23);
        chart.addBar(Color.yellow, 60);
        chart.addBar(Color.cyan, 30);
        chart.addBar(Color.orange, 54);
        chart.addBar(Color.pink, 84);
        jpChart.add(chart, BorderLayout.CENTER);

        return jpChart;
    }

    public JPanel getJpTop5Users() {
        JPanel jpTop5Users = new JPanel(new GridLayout(7,2));

        JLabel jlUsuari = new JLabel("Usuari:");
        jpTop5Users.add(jlUsuari);
        JLabel jlNCopsAcceptat = new JLabel(("Nombre de cops acceptat"));
        jpTop5Users.add(jlNCopsAcceptat);

        jlTop1 = new JLabel("sarEta");
        jlTop1NCops = new JLabel("30");
        jpTop5Users.add(jlTop1);
        jpTop5Users.add(jlTop1NCops);

        jlTop2 = new JLabel("Habbo Hotel");
        jlTop2NCops = new JLabel("28");
        jpTop5Users.add(jlTop2);
        jpTop5Users.add(jlTop2NCops);

        jlTop3 = new JLabel("Manel");
        jlTop3NCops = new JLabel("25");
        jpTop5Users.add(jlTop3);
        jpTop5Users.add(jlTop3NCops);

        jlTop4 = new JLabel("Jofre");
        jlTop4NCops = new JLabel("10");
        jpTop5Users.add(jlTop4);
        jpTop5Users.add(jlTop4NCops);

        jlTop5 = new JLabel("Marcel");
        jlTop5NCops = new JLabel("7");
        jpTop5Users.add(jlTop5);
        jpTop5Users.add(jlTop5NCops);

        return jpTop5Users;

    }
}


