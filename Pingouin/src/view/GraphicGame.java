package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;

/**
 * Vue graphique préparée du jeu.
 * @author Alexis
 * @author Mathias
 */
public abstract class GraphicGame extends JPanel {
    Graphics2D drawable;

    /**
     * Fonction de chargement d'une image pour insertion dans l'UI.
     * @param filename Chemin du fichier d'image à charger.
     * @return Image chargée depuis le chemin spécifié.
     */
    public static Image loadImage(String filename) {
        Image i = null;
        try {
            i = ImageIO.read(GraphicGame.class.getResource(filename));
        } catch (Exception e) {
            System.out.println("L'image '"+filename+"' n'a pas pu être chargée. ("+e.toString()+")");
        }
        return i;
    }

    protected void drawImageAt(Image i, int x, int y, int w, int h) {
        drawable.drawImage(i, x, y, w, h, null);
    }

    abstract void generateBoard();

    @Override
    public void paintComponent(Graphics g) {
        drawable = (Graphics2D) g;
        drawable.clearRect(0, 0, getWidth(), getHeight());
        generateBoard();
    }
}
