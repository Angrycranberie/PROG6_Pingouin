package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Interface graphique principale du jeu.
 * Celle-ci appelle le plateau graphique pour le rendre visible.
 * @author Alexis
 * @author Mathias
 */
public class GraphicInterface implements Runnable, UserInterface {

    Game game; // Le jeu en lui-même.
    EventCollector eventCollector; // Collecteur d'événements pour garantir l'interaction avec le jeu.
    JFrame frame; // Composant de la fenêtre de jeu.
    GraphicGame graphicGame; // Plateau de jeu graphique.
    GameView gameView; // Vue graphique effective du jeu.
    boolean maximized; // Si la fenêtre est en pleine écran ou non.

    /**
     * Constructeur de l'interface graphique (fenêtre) du jeu.
     * @param g Jeu à associer à la fenêtre.
     * @param ec Collecteur des événements de la fenêtre.
     */
    GraphicInterface(Game g, EventCollector ec) {
        game = g;
        eventCollector = ec;
    }

    /**
     * Permet de démarrer l'affichage effectif du jeu en cours.
     * @param g Jeu à associer à la fenêtre.
     * @param ec Collecteur des événements de la fenêtre.
     */
    public static void start(Game g, EventCollector ec) {
        GraphicInterface view = new GraphicInterface(g, ec);
        ec.addUI(view);
        SwingUtilities.invokeLater(view);
    }

    @Override
    public void run() {
        final int TIMER_DELAY = 16; // Constante de délai du timer.

        // Éléments de l'interface principale. - TODO
        frame = new JFrame("Hey, that's my fish !");
        gameView = new GameView(game);
        gameView.setMinimumSize(frame.getSize());

        // Retransmission des événements au contrôleur. - TODO
        gameView.addMouseListener(new GameMouseAdapter(graphicGame, eventCollector));
        frame.addKeyListener(new GameKeyAdapter(eventCollector));
        Timer t = new Timer(TIMER_DELAY, new TimerAdapter(eventCollector));

        // Mise en place de l'interface principale. - TODO
        //frame.add(gameView); // On ajoute le jeu à l'interface.
        frame.setContentPane(gameView.panelMain);
        t.start(); // Début du timer.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Opération de sortie par défaut.
        frame.setMinimumSize(new Dimension(800, 500)); // Définition de la taille de fenêtre par défaut.
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
}
