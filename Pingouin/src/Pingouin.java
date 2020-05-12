import java.awt.Color;

import view.EventCollector;
import view.TextInterface;

import controller.AIRandom;
import controller.ControlerMediator;
import controller.Player;
import model.Game;


/**
 * Classe principale. Lance le jeu en interface textuelle, humain contre humain.
 * @author Yoann
 *
 */
public class Pingouin {

	/**
	 * @param args Aucun argument n'est attendu.
	 */
	public static void main(String[] args) {
		Player p1 = new Player(4, new Color(240, 46, 0), "Joueur 1");
		Player p2 = new AIRandom(4, new Color(46, 240, 0), "Joueur 2");
		Game g = new Game(2, p1, p2, null, null);
		
		ControlerMediator controler = new ControlerMediator(g);
		
		TextInterface.start(g, controler);
		
		
		
	}

}
