package controller.ai;

import controller.Player;
import model.Board;
import model.Game;
import model.Penguin;

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
public class HeurAccess extends Heuristic {
	
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
	 * On regarde tous nos propres pingouins, pour identifier les coups
	 * qui limitent l'accès à des cases 3 pour d'autres pingouins.
	 * On pourrait valuer les coups qui gênent les adversaire et
	 * dévaluer ceux dans lesquels on est gêné (Note : C'est déjà
	 * un peu le cas, car si un ennemi nous bloque, on a accès à moins
	 * de cases à trois poissons).
	 */
	public int heuristicPlace(Game g){
		int maxX, valTile, index, somme = 0;
		int [][] moveList;
		Board b = g.getBoard();
		Player p = g.getCurrentPlayer();
		Penguin[] tabP = p.getPenguins();
		Penguin currP;
		for(int i = 0 ; i < p.getAmountPlaced() ; i++){
			currP = tabP[i];
			moveList = b.movePossibility(currP.getX(), currP.getY());
			
			//On regarde les cases accessibles, et leur valeur.
			index = 0;
			while(moveList[index][0] != -1){
				valTile = b.getTile(moveList[index][0], moveList[index][1]).getFishNumber();
				
				/* On value très fortement les cases à 3 poissons.
				 * Les cases à 2 poissons sont quand même un peu intéressantes.
				 * Les cases à 1 poisson.. C'est mieux que rien.
				 */
				somme += valTile*valTile;
			}
			
			//On vérifie si l'on est sur un bord de carte.
			maxX = Board.LENGTH - 2 + (currP.getY()%2);
			if((currP.getX() == 0) || currP.getX() == maxX){
				somme -= 9;
			}
			
			if((currP.getY() == 0) || (currP.getY() == Board.LENGTH -1)){
				somme -= 9;
			}
			
			/* On regarde les cases voisines, à la recherche d'un
			 * pingouin allié.
			 */
			
			// Flemme
		}
		
		return somme;
	}
}
