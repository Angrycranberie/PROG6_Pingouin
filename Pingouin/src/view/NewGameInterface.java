package view;

import controller.Player;
import model.Game;

import javax.swing.*;
import java.awt.*;
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
    public JPanel p_main;

    NewGameInterface(){

        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuInterface mm = new MainMenuInterface();
                p_main.getRootPane().setContentPane(mm.p_main);
                mm.p_main.getRootPane().updateUI();
            }
        };

        ActionListener al_startGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game g = new Game(2, new Player(4, Color.red,tf_joueur1.getText()), new Player(4, Color.green,tf_joueur2.getText()),null,null);
                GameInterface gi = new GameInterface(g, null); // TODO : "null" À CHANGER, URGENT !
                p_main.getRootPane().setContentPane(gi.p_main);
                gi.p_main.getRootPane().getJMenuBar().setVisible(true);
                gi.p_main.getRootPane().updateUI();
            }
        };

        b_cancel.addActionListener(al_cancel);
        b_startGame.addActionListener(al_startGame);

    }


}
