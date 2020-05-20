package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadGameInterface {
    private JComboBox cb_select;
    public JPanel p_main;
    private JButton b_cancel;
    private JButton b_loadGame;
    private JLabel l_title;
    private JLabel l_select;

    LoadGameInterface(final MainMenuInterface mm, GraphicInterface gra){
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_main.getRootPane().setContentPane(mm.p_main);
                mm.p_main.getRootPane().updateUI();
            }
        };
        ActionListener al_loadGame = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_cancel.addActionListener(al_cancel);
        b_loadGame.addActionListener(al_loadGame);


    }

}
