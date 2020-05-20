package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuInterface {
    public JPanel p_main;
    private JButton b_loadGame;
    private JButton b_quit;
    private JLabel l_title;
    private JButton b_newGame;
    private JButton b_settings;

    EventCollector eventCollector;

    MainMenuInterface(EventCollector ec, GraphicInterface gra){
        eventCollector = ec;

        final MainMenuInterface me = this;

        ActionListener al_loadGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGameInterface lg = new LoadGameInterface(me,gra);
                p_main.getRootPane().setContentPane(lg.p_main);
                lg.p_main.getRootPane().updateUI();
            }
        };
        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                return;
            }
        };
        ActionListener al_newGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameInterface ng = new NewGameInterface(gra);
                NewGameInterface ng = new NewGameInterface(ec);
                p_main.getRootPane().setContentPane(ng.p_main);
                ng.p_main.getRootPane().updateUI();
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
