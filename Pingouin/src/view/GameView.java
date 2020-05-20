package view;

import controller.Player;
import model.Game;
import model.Penguin;
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
    EventCollector eventCollector;

    private final String TILES_PATH = "/gfx/game/tiles/"; // Chemin des fichiers images de tuiles.
    private final String PENGUINS_PATH = "/gfx/game/penguins/"; // Chemin des fichiers images des pingouins.
    private final String PNG_EXT = ".png"; // Extension des fichiers images des tuiles.

    private Image[] tilesImg; // Tableau des images de tuiles.
    private Image[][] penguinsImg; // Tableau des images de pingouins.

    /**
     * Constructeur de l'affichage du jeu.
     * @param g Jeu à afficher.
     */
    GameView(Game g, EventCollector ec) {
        game = g;
        eventCollector = ec;

        // Chargement des textures des tuiles.
        tilesImg = new Image[4];
        for (int i = 0; i < 4; i++)	tilesImg[i] = loadImage(TILES_PATH + "Tile" + i + PNG_EXT);

        // Chargement des textures des pingouins.
        penguinsImg = new Image[4][4];
        for (int j = 0; j < 4; j++)
            for (int k = 0; k < 4; k++)
                penguinsImg[j][k] = loadImage(PENGUINS_PATH + j + "_" + k + PNG_EXT);

        setLayout(new GroupLayout(this));
        setOpaque(true);
        setVisible(true);
        repaint();
    }

    /**
     * Retourne la position X d'une tuile vis-à-vis des paramètres donné en entrée.
     * @param r Numéro de ligne.
     * @param c Numéro de colonne.
     * @param w Largeur d'une tuile.
     * @param g Gouttière entre les tuiles.
     * @param cx Décalage pour centrage horizontal.
     * @return Coordonnée X du coin supérieur gauche d'une tuile.
     */
    private int getTileX(int r, int c, int w, int g, int cx) {
        return ((r % 2 == 0) ? ((w + g) / 2) + ((w + g) * c) : (w + g) * c) + cx;
    }

    /**
     * Retourne la position Y d'une tuile vis-à-vis des paramètres donné en entrée.
     * @param r Numéro de ligne.
     * @param h Hauteur d'une tuile.
     * @param g Gouttière entre les tuiles.
     * @param cy Décalage pour centrage vertical.
     * @return Coordonnée Y du coin supérieur gauche d'une tuile.
     */
    private int getTileY(int r, int h, int g, int cy) {
        return (h - h/4 + g) * r + cy;
    }

    /**
     * Méthode de création du plateau de jeu.
     */
    @Override
    void generateBoard() {
        int h = getHeight()/8, w = (int)((Math.sqrt(3)/2) * h); // Hauteur et largeur des tuiles à afficher.
        final int g = 10; // Taille de la gouttière entre les tuiles.
        int cx = (getWidth() - (w + g) * 7 - w) / 2; // Valeur de décalage pour centrage horizontal.
        int cy = (getHeight() - (h - h/4 + g) * 7 - h) / 2; // Valeur de décalage pour centrage vertical.

        drawable.setColor(new Color(127,148,255)); // Définition de la couleur du fond.
        drawable.fillRect(0, 0, getWidth(), getHeight()); // Remplissage de la couleur du fond.

        // Création du plateau tuile par tuile.
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                if (i %2 == 1 || j < 7) {
                    // Coordonnées x et y pour positionner l'image et son label.
                    int x =  getTileX(i, j, w, g, cx), y = getTileY(i, h, g, cy);
                    Tile t = game.getBoard().getTile(j,i); // Récupération de la case du jeu.
                    // Récupération de l'image de la tuile avec le bon nombre de poissons.
                    Image img = tilesImg[t != null ? t.getFishNumber() : 0].getScaledInstance(w, h, Image.SCALE_SMOOTH);

                    JLabel l = new JLabel(); // Label cliquable associé à l'image.
                    l.setName(j+":"+i); // Nom de la tuile : "colonne:ligne".
                    l.setBounds(x, y + ((h / 4 + g) / 4), w, h - ((h / 4 + g) / 2));
                    l.setVisible(true); // Le label est "visible" (cliquable)...
                    l.setOpaque(false); // ... mais pas opaque (invisible par dessus les images de tuiles).

                    if (t != null) {
                        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        drawImageAt(img, x, y, w, h);
                    } else {
                        l.setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    }
                    l.addMouseListener(new GameMouseAdapter(this, eventCollector));
                    add(l);
                }
            }
        }
    }

    @Override
    void placePenguins() {
        // TODO : Enlever la répétition de cette section. Nécessaire pour le moment car permet d'éviter une exception de thread.
        int h = getHeight()/8, w = (int)((Math.sqrt(3)/2) * h); // Hauteur et largeur des tuiles à afficher.
        final int g = 10; // Taille de la gouttière entre les tuiles.
        int cx = (getWidth() - (w + g) * 7 - w) / 2; // Valeur de décalage pour centrage horizontal.
        int cy = (getHeight() - (h - h/4 + g) * 7 - h) / 2; // Valeur de décalage pour centrage vertical.
        int s = 15;

        for (int i = 0; i < game.getPlayerCount(); i++) {
            Player ply = game.getPlayer(i);
            int color = Math.max(ply.getColor(), 0);
            for (int j = 0; j < ply.getPenguinsCount(); j++) {
                Penguin png = ply.getPenguin(j);
                if (png != null && ply.isPlaying()) {
                    Image img = penguinsImg[color][j].getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    int x = getTileX(png.getY(), png.getX(), w, g, cx);
                    int y = getTileY(png.getY(), h, g, cy)-s;
                    drawImageAt(img, x, y, w, h);
                }
            }
        }
    }
}
