import java.awt.Color;

import view.GraphicInterface;

import controller.AIRandom;
import controller.ControllerMediator;
import controller.Player;
import controller.PlayerHuman;
import controller.ai.AISmart;
import controller.ai.HeurSomme;
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
		Player p1 = new AISmart(4, new Color(240, 46, 0), "Joueur 1", new HeurSomme());
		Player p2 = new AIRandom(4, new Color(46, 240, 0), "Joueur 2");
		Game g = new Game(2, p1, p2, null, null);
		
		ControllerMediator controller = new ControllerMediator(g);
		
		TextInterface.start(g, controller);
//		GraphicInterface.start(g, controller);
	}

}
