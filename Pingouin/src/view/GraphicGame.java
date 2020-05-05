package view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Vue graphique préparée du jeu.
 * @author Alexis
 * @author Mathias
 */
public abstract class GraphicGame extends JComponent implements PropertyChangeListener {

    // TODO

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Action à effectuer lorsque le plateau change. - TODO
    }
}
