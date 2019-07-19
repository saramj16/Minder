package Server.Model.Controller;

import Server.Model.Server;
import Server.Model.entity.UsuariManager;
import Server.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private View v;
    private UsuariManager um;


    public Controller (View v, UsuariManager um) {
        this.v = v;
        this.um = um;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VEURE" :
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
            default:
                break;
        }
    }
}
