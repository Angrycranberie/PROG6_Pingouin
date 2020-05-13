package model;

/**
 * Classe Tests. Vérifie le bon fonctionnement des autres classes & méthodes.
 * @author Charly
 *
 */
public class Tests {

	public static void main(String[] args) {
		Board b = new Board();
		b.shuffle();
		b.test();
		test_History(false);
	}
	
	private static boolean test_History(boolean print) {
		/*boolean result = true;
		
		Player p1 = new Player(new Color(255,0,0),"Jean");
		Player p2 = new Player(new Color(0,255,0),"Jacques");
		Game g = new Game(2,p1,p2,null,null);
		
		
		
		*/
		return false;
	}
}
