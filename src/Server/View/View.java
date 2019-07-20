package Server.View;

import Server.Model.Controller.Controller;
import Server.Model.entity.UsuariManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;

public class View extends JFrame {
    private final int WIDTH = 400;  //Amplada
    private final int HEIGHT = 300; //Alçada
    private JTabbedPane tabbedPane;

    private GraficaBarres chart;
    private JRadioButton jrbDay;
    private JRadioButton jrbWeek;
    private JRadioButton jrbMonth;

    private JButton jbVeure;
    private JLabel jlInfo;


    private JButton jbRefresh;

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


    public View(ArrayList<String> noms, ArrayList<Integer> puntuacions) {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Finestra Servidor");

        tabbedPane = new JTabbedPane();
        //tabbedPane.setBounds(0,0, WIDTH, HEIGHT);

        tabbedPane.add("Evolució", getJpEvolucio());
        tabbedPane.add("Top 5 Usuaris", getJpTop5Users(noms, puntuacions));
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



        int[] horesMatches = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};   //Exemple
        chart = new GraficaBarres();
        jpChart.add(chart, BorderLayout.CENTER);



        jlInfo = new JLabel("");

        jpChart.add(jlInfo, BorderLayout.SOUTH);

        setStart(horesMatches);

        return jpChart;
    }

    public JPanel getJpTop5Users(ArrayList<String> noms, ArrayList<Integer> puntuacions) {
        JPanel jpTop5Users = new JPanel(new BorderLayout());
        JPanel jpTop5Table = new JPanel(new GridLayout(6,2, 0,0));
        jbRefresh = new JButton("Actualitza");
        JPanel jpRefresh = new JPanel(new BorderLayout());
        jpRefresh.add(jbRefresh, BorderLayout.EAST);
        JLabel jlTopTitol = new JLabel("                    Top 5 usuaris més acceptats");
        jpRefresh.add(jlTopTitol, BorderLayout.CENTER);
        jpTop5Users.add(jpTop5Table, BorderLayout.CENTER);
        jpTop5Users.add(jpRefresh, BorderLayout.NORTH);
        //jpTop5Table.setBorder(BorderFactory.createEmptyBorder(,2,2,2));

        JLabel jlUsuari = new JLabel("  Usuari:");
        jlUsuari.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpTop5Table.add(jlUsuari);
        JLabel jlNCopsAcceptat = new JLabel(("  Nombre de cops acceptat"));
        jlNCopsAcceptat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jpTop5Table.add(jlNCopsAcceptat);

        jlTop1 = new JLabel("");
        jlTop1NCops = new JLabel("");
        jpTop5Table.add(jlTop1);
        jpTop5Table.add(jlTop1NCops);

        jlTop2 = new JLabel("");
        jlTop2NCops = new JLabel("");
        jpTop5Table.add(jlTop2);
        jpTop5Table.add(jlTop2NCops);

        jlTop3 = new JLabel("");
        jlTop3NCops = new JLabel("");
        jpTop5Table.add(jlTop3);
        jpTop5Table.add(jlTop3NCops);

        jlTop4 = new JLabel("");
        jlTop4NCops = new JLabel("");
        jpTop5Table.add(jlTop4);
        jpTop5Table.add(jlTop4NCops);

        jlTop5 = new JLabel("");
        jlTop5NCops = new JLabel("");
        jpTop5Table.add(jlTop5);
        jpTop5Table.add(jlTop5NCops);

        //inicialitzaTop5 (ArrayList<String> noms, ArrayList<Integer> puntuacions)
        inicialitzaTop5(noms, puntuacions);

        return jpTop5Users;

    }

    public void inicialitzaTop5 (ArrayList<String> noms, ArrayList<Integer> puntuacions){
        String[] nomsArr = new String[noms.size()];
        nomsArr = noms.toArray(nomsArr);
        Integer[] puntuacionsArr = new Integer[puntuacions.size()];
        puntuacionsArr = puntuacions.toArray(puntuacionsArr);
        setTop5(nomsArr , puntuacionsArr);
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


    public void setStart(int horesMatches[]) {
        chart.reset();
        repaint();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < now.get(Calendar.HOUR_OF_DAY); i++) {
            chart.addBar(new Color(51,153,230+i), horesMatches[i]);
        }
        jlInfo.setText("Seleccioni una opció per veure el seu gràfic corresponent.");
    }

    /**
    Fa set de les barres de la gràfica de matches diaria, rebent com a parametre un array d'integers que son les 24
    hores del dia amb els seus corresponents matches realitzats.
     */
    public void setDayEvolution(int horesMatches[]) {
        chart.reset();
        repaint();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < now.get(Calendar.HOUR_OF_DAY)+1; i++) {
            chart.addBar(new Color(51,153,230+i), horesMatches[i]);
        }
        jlInfo.setText("Nombre de matches realitzats les "+now.get(Calendar.HOUR_OF_DAY)+" hores d'avui.");
    }

    /**
     Fa set de les barres de la gràfica de matches setmanal, rebent com a parametre un array d'integers que son els 7
     dies de la setmana amb els seus corresponents matches realitzats.
     */
    public void setWeekEvolution(int diesMatchesSetmana[]) {
        chart.reset();
        repaint();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < now.get(Calendar.DAY_OF_WEEK); i++) {
            chart.addBar(new Color(110,110,230+i), diesMatchesSetmana[i]);
        }
        jlInfo.setText("Nombre de matches realitzats els "+now.get(Calendar.DAY_OF_WEEK)+" dies d'aquesta setmana.");
    }

    /**
     Fa set de les barres de la gràfica de matches mensual, rebent com a parametre un array d'integers que son els 31
     dies del mes amb els seus corresponents matches realitzats.
     */
    public void setMonthEvolution(int[] diesMatchesMes) {
        chart.reset();
        repaint();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < now.get(Calendar.DAY_OF_MONTH); i++) {
            chart.addBar(new Color(220+i,0,220+i), diesMatchesMes[i]);
        }
        jlInfo.setText("Nombre de matches realitzats els "+now.get(Calendar.DAY_OF_MONTH)+" dies d'aquest mes.");
    }

    /**
     * Rep el top i els seus resultats, els col.loca a les seves JLabels
     * @param noms: Array de noms, ordenats de top 1 al 5
     * @param acceptacions: Array de Integers amb els corresponents matches del top 1 al 5
     */
    public void setTop5(String[] noms, Integer[] acceptacions) {
        if (noms.length != 0){
            try {
                jlTop1.setText("   1.- "+noms[0]);
                jlTop1NCops.setText(("     "+acceptacions[0].toString()));
                jlTop1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jlTop1NCops.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }catch (IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }

            try {
                jlTop2.setText("   2.- "+noms[1]);
                jlTop2NCops.setText(("     "+acceptacions[1].toString()));
                jlTop2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jlTop2NCops.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }catch (IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }

            try {
                jlTop3.setText("   3.- "+noms[2]);
                jlTop3NCops.setText(("     "+acceptacions[2].toString()));
                jlTop3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jlTop3NCops.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }catch (IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }

            try {
                jlTop4.setText("   4.- "+noms[3]);
                jlTop4NCops.setText(("     "+acceptacions[3].toString()));
                jlTop4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jlTop4NCops.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }catch (IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }

            try {
                jlTop5.setText("   5.- " + noms[4]);
                jlTop5NCops.setText(("     " + acceptacions[4].toString()));
                jlTop5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                jlTop5NCops.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }catch (IndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
            }
        }else {
            jlTop1.setText("   1.- No users!");
            jlTop2.setText("   1.- No users!");
            jlTop3.setText("   1.- No users!");
            jlTop4.setText("   1.- No users!");
            jlTop5.setText("   1.- No users!");

        }
    }

    public void registerController (Controller c) {
        jbVeure.setActionCommand("VEURE");
        jbVeure.addActionListener(c);

        jbRefresh.setActionCommand("ACTUALITZA");
        jbRefresh.addActionListener(c);

    }
}


