package view;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Vue graphique préparée du jeu.
 * @author Alexis
 * @author Mathias
 */
public abstract class GraphicGame extends JPanel implements PropertyChangeListener {
    Graphics2D drawable;

    protected Image loadImage(String filename) {
        ImageIcon ii = new ImageIcon(filename);
        return ii.getImage();
    }

    protected void drawImageAt(Image i, int x, int y, int w, int h) {
        drawable.drawImage(i, x, y, w, h, null);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
    }

    abstract void generateBoard();

    public void paintComponent(Graphics g) {
        drawable = (Graphics2D) g;
        drawable.clearRect(0, 0, getWidth(), getHeight());
        generateBoard();
    }
}
