package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Adaptateur de souris pour la fenêtre de jeu principale.
 * @author Alexis
 * @author Mathias
 */
public class GameMouseAdapter extends MouseAdapter {
    GameView gameView; // Vue du jeu.
    EventCollector eventCollector; // Collecteur d'événements de la fenêtre.

    /**
     * Constructeur de l'adapteur de souris.
     * @param gv Vue du jeu à associer à l'adapteur.
     * @param ec Collecteur d'événements à associer à l'adapteur.
     */
    GameMouseAdapter(GameView gv, EventCollector ec) {
        gameView = gv;
        eventCollector = ec;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        String[] p = e.getComponent().getName().split(":");
        int c = Integer.parseInt(p[0]);
        int r = Integer.parseInt(p[1]);
        System.out.println("Row : "+r+ " ; Col : "+c);
        // eventCollector.mouseClick(r, c);
    }
}
