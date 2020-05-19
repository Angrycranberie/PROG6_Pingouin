package view;

import model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameInterface implements PropertyChangeListener {
    public JPanel p_main;
    private JButton b_save;
    private JButton b_newGame;
    private JButton b_backMainMenu;
    private JButton b_undo;
    private JButton b_redo;
    private JLabel l_title;
    private JLabel l_feedback;
    private JLabel l_scoreJ1;
    private JLabel l_scoreJ2;

    public Game game;
    public GameView gameView;
    public EventCollector eventCollector;
    private boolean saved = false;


    GameInterface(Game g, EventCollector ec){
        final GameInterface me = this;
        InGameMenuInterface menu = new InGameMenuInterface(this);
        game = g;
        game.addPropertyChangeListener(this);
        eventCollector = ec;

        p_main = new JPanel();
        p_main.setSize(900,900);
        p_main.setLayout(new GroupLayout(p_main));

        Image logo = GraphicGame.loadImage("/gfx/ui/logo.png")
                .getScaledInstance(1500/4, 500/4, Image.SCALE_SMOOTH);
        l_title = new JLabel();
        l_title.setBounds((p_main.getWidth()-1500/4)/2,0, 1500/4, 500/4);
        l_title.setIcon(new ImageIcon(logo));
        l_title.setOpaque(true);
        l_title.setVisible(true);

        l_feedback = new JLabel();
        l_feedback.setText("La partie va commencer");
        l_feedback.setHorizontalAlignment(SwingConstants.CENTER);
        l_feedback.setHorizontalTextPosition(SwingConstants.CENTER);
        l_feedback.setBounds(0, l_title.getHeight(), p_main.getWidth(),50);

        gameView = new GameView(game, eventCollector);
        gameView.setBounds(0, l_title.getHeight()+ l_feedback.getHeight(), p_main.getWidth(), (int) (p_main.getHeight()*0.8));

        b_newGame= new GameButton("Nouvelle partie", GameButton.TYPE_INFO);
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

        b_save= new GameButton("Sauvegarder", GameButton.TYPE_INFO);
        b_save.setSize(150,30);
        b_save.setLocation(b_newGame.getX()+b_newGame.getWidth()+10, 5);
        b_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveInterface si = new SaveInterface(me, "game");
                p_main.getRootPane().setContentPane(si.p_main);
                si.p_main.getRootPane().updateUI();
            }
        });

        b_backMainMenu= new GameButton("Retour au menu principal", GameButton.TYPE_ALERT);
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

        b_undo= new GameButton("Annuler", GameButton.TYPE_DEFAULT);
        b_undo.setSize(150,50);
        b_undo.setLocation(50, gameView.getY() +gameView.getHeight() + 10);
        b_undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        b_redo= new GameButton("Refaire", GameButton.TYPE_DEFAULT);
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
        p_main.add(l_title);
        p_main.add(l_feedback);
        p_main.add(gameView);
        p_main.add(b_undo);
        p_main.add(b_redo);
        p_main.add(l_scoreJ1);
        p_main.add(l_scoreJ2);
    }

    public JLabel getL_feedback() {
        return l_feedback;
    }

    public JLabel getL_scoreJ1() {
        return l_scoreJ1;
    }

    public JLabel getL_scoreJ2() {
        return l_scoreJ2;
    }

    public void setL_feedback(JLabel l_feedback) {
        this.l_feedback = l_feedback;
    }

    public void setL_scoreJ1(JLabel l_scoreJ1) {
        this.l_scoreJ1 = l_scoreJ1;
    }

    public void setL_scoreJ2(JLabel l_scoreJ2) {
        this.l_scoreJ2 = l_scoreJ2;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        gameView.repaint();
    }
}
