package view;

import model.Game;

import javax.swing.*;
import java.awt.*;


/**
 * Vue effective du jeu en cours.
 * @author Alexis
 * @author Mathias
 */
public class GameView extends GraphicGame {
    Game game; // Jeu à afficher.
    JPanel panelMain;

    Image tile = loadImage("../../res/img/tiles/Tile0.png");

    /**
     * Constructeur de l'affichage du jeu.
     * @param g Jeu à afficher.
     */
    GameView(Game g) {
        game = g;
        panelMain = new JPanel();
        panelMain.setLayout(new GroupLayout(panelMain));
        g.addPropertyChangeListener(this);
        setBackground(new Color(127, 127, 255));
        setVisible(true);
        generateBoard();
    }

    @Override
    void generateBoard() {
        for(int i = 1; i <9; i++){
            for(int j = 0; j < (i%2==0?8:9); j++) {
                JButton b = new JButton();
                b.setName("" + i + "," + j);
                b.setText(b.getName());
                b.setSize(100,50);
                if(i%2 == 0){
                    b.setLocation(50 + 100*j,50*i);
                    
                }else{
                    b.setLocation( 100*j,50*i);
                }
                panelMain.add(b);


            }
        }
    }

}
