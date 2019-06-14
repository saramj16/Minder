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
                }
                if (v.getSelectedEvolution() == 2) {
                    v.setWeekEvolution(um.llistaMatchesSetmanal());
                }
                if (v.getSelectedEvolution() == 3) {
                    v.setMonthEvolution(um.llistaMatchesMensual());
                }
                break;
            default:
                break;
        }
    }
}
