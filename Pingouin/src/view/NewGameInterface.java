package view;

import controller.ControllerMediator;
import controller.Player;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interface de création d'un nouvelle partie
 * @author Mathias
 * Alexis (mockup)
 */

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
    
    EventCollector eventCollector;

    /**
     *
     * @param ec EnventController de la fenêtre
     * @param gra L'interface graphique : la fenêtre sur laquelle l'application s'affiche
     */
    NewGameInterface(EventCollector ec, final GraphicInterface gra){
        eventCollector = ec;

        //Clic sur le bouton "Annuler"
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Changement de fenêtre
                MainMenuInterface mm = new MainMenuInterface(eventCollector, gra);
                p_main.getRootPane().setContentPane(mm.p_main);
                mm.p_main.getRootPane().updateUI();
            }
        };

        //Clic sur le bouton "Démarrer"
        ActionListener al_startGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Création d'une nouvelle partie avec les paramêtre donné via l'interface
                Game g = new Game(2, new Player(4, 0,tf_joueur1.getText()), new Player(4, 1,tf_joueur2.getText()),null,null);
                try {
                    gra.gameInterface = new GameInterface(g, new ControllerMediator(g), gra);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
                p_main.getRootPane().setContentPane(gra.gameInterface.p_main);
                gra.updateGIUI();
            }
        };

        //Ajout des listener sur les boutons
        b_cancel.addActionListener(al_cancel);
        b_startGame.addActionListener(al_startGame);

    }


}
