package view;

import model.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameInterface {
    public JPanel p_main;
    private JButton b_save;
    private JButton b_newGame;
    private JButton b_backMainMenu;
    private JButton b_undo;
    private JButton b_redo;
    private JLabel l_turnOrder;
    private JLabel l_scoreJ1;
    private JLabel l_scoreJ2;
    private GameView gameView;

    private boolean saved = false;


    GameInterface(Game g){

        GameInterface me = this;

        p_main = new JPanel();
        p_main.setSize(900,900);
        p_main.setLayout(new GroupLayout(p_main));

        b_newGame= new JButton();
        b_newGame.setText("Nouvelle Partie");
        b_newGame.setSize(150,30);
        b_newGame.setLocation(50, 5);
        b_newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saved){
                    NewGameInterface ng = new NewGameInterface();
                    p_main.getRootPane().setContentPane(ng.p_main);
                    ng.p_main.getRootPane().updateUI();
                } else {
                    QuitGameInterface qg = new QuitGameInterface(me, "ng");
                    p_main.getRootPane().setContentPane(qg.p_main);
                    qg.p_main.getRootPane().updateUI();
                }
            }
        });

        b_save= new JButton();
        b_save.setText("Sauvegarder");
        b_save.setSize(150,30);
        b_save.setLocation(b_newGame.getX()+b_newGame.getWidth()+10, 5);
        b_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveInterface si = new SaveInterface(me);
                p_main.getRootPane().setContentPane(si.p_main);
                si.p_main.getRootPane().updateUI();
            }
        });

        b_backMainMenu= new JButton();
        b_backMainMenu.setText("Retour au menu principal");
        b_backMainMenu.setSize(200,30);
        b_backMainMenu.setLocation(b_save.getX()+b_save.getWidth()+10, 5);
        b_backMainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(saved){
                    MainMenuInterface mm = new MainMenuInterface();
                    p_main.getRootPane().setContentPane(mm.p_main);
                    mm.p_main.getRootPane().updateUI();

                } else {
                    QuitGameInterface qg = new QuitGameInterface(me, "mm");
                    p_main.getRootPane().setContentPane(qg.p_main);
                    qg.p_main.getRootPane().updateUI();
                }

            }
        });



        gameView = new GameView(g);
        gameView.setLocation(50,b_newGame.getHeight()+50);
        gameView.setSize(800,800);



        l_turnOrder = new JLabel();
        l_turnOrder.setText("La partie va commencer");
        //l_turnOrder.setHorizontalAlignment(SwingConstants.CENTER);
        //l_turnOrder.setHorizontalTextPosition(SwingConstants.CENTER);
        l_turnOrder.setSize(150,10);
        l_turnOrder.setLocation(gameView.getWidth()/2, gameView.getY() - 20);



        b_undo= new JButton();
        b_undo.setText("Annuler le coup");
        b_undo.setSize(150,50);
        b_undo.setLocation(50, gameView.getY() +gameView.getHeight() + 10);
        b_undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b_redo= new JButton();
        b_redo.setText("Refaire le coup");
        b_redo.setSize(150,50);
        b_redo.setLocation(b_undo.getLocation().x+b_undo.getWidth()+10, gameView.getY() +gameView.getHeight() + 10);
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
        l_scoreJ1.setLocation(b_redo.getLocation().x+b_redo.getWidth()+10, gameView.getY() +gameView.getHeight() + 10);

        l_scoreJ2 = new JLabel();
        l_scoreJ2.setText("Score joueur 2 : ");
        l_scoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ2.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ2.setSize(150,10);
        l_scoreJ2.setLocation(l_scoreJ1.getLocation().x+l_scoreJ1.getWidth()+10, gameView.getY() +gameView.getHeight() + 10);

        p_main.add(b_newGame);
        p_main.add(b_save);
        p_main.add(b_backMainMenu);
        p_main.add(l_turnOrder);
        p_main.add(gameView);
        p_main.add(b_undo);
        p_main.add(b_redo);
        p_main.add(l_scoreJ1);
        p_main.add(l_scoreJ2);
    }

    public JLabel getL_turnOrder() {
        return l_turnOrder;
    }

    public JLabel getL_scoreJ1() {
        return l_scoreJ1;
    }

    public JLabel getL_scoreJ2() {
        return l_scoreJ2;
    }

    public void setL_turnOrder(JLabel l_turnOrder) {
        this.l_turnOrder = l_turnOrder;
    }

    public void setL_scoreJ1(JLabel l_scoreJ1) {
        this.l_scoreJ1 = l_scoreJ1;
    }

    public void setL_scoreJ2(JLabel l_scoreJ2) {
        this.l_scoreJ2 = l_scoreJ2;
    }
}
