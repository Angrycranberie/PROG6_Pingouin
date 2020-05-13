package view;

import model.Game;

import java.awt.*;


/**
 * Vue effective du jeu en cours.
 * @author Alexis
 * @author Mathias
 */
public class GameView extends GraphicGame {
    Game game; // Jeu à afficher.

    Image tile = loadImage("../../res/img/tiles/Tile0.png");

    /**
     * Constructeur de l'affichage du jeu.
     * @param g Jeu à afficher.
     */
    GameView(Game g) {
        game = g;
        g.addPropertyChangeListener(this);
        setBackground(new Color(127, 127, 255));
        setVisible(true);
    }

    @Override
    void generateBoard() {
        drawImageAt(tile, 50, 50, 128, 128);
    }
}
