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
 * 
 * Adaptée pour 2 joueurs uniquement. L'adaptation à n joueurs
 * se fait par comparaison au plus grand score parmis les
 * adversaires.
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
		int somme = 0, score;
		boolean canPlaySelf = true, canPlayOppo = false;
		Player[] playList = g.getPlayers();
		Player currPlayer;
		Penguin currPeng;
		Board b = g.getBoard();
		/*
		 * Point de départ : différence de score entre soi et l'adversaire.
		 * On compte le score, + les poissons sous les pingouins.
		 */
		for(int i = 0 ; i < g.getPlayerCount() ; i++){
			currPlayer = playList[i];
			score = currPlayer.getFishScore();
			for(int j = 0 ; j < currPlayer.getAmountPlaced() ; j++){
				if(currPlayer.isPlaying()){
					currPeng = currPlayer.getPenguin(j);
					score += b.getTile(currPeng.getX(), currPeng.getY()).getFishNumber();
				}
			}
			if(currPlayer == g.getCurrentPlayer()){
				somme += score;
				canPlaySelf = g.canPlay(currPlayer);
			}
			else{
				somme -= score;
				canPlayOppo = canPlayOppo || g.canPlay(currPlayer);
			}
		}
		
		/* Est-ce que la partie est finie/Qui gagne ? */
		if(!canPlaySelf){
			/* On a perdu */
			if(somme <= 0) return -1000;
			else {
				/* On a gagné */
				if(!canPlayOppo) return 1000;
				/* Si on ne peut plus jouer, que l'adversaire
				 * peut jouer, et qu'on a + de points que lui,
				 * c'est dur de savoir qui gagne.
				 */
			}
		}
		
		if(!canPlayOppo){
			/* On a gagné, peu importe les prochains coups. */
			if(somme > 0) return 1000;
		}
		
		
		/* 
		 * Valuation de la configuration. On réutilise la valuation
		 * de la phase de placement.
		 */
		
		somme += heuristicPlace(g);
		
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
				index++;
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