import view.GraphicInterface;
import view.TextInterface;

import controller.AIRandom;
import controller.ControllerMediator;
import controller.Player;
import controller.PlayerHuman;
import controller.ai.AIAccess;
import controller.ai.AISomme;
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
		Player p1 = new AIAccess(4, Player.COLOR_CLASSIC, "AIRandom");
		Player p2 = new AIRandom(4, Player.COLOR_CHICK, "AIAccess");
		Game g = new Game(2, p1, p2, null, null);
		
		ControllerMediator controller = new ControllerMediator(g);
		
		TextInterface.start(g, controller);
//		GraphicInterface.start(g, controller);
	}

}
