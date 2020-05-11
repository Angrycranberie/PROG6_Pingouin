package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameInterface {
    private JTextField tf_joueur2;
    private JTextField tf_joueur1;
    private JComboBox cb_joueur2;
    private JComboBox cb_joueur1;
    private JButton b_startGame;
    private JButton b_cancel;
    private JLabel l_joueur2;
    private JLabel l_title;
    private JLabel l_joueur1;
    private JLabel l_VS;

    NewGameInterface(){

        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        ActionListener al_startGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_cancel.addActionListener(al_cancel);
        b_startGame.addActionListener(al_startGame);

    }


}
