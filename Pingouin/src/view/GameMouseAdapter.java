package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Adaptateur de souris pour la fenêtre de jeu principale.
 * @author Alexis
 * @author Mathias
 */
public class GameMouseAdapter extends MouseAdapter {
    GraphicGame graphicGame; // Vue du jeu.
    EventCollector eventCollector; // Collecteur d'événements de la fenêtre.

    /**
     * Constructeur de l'adapteur de souris.
     * @param gg Vue du jeu à associer à l'adapteur.
     * @param ec Collecteur d'événements à associer à l'adapteur.
     */
    GameMouseAdapter(GraphicGame gg, EventCollector ec) {
        graphicGame = gg;
        eventCollector = ec;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO
    }
}
