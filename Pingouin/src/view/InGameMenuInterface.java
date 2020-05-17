package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InGameMenuInterface {
    private JButton b_resume;
    private JLabel l_title;
    private JLabel l_menu;
    private JButton b_save;
    private JButton b_newGame;
    private JButton b_backMainMenu;
    public JPanel p_main;

    private GameInterface game;

    InGameMenuInterface(GameInterface g){

        game = g;

        //GameInterface g = p_main.getRootPane().;
        ActionListener al_resume = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_main.getRootPane().setContentPane(game.p_main);
            }
        };
        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SaveInterface si = new SaveInterface();
            }
        };


        ActionListener al_newGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        ActionListener al_backMainMenu = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_resume.addActionListener(al_resume);
        b_backMainMenu.addActionListener(al_backMainMenu);
        b_newGame.addActionListener(al_newGame);
        b_save.addActionListener(al_save);
    }

}
