package controller;

import java.util.Random;

/**
 * Classe d'IA aux coups aléatoires.
 * @author Vincent
 */
public class AIRandom extends Player {

	Random r;
	
	public AIRandom(int nbPenguins, int c, String name) {
		super(nbPenguins, c, name);
		r = new Random();
	}
	
	@Override
	public void play() {
		int tmp, x, y;
		
		/* choix du pingouin à jouer */
		tmp = r.nextInt(2);
		
		/* on teste si le pingouin peut bouger */
		/* si non, on prend l'autre */
		
		/* récupération des cases accessibles pour le pingouin */
		
		/* on choisit une case au hasard parmi les accessibles */
		
		// makeMove(px, py, x, y);
	}
	
}
