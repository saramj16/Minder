package Server.Model.Controller;

import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import Server.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe controlador del servidor. Capta les accions realitzades a la vista d'aquest
 */
public class Controller implements ActionListener {

    private View v;
    private UsuariManager um;

    /**
     * Constructor
     * @param v
     * @param um
     */
    public Controller (View v, UsuariManager um) {
        this.v = v;
        this.um = um;
    }

    /**
     * Sobreescriu actionPerformed
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VEURE" : //Mostra la gràfica corresponent segons el JRadioButton seleccionat
                if( v.getSelectedEvolution() == 1) { //Dia
                    v.setDayEvolution(um.llistaMatchesDiaria());
                    for (int i : um.llistaMatchesDiaria()) {
                        System.out.println("num matches/hora: "+i);
                    }
                }
                if (v.getSelectedEvolution() == 2) {    //Setmana
                    v.setWeekEvolution(um.llistaMatchesSetmanal());
                    for (int i : um.llistaMatchesSetmanal()) {
                        System.out.println("num matches/dia: "+i);
                    }
                }
                if (v.getSelectedEvolution() == 3) {    //Mes
                    v.setMonthEvolution(um.llistaMatchesMensual());
                    for (int i : um.llistaMatchesMensual()) {
                        System.out.println("num matches/dia: "+i);
                    }
                }
                break;

            case "ACTUALITZA": //Refresca el Top 5 amb resultats a temps real
                ArrayList<String> noms = um.getTop5UsuarisAcceptats();
                ArrayList<Integer> puntuacions = um.getTop5NumAcceptacions();

                System.out.println( noms.get(0) + puntuacions.get(0));
                System.out.println( noms.get(1) + puntuacions.get(1));
                System.out.println( noms.get(2) + puntuacions.get(2));

                String[] nomsStr = new String[noms.size()];
                Integer[] puntuacionsInt = new Integer[puntuacions.size()];
                v.setTop5(noms.toArray(nomsStr), puntuacions.toArray(puntuacionsInt));
                break;
            default:
                break;
        }
    }
}
