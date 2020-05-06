package controller;

import model.Game;
import model.Penguin;

import java.awt.Color;
import java.util.Random;

/**
 * Classe d'IA aux coups aléatoires.
 * @author Vincent
 */
public class AIRandom extends Player {

	Random r;
	
	public AIRandom(Game game, int penguinsNumber, Color color, String name) {
		super(game, penguinsNumber, color, name);
		r = new Random();
	}

	@Override
	public void play() {
		int tmp, x, y, index=0;
		int move[][];
		Penguin tabPen[] = penguins();
		Penguin pen;
		
		/* choix du pingouin à jouer */
		do {
			tmp = r.nextInt(getPenguinsNumber());
			pen = tabPen[tmp];
			move = getGame().legitMovePossibility(pen);
			
			index = lengthMove(move);
			
		} while(index == 0); 
		
		
		do {
			/* on choisit une case au hasard parmi les accessibles */
			tmp = r.nextInt(index);
			x = move[tmp][0];
			y = move[tmp][1];
		} while (!getGame().makeMove(pen.coord_x(), pen.coord_y(), x, y));
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
