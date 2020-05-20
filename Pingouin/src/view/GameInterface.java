package view;

import model.Game;
import sun.misc.JavaLangAccess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class GameInterface implements PropertyChangeListener {
    public JPanel p_main;
    private JButton b_undo;
    private JButton b_redo;
    private JLabel l_title;
    private JLabel l_feedback;
    private JLabel l_scoreJ1;
    private JLabel l_scoreJ2;
    private HashMap color;
    private Image[] pinguoins;
    private JLabel l_pingouinJ1;
    private JLabel l_pingouinJ2;


    public Game game;
    public GameView gameView;
    public EventCollector eventCollector;
    public static boolean saved = false;
    GraphicInterface gra;
    
    HashMap<Integer, String> statusString;

    GameInterface(Game g, EventCollector ec, GraphicInterface gra) throws IOException, FontFormatException {

        color = new HashMap();
        color.put((int) 0, new Color(28,28,28));
        color.put((int) 1, new Color(255, 204, 0));
        color.put((int) 2, new Color(0, 102, 255));
        color.put((int) 3, new Color(102, 255, 255));

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
        gameView.setBounds(0, l_title.getHeight()+2*10, p_main.getWidth(), (int) (p_main.getHeight()*0.7));

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

        pinguoins = new Image[4];
        for(int i = 0; i<3; i++){
            pinguoins[i] = GraphicGame.loadImage("/gfx/game/penguins/"+i+"_0.png").getScaledInstance((int)((Math.sqrt(3)/2) * gameView.getHeight()/7),gameView.getHeight()/7,Image.SCALE_SMOOTH);
        }


        Image avatarJ1 = pinguoins[game.getPlayer(0).getColor()];
        Image avatarJ2 = pinguoins[game.getPlayer(1).getColor()];
       // File font = new File("/fonts/icecube.ttf");
       // Font labelFont = Font.createFont(Font.TRUETYPE_FONT, font);

        l_scoreJ2 = new JLabel();
        //l_scoreJ2.setFont(labelFont);
        l_scoreJ2.setText(game.getPlayer(1).getName());
        l_scoreJ2.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ2.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ2.setSize(logoHeight,(int)(logoHeight*0.5));
        l_scoreJ2.setLocation(p_main.getWidth()*2/3 + (l_scoreJ2.getWidth() + (int)(p_main.getWidth()*0.05) ), gameView.getY() +gameView.getHeight() + 10);
        l_scoreJ2.setForeground((Color)color.get(game.getPlayer(1).getColor()));
        l_scoreJ2.setForeground(l_scoreJ2.getForeground().darker());

        l_scoreJ1 = new JLabel();
        l_scoreJ1.setText(gameView.game.getPlayer(0).getName());
        l_scoreJ1.setHorizontalAlignment(SwingConstants.CENTER);
        l_scoreJ1.setHorizontalTextPosition(SwingConstants.CENTER);
        l_scoreJ1.setSize(logoHeight,(int)(logoHeight*0.5));
        l_scoreJ1.setLocation(p_main.getWidth()*2/3 - (l_scoreJ1.getWidth()), gameView.getY() +gameView.getHeight() + 10);
        l_scoreJ1.setForeground((Color)color.get(game.getPlayer(0).getColor()));

        l_pingouinJ1 = new JLabel();
        l_pingouinJ1.setBounds(l_scoreJ1.getX()-l_scoreJ1.getWidth(),l_scoreJ1.getY() - 50,l_scoreJ1.getWidth(),(int) (p_main.getHeight()*0.2));
        l_pingouinJ1.setIcon(new ImageIcon(avatarJ1));
        l_pingouinJ1.setOpaque(true);
        l_pingouinJ1.setVisible(true);

        l_pingouinJ2 = new JLabel();
        l_pingouinJ2.setBounds(l_scoreJ2.getX()-l_scoreJ2.getWidth(),l_scoreJ2.getY() - 50,l_scoreJ2.getWidth(),(int) (p_main.getHeight()*0.2));
        l_pingouinJ2.setIcon(new ImageIcon(avatarJ2));
        l_pingouinJ2.setOpaque(true);
        l_pingouinJ2.setVisible(true);


        p_main.add(l_title);
        p_main.add(l_feedback);
        p_main.add(gameView);
        p_main.add(b_undo);
        p_main.add(b_redo);
        p_main.add(l_scoreJ1);
        p_main.add(l_scoreJ2);
        p_main.add(l_pingouinJ1);
        p_main.add(l_pingouinJ2);
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
        if (!evt.getPropertyName().equals("status")) gameView.repaint();
        else{
            System.out.println(evt.getOldValue().toString()+" >> "+evt.getNewValue().toString());
            updateFeedback();
        }
    }

    public void resize(){
        p_main.setSize(gra.frame.getSize());
        gameView.setBounds(0, l_title.getHeight()+2*10, p_main.getWidth(), (int) (p_main.getHeight()*0.7));
        //l_title.setLocation((p_main.getWidth()-1500/4)/2,0);
        l_feedback.setBounds(l_title.getWidth()+10, 10, p_main.getWidth()-l_title.getWidth()-2*10, l_title.getHeight());
        l_scoreJ2.setLocation(p_main.getWidth()*2/3 + (l_scoreJ2.getWidth() + (int)(p_main.getWidth()*0.05) ), gameView.getY() +gameView.getHeight() + 10);
        l_scoreJ1.setLocation(p_main.getWidth()*2/3 - (l_scoreJ1.getWidth()), gameView.getY() +gameView.getHeight() + 10);
        b_undo.setLocation(50, gameView.getY() +gameView.getHeight() + 10);
        b_redo.setLocation(b_undo.getLocation().x+b_undo.getWidth()+10, gameView.getY() +gameView.getHeight() + 10);
        l_pingouinJ1.setBounds(l_scoreJ1.getX()-l_scoreJ1.getWidth(),l_scoreJ1.getY() - 50,l_scoreJ1.getWidth(),(int) (p_main.getHeight()*0.2));
        l_pingouinJ2.setBounds(l_scoreJ2.getX()-l_scoreJ2.getWidth(),l_scoreJ2.getY() - 50,l_scoreJ2.getWidth(),(int) (p_main.getHeight()*0.2));

    }

    private void populateStatusString() {
        statusString = new HashMap<Integer, String>();
        statusString.put(
                Game.PENGUIN_PLACED,
                "<p><strong>$1</strong>, vous avez placé votre $2 pingouin !</p><p><strong>$3</strong>, placez votre $4 pingouin.</p>"
        );
        statusString.put(
                Game.ONLY_ONE_FISH,
                "<p>Vous ne pouvez placer votre pingouin que sur les cases ayant un poisson, <strong>$1</strong> !</p>"
        );
        statusString.put(
                Game.ALREADY_OCCUPIED,
                "<p><em>Mince !</em> Un pingouin occupe déjà cette case. <br/>Choisissez-en une autre, <strong>$1</strong> !</p>"
        );
    }

    private void updateFeedback() {
        l_feedback.setText("<html><body>"+statusString.get(game.status)+"</body></html>");
        l_feedback.updateUI();
    }
}
