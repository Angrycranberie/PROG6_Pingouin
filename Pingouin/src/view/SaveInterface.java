package view;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveInterface {
    private JTextField tf_saveName;
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_save;
    private JButton b_cancel;
    public JPanel p_main;


    SaveInterface(GameInterface g, String s){


        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu confirm = new JPopupMenu();

                if(s == "game"){
                    p_main.getRootPane().setContentPane(g.p_main);
                    g.p_main.getRootPane().updateUI();
                } else if (s == "ng"){
                    NewGameInterface ng = new NewGameInterface();
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                } else if (s == "mm") {
                    MainMenuInterface mm = new MainMenuInterface();
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();
                }
            }
        };
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(s == "game"){
                    p_main.getRootPane().setContentPane(g.p_main);
                    g.p_main.getRootPane().updateUI();
                } else if (s == "ng"){
                    NewGameInterface ng = new NewGameInterface();
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                } else if (s == "mm") {
                    MainMenuInterface mm = new MainMenuInterface();
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();
                }

            }
        };

        b_cancel.addActionListener(al_cancel);
        b_save.addActionListener(al_save);

    }

}
