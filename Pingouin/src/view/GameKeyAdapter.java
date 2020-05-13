package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Adaptateur de clavier pour la fenêtre de jeu principale.
 * @author Alexis
 * @author Mathias
 */
public class GameKeyAdapter extends KeyAdapter {
    EventCollector eventCollector; // Collecteur d'événements de la fenêtre.

    /**
     * Constructeur de l'adapteur de clavier.
     * @param ec Collecteur d'événements à associer à l'adapteur.
     */
    GameKeyAdapter(EventCollector ec) {
        eventCollector = ec;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO
    }
}
