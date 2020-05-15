package view;

import model.Game;
import model.Tile;

import javax.swing.*;
import java.awt.*;


/**
 * Vue effective du jeu en cours.
 * @author Alexis
 * @author Mathias
 */
public class GameView extends GraphicGame {
    Game game; // Jeu à afficher.
    Image[] tiles; // Tableau des images de tuiles.
    String Path = "/img/game/tiles/Tile";

    /**
     * Constructeur de l'affichage du jeu.
     * @param g Jeu à afficher.
     */
    GameView(Game g) {
        game = g;
        setLayout(new GroupLayout(this));
        g.addPropertyChangeListener(this);

        // Chargement des textures des tuiles.
        tiles = new Image[4];
        for (int i = 0; i < 4; i++)  {
        	tiles[i] = loadImage(Path + i + ".png");
        }
        
        setOpaque(true);
        setVisible(true);
        generateBoard();
    }

    @Override
    void generateBoard() {
        int w = 100, h = 100; // Largeur et hauteur des tuiles à afficher.
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                Tile t = game.getBoard().getTile(j,i);
                TileButton b = new TileButton(j+","+i, w, h);
                if (i %2 == 1 || j < 7) {
                	
                    b.setIcon(new ImageIcon(tiles[t.getFishNumber()].getScaledInstance(w, h, Image.SCALE_SMOOTH)));
                }
                else b.setEnabled(false);
                if(i%2 == 0) b.setLocation((w/2) + w*j,h*i);
                else b.setLocation( w*j,h*i);
                add(b);
            }
        }
    }

}
