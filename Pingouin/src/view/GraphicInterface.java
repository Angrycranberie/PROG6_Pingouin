package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Interface graphique principale du jeu.
 * Celle-ci appelle le plateau graphique pour le rendre visible.
 * @author Alexis
 * @author Mathias
 */
public class GraphicInterface implements Runnable, UserInterface, ComponentListener, ActionListener {

    Game game; // Le jeu en lui-même.
    EventCollector eventCollector; // Collecteur d'événements pour garantir l'interaction avec le jeu.
    JFrame frame; // Composant de la fenêtre de jeu.
    boolean maximized; // Si la fenêtre est en pleine écran ou non.
    public GameInterface gameInterface;
    public MainMenuInterface mainMenu;
    GraphicInterface me = this;

    // La barre de menu
    JMenuBar mb = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    JMenuItem save = new JMenuItem("Sauvegarder");
    JMenuItem ng = new JMenuItem("Nouvelle Partie");
    JMenuItem mm = new JMenuItem("Menu Principal");


    /**
     * Constructeur de l'interface graphique (fenêtre) du jeu.
     * @param g Jeu à associer à la fenêtre.
     * @param ec Collecteur des événements de la fenêtre.
     */
    GraphicInterface(Game g, EventCollector ec) {
        game = g;
        eventCollector = ec;

    }
    GameInterface getGameInterface(){
        return gameInterface;
    }
    /**
     * Permet de démarrer l'affichage effectif du jeu en cours.
     * @param g Jeu à associer à la fenêtre.
     * @param ec Collecteur des événements de la fenêtre.
     */
    public static void start(Game g, EventCollector ec) {
        int i = 0;
    	GraphicInterface view = new GraphicInterface(g, ec);
        ec.addUI(view);
        SwingUtilities.invokeLater(view);
        while (true) {
            if (g.movePhase()) {
                if (!ec.startTurn()) {
                    g.endGame();
                    return;
                }
            } else {
                if (g.getCurrentPlayer().isAI()) {
                    ec.startAITurn();
                }
            }
        }
    }

    @Override
    public void run() {
        final int TIMER_DELAY = 16; // Constante de délai du timer.

        // Éléments de l'interface principale.
        frame = new JFrame("Hey, that's my fish !");
        frame.addComponentListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Opération de sortie par défaut.
        frame.setMinimumSize(
                new Dimension(915, Math.min(1000, Toolkit.getDefaultToolkit().getScreenSize().height))
        ); // Définition de la taille de fenêtre par défaut.

        mainMenu = new MainMenuInterface(eventCollector, this);
       // gameInterface = new GameInterface(game, eventCollector,this);

        //La barre de menu
        save.addActionListener(this);
        ng.addActionListener(this);
        mm.addActionListener(this);

        menu.add(save);
        menu.add(ng);
        menu.add(mm);

        mb.add(menu);

        frame.setJMenuBar(mb);
        frame.getJMenuBar().setVisible(false);
        // Retransmission des événements au contrôleur.
        frame.addKeyListener(new GameKeyAdapter(eventCollector));
        Timer t = new Timer(TIMER_DELAY, new TimerAdapter(eventCollector));

        // Mise en place de l'interface principale.
        frame.setContentPane(mainMenu.p_main); // On ajoute le jeu à l'interface.
        t.start(); // Début du timer.
        frame.setVisible(true); // On rend la fenêtre visible.
    }

    @Override
    public void toggleFullscreen() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice dev = env.getDefaultScreenDevice();
        if (maximized) {
            dev.setFullScreenWindow(null);
            maximized = false;
        } else {
            dev.setFullScreenWindow(frame);
            maximized = true;
        }
    }


    //Méthode qui met à jour l'interface lors du redimensionnement de la fenêtre
    @Override
    public void componentResized(ComponentEvent e) {
        if(gameInterface != null){
            gameInterface.resize();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    //Gestion des clics sur la barre de menu en haut de l'écran
    public void actionPerformed(ActionEvent e){
        frame.getJMenuBar().setVisible(false);
        if(e.getSource()==save){
            SaveInterface si = new SaveInterface( (JPanel) frame.getContentPane(), "game", eventCollector, me);
            frame.getRootPane().setContentPane(si.p_main);
            si.p_main.getRootPane().updateUI();
        } else if (e.getSource()==ng){
            if(GameInterface.saved){
                NewGameInterface ng = new NewGameInterface(eventCollector, me);
                frame.getRootPane().setContentPane(ng.p_main);
                ng.p_main.getRootPane().updateUI();
            } else {
                QuitGameInterface qg = new QuitGameInterface((JPanel) frame.getContentPane(), "ng",eventCollector ,me);
                frame.getRootPane().setContentPane(qg.p_main);
                qg.p_main.getRootPane().updateUI();
            }
        } else if (e.getSource()==mm){
                if(GameInterface.saved){
                    MainMenuInterface mm = new MainMenuInterface(eventCollector, me);
                    frame.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();

                } else {
                    QuitGameInterface qg = new QuitGameInterface((JPanel) frame.getContentPane(), "mm", eventCollector, me);
                    frame.getRootPane().setContentPane(qg.p_main);
                    qg.p_main.getRootPane().updateUI();
                }

            }
    }

    //Mise à jour de la fenêtre avec la barre de menu
    public void updateGIUI(){
        frame.getJMenuBar().setVisible(true);
        frame.getRootPane().updateUI();
    }

}
