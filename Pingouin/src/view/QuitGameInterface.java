package view;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Interface d'avertissment lors de la sortie d'une partie
 * @author Mathias
 * Alexis (mockup)
 */

public class QuitGameInterface {
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_warning;
    private JButton b_resume;
    private JButton b_quit;
    public JPanel p_main;
    EventCollector eventCollector;


    /**
     *
     *@param g le panel principal de l'interface de jeu afin d'y revenir si beson
     *@param s Mémorisation de "où" on veut aller : ng -> interface de nouvelle partie, mm -> menu principal, game -> retour au jeu, quit -> interface d'avertissement
     *         de sortie d'une partie
     *@param ec L'eventController de la fenêtre
     *@param gra L'interface graphique : la fenêtre sur laquelle l'application s'affiche
     */
    QuitGameInterface(final JPanel g, final String s, final EventCollector ec ,final GraphicInterface gra){


        eventCollector = ec;

        //Clic sur le bouton "reprendre la partie"
        ActionListener al_resume = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Changement de fenêtre
                p_main.getRootPane().setContentPane(g);
                gra.updateGIUI();
            }
        };

        //clix sur le bouton "quitter sans sauvegarder"
        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Chargement de la fenêtre selon ce qui a été demandé
                if(s.equals("mm")){
                    MainMenuInterface mm = new MainMenuInterface(ec,gra);
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();
                } else if(s.equals("ng")){
                    NewGameInterface ng = new NewGameInterface(ec, gra);
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                }

            }
        };

        //clic sur le bouton "Sauvegarder et quitter
        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveInterface si = new SaveInterface(g,"quit",ec,gra);
                p_main.getRootPane().setContentPane(si.p_main);
                si.p_main.getRootPane().updateUI();
            }
        };

        //Ajout des listener sur les boutons
        b_quit.addActionListener(al_quit);
        b_resume.addActionListener(al_resume);
        b_save.addActionListener(al_save);

    }

}
