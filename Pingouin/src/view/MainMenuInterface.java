package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface du menu principal
 * @author Mathias
 * Alexis (mockup)
 */


public class MainMenuInterface {
    public JPanel p_main;
    private JButton b_loadGame;
    private JButton b_quit;
    private JLabel l_title;
    private JButton b_newGame;
    private JButton b_settings;

    EventCollector eventCollector;


    /**
     *
     * @param ec EnventController de la fenêtre
     * @param gra L'interface graphique : la fenêtre sur laquelle l'application s'affiche
     */
    MainMenuInterface(EventCollector ec, final GraphicInterface gra){
        eventCollector = ec;

        //mémorisation de l'interface actuelle
        final MainMenuInterface me = this;

        //Clic sur le bouton "Charger une partie"
        ActionListener al_loadGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGameInterface lg = new LoadGameInterface(me,gra);
                p_main.getRootPane().setContentPane(lg.p_main);
                lg.p_main.getRootPane().updateUI();
            }
        };

        //Clic sur le bouton "Quitter"
        //TODO faire fonctionner le bouton "quitter"
        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                return;
            }
        };

        //Clic sur le bouton "nouvelle partie"
        ActionListener al_newGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Changement de fenêtre
                NewGameInterface ng = new NewGameInterface(eventCollector,gra);
                p_main.getRootPane().setContentPane(ng.p_main);
                ng.p_main.getRootPane().updateUI();
            }
        };

        //Clic sur le bouton "Options"
        //TODO ajouter des options à modifier et une interface
        ActionListener al_settings = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        //Ajout des listener sur les boutons
        b_loadGame.addActionListener(al_loadGame);
        b_newGame.addActionListener(al_newGame);
        b_quit.addActionListener(al_quit);
        b_settings.addActionListener(al_settings);

    }
}
