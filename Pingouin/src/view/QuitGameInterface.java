package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuitGameInterface {
    private JButton b_save;
    private JLabel l_title;
    private JLabel l_warning;
    private JButton b_resume;
    private JButton b_quit;
    public JPanel p_main;

    QuitGameInterface(final JPanel g, final String s, GraphicInterface gra){
    EventCollector eventCollector;

    QuitGameInterface(final JPanel g, final String s, final EventCollector ec){
        eventCollector = ec;

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
                    MainMenuInterface mm = new MainMenuInterface(gra);
                if(s.equals("mm")){
                    MainMenuInterface mm = new MainMenuInterface(ec);
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();
                } else if(s=="ng"){
                    NewGameInterface ng = new NewGameInterface(gra);
                } else if(s.equals("ng")){
                    NewGameInterface ng = new NewGameInterface(ec);
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                }

            }
        };

        ActionListener al_save = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveInterface si = new SaveInterface(g,s,gra);
                SaveInterface si = new SaveInterface(g, s, ec);
                p_main.getRootPane().setContentPane(si.p_main);
                si.p_main.getRootPane().updateUI();
            }
        };

        b_quit.addActionListener(al_quit);
        b_resume.addActionListener(al_resume);
        b_save.addActionListener(al_save);

    }

}
