package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitGameInterface {
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_warning;
    private JButton b_resume;
    private JButton b_quit;

    QuitGameInterface(){

        ActionListener al_resume = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };

        b_quit.addActionListener(al_quit);
        b_resume.addActionListener(al_resume);
        b_save.addActionListener(al_save);

    }

}
