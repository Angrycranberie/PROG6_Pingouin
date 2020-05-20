package view;

import model.Game;
import sun.misc.JavaLangAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

public class GameInterface implements PropertyChangeListener {
    public JPanel p_main;
    private JButton b_undo;
    private JButton b_redo;
    private JLabel l_title;
    private JLabel l_feedback;
    private JLabel l_scoreJ1;
    private JLabel l_scoreJ2;

    public Game game;
    public GameView gameView;
    public EventCollector eventCollector;
    public static boolean saved = false;
    GraphicInterface gra;

    HashMap<Integer, String> statusString;

    GameInterface(Game g, EventCollector ec, GraphicInterface gra){
        this.gra = gra;
        final GameInterface me = this;
        game = g;
        game.addPropertyChangeListener(this);
        eventCollector = ec;

        populateStatusString(); // Association des statuts à leur feedback.

        p_main = new JPanel();
        p_main.setSize(gra.frame.getSize());
        p_main.setLayout(new GroupLayout(p_main));

        int logoHeight = (int) (p_main.getHeight()*0.1);
        Image logo = GraphicGame.loadImage("/gfx/ui/logo.png")
                .getScaledInstance(logoHeight*3, logoHeight, Image.SCALE_SMOOTH);
        l_title = new JLabel();
        l_title.setBounds(10,10, logoHeight*3, logoHeight);
        l_title.setIcon(new ImageIcon(logo));
        l_title.setOpaque(true);
        l_title.setVisible(true);

        l_feedback = new JLabel();
        l_feedback.setText("La partie va commencer");
        l_feedback.setHorizontalAlignment(SwingConstants.CENTER);
        l_feedback.setHorizontalTextPosition(SwingConstants.CENTER);
        l_feedback.setBounds(l_title.getWidth()+10, 10, p_main.getWidth()-l_title.getWidth()-2*10, l_title.getHeight());

        gameView = new GameView(game, eventCollector);
        gameView.setBounds(0, l_title.getHeight()+2*10, p_main.getWidth(), (int) (p_main.getHeight()*0.8));

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

        l_scoreJ2 = new JLabel();
        l_scoreJ2.setText("Score joueur 2 : ");
        l_scoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ2.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ2.setSize(150,10);
        l_scoreJ2.setLocation(p_main.getWidth()/2 + (l_scoreJ2.getWidth()), gameView.getY() +gameView.getHeight() + 10);

        l_scoreJ1 = new JLabel();
        l_scoreJ1.setText("Score joueur 1 : ");
        l_scoreJ1.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ1.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ1.setSize(150,10);
        l_scoreJ1.setLocation(p_main.getWidth()/2 - (l_scoreJ1.getWidth()), gameView.getY() +gameView.getHeight() + 10);

        p_main.add(l_title);
        p_main.add(l_feedback);
        p_main.add(gameView);
        p_main.add(b_undo);
        p_main.add(b_redo);
        p_main.add(l_scoreJ1);
        p_main.add(l_scoreJ2);
    }

    public JLabel getFeedbackLabel() {
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
        updateFeedback();
    }

    public void redimensionnement(){
        p_main.setSize(gra.frame.getSize());
        gameView.setSize(p_main.getWidth(), gameView.getHeight());
        //l_title.setLocation((p_main.getWidth()-1500/4)/2,0);
        l_feedback.setBounds(l_title.getWidth()+10, 10, p_main.getWidth()-l_title.getWidth()-2*10, l_title.getHeight());
        l_scoreJ2.setLocation(p_main.getWidth()/2 - (l_scoreJ2.getWidth()), gameView.getY() +gameView.getHeight() + 10);
        l_scoreJ1.setLocation(p_main.getWidth()/2 + (l_scoreJ1.getWidth()), gameView.getY() +gameView.getHeight() + 10);

    }

    private void populateStatusString() {
        statusString = new HashMap<Integer, String>();
        statusString.put(
                Game.PENGUIN_PLACED,
                "$1, vous avez placé votre $2 pingouin !\n$3, placez votre $4 pingouin."
        );
        statusString.put(
                Game.ONLY_ONE_FISH,
                "Vous ne pouvez placer votre pingouin que sur les cases ayant un poisson, $1 !"
        );
        statusString.put(
                Game.ALREADY_OCCUPIED,
                "Mince ! Un pingouin occupe déjà cette case.\nChoisissez-en une autre, $1 !"
        );
    }

    public void updateFeedback() {
        l_feedback.setText(statusString.get(game.status));
    }
}
