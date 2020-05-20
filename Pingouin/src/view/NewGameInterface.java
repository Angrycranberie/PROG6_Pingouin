package view;

import controller.ControllerMediator;
import controller.Player;
import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameInterface {
    private JTextField tf_joueur2;
    private JTextField tf_joueur1;
    private JComboBox cb_joueur2;
    private JComboBox cb_joueur1;
    private JButton b_startGame;
    private JButton b_cancel;
    private JLabel l_joueur2;
    private JLabel l_title;
    private JLabel l_joueur1;
    private JLabel l_VS;
    public JPanel p_main;
    
    EventCollector eventCollector;
   
   
     NewGameInterface(EventCollector ec, GraphicInterface gra){
        eventCollector = ec;

        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenuInterface mm = new MainMenuInterface(eventCollector, gra);
                p_main.getRootPane().setContentPane(mm.p_main);
                mm.p_main.getRootPane().updateUI();
            }
        };

        ActionListener al_startGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game g = new Game(2, new Player(4, 0,tf_joueur1.getText()), new Player(4, 1,tf_joueur2.getText()),null,null);
                gra.gameInterface = new GameInterface(g, new ControllerMediator(g), gra);
                p_main.getRootPane().setContentPane(gra.gameInterface.p_main);
                gra.gameInterface.p_main.getRootPane().getJMenuBar().setVisible(true);
                gra.gameInterface.p_main.getRootPane().updateUI();
            }
        };

        b_cancel.addActionListener(al_cancel);
        b_startGame.addActionListener(al_startGame);

    }


}
