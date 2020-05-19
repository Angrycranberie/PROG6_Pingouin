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
    public JPanel p_main;

    QuitGameInterface(final JPanel g, final String s){

        ActionListener al_resume = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p_main.getRootPane().setContentPane(g);
                g.getRootPane().getJMenuBar().setVisible(true);
                g.getRootPane().updateUI();
            }
        };

        ActionListener al_quit = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(s=="mm"){
                    MainMenuInterface mm = new MainMenuInterface();
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();
                } else if(s=="ng"){
                    NewGameInterface ng = new NewGameInterface();
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                }

            }
        };

        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveInterface si = new SaveInterface(g,s);
                p_main.getRootPane().setContentPane(si.p_main);
                si.p_main.getRootPane().updateUI();
            }
        };

        b_quit.addActionListener(al_quit);
        b_resume.addActionListener(al_resume);
        b_save.addActionListener(al_save);

    }

}
