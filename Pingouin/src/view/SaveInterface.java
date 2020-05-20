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
    public JPanel p_main;

    EventCollector eventCollector;


    SaveInterface(final JPanel g, final String s, EventCollector ec){
        eventCollector = ec;

        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu confirm = new JPopupMenu();

                switch (s) {
                    case "game":
                        p_main.getRootPane().setContentPane(g);
                        g.getRootPane().updateUI();
                        break;
                    case "ng":
                        NewGameInterface ng = new NewGameInterface(eventCollector);
                        p_main.getRootPane().setContentPane(ng.p_main);
                        ng.p_main.getRootPane().updateUI();
                        break;
                    case "mm":
                        MainMenuInterface mm = new MainMenuInterface(eventCollector);
                        p_main.getRootPane().setContentPane(mm.p_main);
                        mm.p_main.getRootPane().updateUI();
                        break;
                }
            }
        };
        ActionListener al_cancel = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (s) {
                    case "game":
                        p_main.getRootPane().setContentPane(g);
                        g.getRootPane().getJMenuBar().setVisible(true);
                        g.getRootPane().updateUI();
                        break;
                    case "ng":
                        NewGameInterface ng = new NewGameInterface(eventCollector);
                        p_main.getRootPane().setContentPane(ng.p_main);
                        ng.p_main.getRootPane().updateUI();
                        break;
                    case "mm":
                        MainMenuInterface mm = new MainMenuInterface(eventCollector);
                        p_main.getRootPane().setContentPane(mm.p_main);
                        mm.p_main.getRootPane().updateUI();
                        break;
                }

            }
        };

        b_cancel.addActionListener(al_cancel);
        b_save.addActionListener(al_save);

    }

}
