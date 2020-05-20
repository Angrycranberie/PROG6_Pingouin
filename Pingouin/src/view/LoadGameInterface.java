package view;

import javax.swing.*;
import javax.swing.event.ListDataListener;
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
        ComboBoxModel<String> cbm = new ComboBoxModel<String>() {
            @Override
            public void setSelectedItem(Object anItem) {

            }

            @Override
            public Object getSelectedItem() {
                return null;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public String getElementAt(int index) {
                return null;
            }

            @Override
            public void addListDataListener(ListDataListener l) {

            }

            @Override
            public void removeListDataListener(ListDataListener l) {

            }
        };
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
               // gra.game.load()
            }
        };

        b_cancel.addActionListener(al_cancel);
        b_loadGame.addActionListener(al_loadGame);


    }

}
