package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Interface de sauvegarde la partie
 * @author Mathias
 * Alexis (mockup)
 */


public class SaveInterface {
    private JTextField tf_saveName;
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_save;
    private JButton b_cancel;
    public JPanel p_main;

    EventCollector eventCollector;

    /**
     *
     * @param g le panel principal de l'interface de jeu afin d'y revenir si beson
     * @param s Mémorisation de "où" on veut aller : ng -> interface de nouvelle partie, mm -> menu principal, game -> retour au jeu, quit -> interface d'avertissement
     *          de sortie d'une partie
     * @param ec L'eventController de la fenêtre
     * @param gra L'interface graphique : la fenêtre sur laquelle l'application s'affiche
     */

    SaveInterface(final JPanel g, final String s, EventCollector ec, final GraphicInterface gra){
        eventCollector = ec;

        //Clic sur le bouton "Sauvearder"
        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO : une fenêtre de dialogue si le nom de la sauvegarde est déjà pris
                JPopupMenu confirm = new JPopupMenu();

                //appel de la méthode de sauvegarde
                gra.game.save(tf_saveName.getText()+".txt");

                //Changement de fenêtre à la fin de la sauvegarde
                switch (s) {
                    case "game":
                       // gra.gameInterface.game.save(tf_saveName + "")
                        p_main.getRootPane().setContentPane(g);
                        gra.updateGIUI();
                        break;
                    case "ng":
                        NewGameInterface ng = new NewGameInterface(eventCollector, gra);
                        p_main.getRootPane().setContentPane(ng.p_main);
                        ng.p_main.getRootPane().updateUI();
                        break;
                    case "mm":
                        MainMenuInterface mm = new MainMenuInterface(eventCollector, gra);
                        p_main.getRootPane().setContentPane(mm.p_main);
                        mm.p_main.getRootPane().updateUI();
                        break;
                }
            }
        };

        //Clic sur le bouton "Annuler"
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Retour sur la fenêtre "où" on était
                switch (s) {
                    case "game":
                        p_main.getRootPane().setContentPane(g);
                        g.getRootPane().getJMenuBar().setVisible(true);
                        gra.updateGIUI();
                        break;
                    case "quit":
                        QuitGameInterface qi = new QuitGameInterface(g,s,eventCollector, gra);
                        p_main.getRootPane().setContentPane(qi.p_main);
                        qi.p_main.getRootPane().updateUI();
                        break;
                }

            }
        };

        //ajout des listener sur les boutons
        b_cancel.addActionListener(al_cancel);
        b_save.addActionListener(al_save);

    }

}
