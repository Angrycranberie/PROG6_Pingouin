package view;

import controller.ControllerMediator;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Interface de chargement d'une partie
 * @author Mathias
 * Alexis (mockup)
 */

public class LoadGameInterface {
    public JPanel p_main;
    private JButton b_cancel;
    private JButton b_loadGame;
    private JLabel l_title;
    private JLabel l_select;
    private JTextField tf_nomSauvegarde;

    /**
     *
     * @param mm Interface de menu principal afin de pouvoir revenir en arrière
     * @param gra L'interface graphique : la fenêtre sur laquelle l'application s'affiche
     */
    LoadGameInterface(final MainMenuInterface mm, GraphicInterface gra){
        //Clic sur le bouton "Annuler"
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chargement d'une partie une fois que le nom est rentré
                p_main.getRootPane().setContentPane(mm.p_main);
                mm.p_main.getRootPane().updateUI();
            }
        };

        //Clic sur le bouton "Charger la partie"
        ActionListener al_loadGame = new ActionListener() {
            @Override
            //TODO Verfication de l'entréé
            //TODO Remplacer le 'textfield' par une 'ComboBox' (ou un menu déroulant)
            public void actionPerformed(ActionEvent e) {
                gra.game.load(tf_nomSauvegarde.getText());
                try {
                    gra.gameInterface = new GameInterface(gra.game, new ControllerMediator(gra.game), gra);
                } catch (IOException | FontFormatException ioException) {
                    ioException.printStackTrace();
                }
                p_main.getRootPane().setContentPane(gra.gameInterface.p_main);
                gra.updateGIUI();
            }
        };

        b_cancel.addActionListener(al_cancel);
        b_loadGame.addActionListener(al_loadGame);


    }

}
