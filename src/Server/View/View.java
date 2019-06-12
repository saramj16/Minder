package Server.View;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final int WIDTH = 400;  //Amplada
    private final int HEIGHT = 300; //Alçada
    private JTabbedPane tabbedPane;

    private JRadioButton jrbDay;
    private JRadioButton jrbWeek;
    private JRadioButton jrbMonth;

    private JButton jbVeure;


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

        JPanel jpNorth = new JPanel(new GridLayout(2,1));
        JLabel jlEvolucio = new JLabel("Evolució del nombre de matches:");
        jpNorth.add(jlEvolucio);

        JPanel jpNorth2 = new JPanel(new GridLayout(1,4));
        jrbDay = new JRadioButton("Diari");
        jrbDay.setSelected(true);
        jpNorth2.add(jrbDay);
        jrbWeek = new JRadioButton("Setmanal");
        jpNorth2.add(jrbWeek);
        jrbMonth = new JRadioButton("Mensual");
        jpNorth2.add(jrbMonth);

        ButtonGroup group = new ButtonGroup();
        group.add(jrbDay);
        group.add(jrbWeek);
        group.add(jrbMonth);

        jbVeure = new JButton("Mostrar");
        jpNorth2.add(jbVeure);

        jpNorth.add(jpNorth2);
        jpChart.add(jpNorth, BorderLayout.NORTH);


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
        JPanel jpTop5Users = new JPanel(new GridLayout(6,2, 0,0));
        //jpTop5Users.setBorder(BorderFactory.createEmptyBorder(,2,2,2));

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

    /*
    Retorna 1 si diari, 2 si setmanal o 3 si mensual
     */
    public int getSelectedEvolution() {
        if (jrbDay.isSelected()) {
            return 1;
        } else {
            if (jrbWeek.isSelected()){
                return 2 ;
            } else {
                if (jrbMonth.isSelected()) {
                    return 3;
                } else {
                    return 0; //Error
                }
            }
        }
    }

    public void setDayEvolution() {
        //TODO: Fer que es mostri l'evolucio del dia
    }

    public void setWeekEvolution() {
        //TODO: Fer que es mostri l'evolucio de la setmana
    }

    public void setMonthEvolution() {
        //TODO: Fer que es mostri l'evolucio del mes.
    }


    public void setTop5(String[] noms, Integer[] acceptacions) {
        jlTop1.setText(noms[0]);
        jlTop1NCops.setText((acceptacions[0].toString()));

        jlTop2.setText(noms[1]);
        jlTop2NCops.setText((acceptacions[1].toString()));

        jlTop3.setText(noms[2]);
        jlTop3NCops.setText((acceptacions[2].toString()));

        jlTop4.setText(noms[3]);
        jlTop4NCops.setText((acceptacions[3].toString()));

        jlTop5.setText(noms[4]);
        jlTop5NCops.setText((acceptacions[4].toString()));

    }

}


