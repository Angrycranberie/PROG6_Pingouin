package controller;

import model.Penguin;

import java.awt.Color;
import java.util.Random;

/**
 * Classe d'IA aux coups aléatoires.
 * @author Vincent
 */
public class AIRandom extends Player {

	Random r;
	
	public AIRandom(int penguinsNumber, Color color, String name) {
		super(penguinsNumber, color, name);
		setAI(true);
		r = new Random();
	}

	@Override
	public boolean play() {
		int x,y;
		
		// placement des pingouins
		if(getGame().placePhase()) {
			do {
				/* TODO
				 * Mettre à jour ces deux lignes en prenant les dimensions du tableau ! 
				 */
				x = r.nextInt(8);
				y = r.nextInt(8);
			} while(!getGame().placePenguin(x,y));
		
		} // jouer un coup
		else {
			int tmp, index=0;
			int move[][];
			Penguin tabPen[] = penguins();
			Penguin pen;
			
			/* choix du pingouin à jouer 
			 * si le pingouin n'a pas de coup disponible, on en joue un autre */
			do {
				tmp = r.nextInt(getPenguinsNumber());
				pen = tabPen[tmp];
				move = getGame().legitMovePossibility(pen);
				
				// compte le nombre de coup disponible pour le pingouin
				index = lengthMove(move);
				
			} while(index == 0); 
			
			do {
				/* on choisit une case au hasard parmi les accessibles */
				tmp = r.nextInt(index);
				x = move[tmp][0];
				y = move[tmp][1];
			} while (!getGame().movePenguin(pen.coord_x(), pen.coord_y(), x, y));
		}
		return true;
	}
	
	/**
	 * Retourne le nombre de coup disponible
	 * @param move le tableau de coup disponible
	 * @return le nombre de coup
	 */
	private int lengthMove(int move[][]) {
		int len=0;
		boolean end = false;
		while(!end && len < 60) {
			if(move[len][0] == -1) end = true;
			else len++;
		}
		return len;
	}
}
