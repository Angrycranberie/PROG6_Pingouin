package view;

import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuInterface {
    public JPanel panelMain;
    private JButton b_loadGame;
    private JButton b_quit;
    private JLabel l_title;
    public JButton b_newGame;
    private JButton b_settings;


    MainMenuInterface(){
        ActionListener al_loadGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener al_newGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener al_settings = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_loadGame.addActionListener(al_loadGame);
        b_newGame.addActionListener(al_newGame);
        b_quit.addActionListener(al_quit);
        b_settings.addActionListener(al_settings);

    }
}
