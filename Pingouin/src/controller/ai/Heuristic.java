package controller.ai;

import model.Game;

/**
 * Classe générale d'heuristique. Ne doit pas être instanciée.
 * @author yeauhant
 */
public abstract class Heuristic {

	/* Problème ? Le joueur courant change au mauvais moment dans le
	 * parcours de l'arbre ?
	 */
	
	/**
	 * Calcule l'heuristique assoicée à la partie et au joueur courant
	 * après un déplacement de pingouin.
	 * @param g La partie en cours.
	 * @return L'heuristique calculée.
	 */
	public int heuristicMove(Game g){
		System.err.println("Erreur : Heuristique indéfinie.");
		return 0;
	}
	
	
	/**
	 * Calcule l'heuristique associée à la partie et au joueur courant
	 * après un placement de pingouin.
	 * @param g La partie en cours.
	 * @return L'heuristique calculée.
	 */
	public int heuristicPlace(Game g){
		System.err.println("Erreur : Heuristique indéfinie.");
		return 0;
	}


}
