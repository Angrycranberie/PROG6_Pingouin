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
    private JPanel p_main;

    InGameMenuInterface(){
        ActionListener al_resume = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
