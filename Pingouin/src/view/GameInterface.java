package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface {
    public JPanel PanelMain;
    private JButton b_menu;
    private JButton b_undo;
    private JButton b_redo;
    private JLabel l_turnOrder;
    private JLabel l_scoreJ1;
    private JLabel l_scoreJ2;
    private GameView gameView;

    GameInterface(Game g){
        PanelMain = new JPanel();
        InGameMenuInterface menu = new InGameMenuInterface();
        PanelMain.setSize(900,900);
        PanelMain.setLayout(new GroupLayout(PanelMain));


        gameView = new GameView(g);
        gameView.setLocation(50,40);
        gameView.setSize(800,800);



        l_turnOrder = new JLabel();
        l_turnOrder.setText("La partie va commencer");
        l_turnOrder.setHorizontalAlignment(SwingConstants.CENTER);
        l_turnOrder.setHorizontalTextPosition(SwingConstants.CENTER);
        l_turnOrder.setSize(150,10);
        l_turnOrder.setLocation(gameView.getWidth()/2+l_turnOrder.getWidth()/2, 20);

        b_menu= new JButton();
        b_menu.setText("Menu");
        b_menu.setSize(100,50);
        b_menu.setLocation(gameView.getWidth()-b_menu.getWidth()/2, gameView.getHeight()+b_menu.getHeight());
        b_menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // PanelMain.setVisible(false);
                //PanelMain.getRootPane().setContentPane(menu.p_main);
            }
        });

        b_undo= new JButton();
        b_undo.setText("Annuler le coup");
        b_undo.setSize(150,50);
        b_undo.setLocation(50, gameView.getHeight()+b_menu.getHeight());
        b_undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b_redo= new JButton();
        b_redo.setText("Refaire le coup");
        b_redo.setSize(150,50);
        b_redo.setLocation(b_undo.getLocation().x+b_undo.getWidth()+10, gameView.getHeight()+b_menu.getHeight());
        b_redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        l_scoreJ1 = new JLabel();
        l_scoreJ1.setText("Score joueur 1 : ");
        l_scoreJ1.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ1.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ1.setSize(150,10);
        l_scoreJ1.setLocation(b_redo.getLocation().x+b_redo.getWidth()+10, gameView.getHeight()+b_menu.getHeight());

        l_scoreJ2 = new JLabel();
        l_scoreJ2.setText("Score joueur 2 : ");
        l_scoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ2.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ2.setSize(150,10);
        l_scoreJ2.setLocation(l_scoreJ1.getLocation().x+l_scoreJ1.getWidth()+10, gameView.getHeight()+b_menu.getHeight());



        PanelMain.add(l_turnOrder);
        PanelMain.add(gameView);
        PanelMain.add(b_menu);
        PanelMain.add(b_undo);
        PanelMain.add(b_redo);
        PanelMain.add(l_scoreJ1);
        PanelMain.add(l_scoreJ2);
    }

}
