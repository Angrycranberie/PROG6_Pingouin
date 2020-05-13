package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveInterface {
    private JTextField tf_saveName;
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_save;
    private JButton b_cancel;

    SaveInterface(){
        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_cancel.addActionListener(al_cancel);
        b_save.addActionListener(al_save);

    }

}
