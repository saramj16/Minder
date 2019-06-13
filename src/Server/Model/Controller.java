package Server.Model;

import Server.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private View v;
    private int[] horesMatches = {1,2,3,4,5,6,7,8,9,10,11,12,12,11,10,9,8,7,6,5,4,3,2,1};   //Exemple
    private int[] diesMatchesWeek = {6,7,8,9,10,2,1};   //Exemple
    private int[] diesMatchesMonth = {1,2,3,4,5,6,7,8,9,10,11,12,12,11,10,9,8,7,6,5,4,3,2,1,6,7,8,9,10,2,1};   //Exemple

    public Controller (View v) {
        this.v = v;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "VEURE" :
                if( v.getSelectedEvolution() == 1) { //Dia
                    v.setDayEvolution(horesMatches);
                }
                if (v.getSelectedEvolution() == 2) {
                    v.setWeekEvolution(diesMatchesWeek);
                }
                if (v.getSelectedEvolution() == 3) {
                    v.setMonthEvolution(diesMatchesMonth);
                }
                break;
            default:
                break;
        }
    }
}
