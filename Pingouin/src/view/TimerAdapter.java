package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adaptateur de timer.
 * On effectue une action pour chaque intervalle de temps défini.
 * @author Alexis
 * @author Mathias
 */
public class TimerAdapter implements ActionListener {
    EventCollector eventCollector; // Collecteur d'événements de la fenêtre.

    /**
     * Constructeur de l'adaptateur du timer.
     * @param ec Collecteur d'événements à associer à l'adaptateur.
     */
    TimerAdapter(EventCollector ec) {
        eventCollector = ec;
    }

    /**
     * Action effectuée pour chaque intervalle de temps.
     * @param e Événement associé à l'action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        eventCollector.timedAction();
    }
}
