package controller.ai;

import model.Game;

/**
 * Classe d'heuristique qui favorise les positions ayant accès à
 * des cases valant 3 points. Value positivement les parties pour lesquelles
 * le joueur a plus de points que l'adversaire. Détecte les fins de 
 * partie et renvoie une heuristique correspondante (Grand si victoire,
 * Petit si défaite).
 * Intègre aussi quelques éléments de valuation lors du placement : 
 * Les bords sont dépréciés ; les coins sont très dépréciés ; être
 * proche d'un allié est déprécié.
 * @author yeauhant
 *
 */
public class HeurAcces extends Heuristic {
	
	@Override
	/**
	 * Calcule l'heuristique d'un coup de déplacement. Cf spécification
	 * de la classe.
	 */
	public int heuristicMove(Game g){
		int somme = 0;
		
		return somme;
	}

	@Override
	/**
	 * Calcule l'heuristique d'un coup de placement.
	 * On ne prend pas en compte les adversaires dans cette phase.
	 * On pourrait valuer les coups qui gênent les adversaire et
	 * dévaluer ceux dans lesquels on est gêner (Note : C'est déjà
	 * un peu le cas, car si un ennemi nous bloque, on a accès à moins
	 * de cases à trois poissons).
	 */
	public int heuristicPlace(Game g){
		int somme = 0;
		

		
		return somme;
	}
}
