package controller;


/**
 * Classe de joueur humain. Joue un coup à partir d'un clic.
 * @author Vincent
 */
public class PlayerHuman extends Player {

	public PlayerHuman(int penguinsNumber, int color, String name) {
		super(penguinsNumber, color, name);
		setAI(0);
	}
	
	@Override
	public boolean positionPenguin(int x, int y) {
		return getGame().placePenguin(x,y);
	}
	
	@Override
	public boolean play(int x1, int y1, int x2, int y2) {
		return getGame().movePenguin(x1, y1, x2, y2);
	}
}
