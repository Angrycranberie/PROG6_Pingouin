package controller.ai;

import java.util.ArrayList;

import model.Board;
import model.Game;
import model.Penguin;

/**
 * 
 * @author yeauhant
 *
 */
public class DecisionTree {

	private Game game;
	private Board board;
	private Heuristic heur;
	
	/**
	 * Associe une partie à l'arbre de décision.
	 * Cette méthode doit être appelée avant chaque nouveau coup de l'IA.
	 * @param g Partie à associer.
	 */
 	public DecisionTree(Game g, Heuristic h){
 		this.game = g.clone();
		board = game.getBoard();
		this.heur = h;
	}
 	
 	/**
 	 * Calcule la liste des meilleurs coups à jouer. Utilise l'heuristique définie.
 	 * Cette methode est recursive.
 	 * @param alpha Valeur de coupure Alpha. Dans un noeud Min, si l'heuristique
 	 * 	d'un fils lui est inférieure, on arrête le calcul. Est mise à jour par
 	 * 	les noeuds Max : Vaut le maximum d'un des fils déjà calculés.
 	 * 	Propagation descendante. A initiliaser à une très petite valeur.
 	 * @param beta Valeur de coupure Beta. Dans un noeud Max, si l'heuristique
 	 * 	d'un fils lui est supérieure, on arrête le calcul. Est mise à jour par
 	 * 	les noeuds Min : Vaut le minimum d'un des fils déjà calculé.
 	 * 	Propagation descendante. A initiliaser à une très grande valeur.
 	 * @param ownTurn Indique si le noeud est Max (Vrai) ou Min (Faux).
 	 * @param depth	Profondeur restante à explorer. Lorsqu'elle vaut zéro,
 	 * 	on évalue et renvoie l'heuristique associée au plateau courant.
 	 * @return Couple (Heuristique du meilleur coup ; liste des meilleurs coups).
 	 */
 	public Couple<Integer, ArrayList<Couple<Couple<Integer, Integer>, Couple<Integer, Integer>>>>
 	moveDecision(int alpha, int beta, boolean ownTurn, int depth){
 		ArrayList<Couple<Couple<Integer, Integer>, Couple<Integer, Integer>>> resMove = 
 				new ArrayList<Couple<Couple<Integer, Integer>, Couple<Integer, Integer>>>();
 		
		int value, x, y, j, tmp;
		int moveList[][];
		Penguin [] penguins = game.getCurrentPlayer().getPenguins();
		
		if(!game.canPlay(game.getCurrentPlayer()) || (depth == 0)){
			game.prevPlayer();
			Couple<Integer, ArrayList<Couple<Couple<Integer, Integer>, Couple<Integer, Integer>>>>res = new Couple<>(heur.heuristicMove(game), resMove);
			game.nextPlayer();
			return res;
		}
		
		/* Initialisation de l'heuristique calculée */
		if(ownTurn) value = -100000;
		else value = 100000;
		
		/* On parcours tous les fils possibles de ce noeud et on regarde leur valeur */
		for(int i = 0 ; i < penguins.length ; i++){
			x = penguins[i].getX();
			y = penguins[i].getY();
			moveList = board.movePossibility(x, y);
			j = 0;
			while(moveList[j][0] != -1){
				if(game.movePenguin(x, y, moveList[j][0], moveList[j][1])) {
					game.nextPlayer();
					if(ownTurn) {
						tmp = moveDecision(alpha, beta, false, depth-1).getFirst();
						if(tmp >= value){
							/* Le coup est acceptable. On l'ajoute à la liste de coups.
							 * Si la nouvelle heuristique est meilleure, on vide d'abord la liste.
							 */
							if (tmp != value) resMove.clear();
							
							resMove.add(new Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> 
								(new Couple<Integer, Integer> (x, y),
								 new Couple<Integer, Integer> (moveList[j][0], moveList[j][1])));
							value = tmp;
						} /* Sinon : le coup est moins bon. On ne change pas l'heuristique
						   * ni la liste de coups mémorisés.
						   */
						
						game.undo(1);
						game.prevPlayer();
						if(value >= beta){
							return new Couple<>(value, resMove);	// coupure beta.
						}
						alpha = Math.max(alpha, value);
						
					} else {						
						tmp = moveDecision(alpha, beta, false, depth-1).getFirst();
						if(tmp <= value){
							/* Le coup est acceptable. On l'ajoute à la liste de coups.
							 * Si la nouvelle heuristique est meilleure, on vide d'abord la liste.
							 */
							if (tmp != value) resMove.clear();
							
							resMove.add(new Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> 
								(new Couple<Integer, Integer> (x, y),
								 new Couple<Integer, Integer> (moveList[j][0], moveList[j][1])));
							value = tmp;
						}
						
						game.undo(1);
						game.prevPlayer();
						if(alpha >= value){
							return new Couple<>(value, resMove);	// coupure alpha.
						}
						beta = Math.min(beta, value);
					}
					
				}
				j++;
			}
		}
		return new Couple<>(value, resMove);
 	}
	
 	/* Cette partie nécessite une factorisation,
 	 * mais les conditions variantes (nature d'un Coup, ownTurn ou non etc..)
 	 * rendent la chose pas si simple.
 	 */
 	
 	/**
 	 * Calcule la liste des meilleurs placements à jouer. Utilise l'heuristique définie.
 	 * Cette methode est recursive.
 	 * @param alpha Valeur de coupure Alpha. Dans un noeud Min, si l'heuristique
 	 * 	d'un fils lui est inférieure, on arrête le calcul. Est mise à jour par
 	 * 	les noeuds Max : Vaut le maximum d'un des fils déjà calculés.
 	 * 	Propagation descendante. A initiliaser à une très petite valeur.
 	 * @param beta Valeur de coupure Beta. Dans un noeud Max, si l'heuristique
 	 * 	d'un fils lui est supérieure, on arrête le calcul. Est mise à jour par
 	 * 	les noeuds Min : Vaut le minimum d'un des fils déjà calculé.
 	 * 	Propagation descendante. A initiliaser à une très grande valeur.
 	 * @param ownTurn Indique si le noeud est Max (Vrai) ou Min (Faux).
 	 * @param depth	Profondeur restante à explorer. Lorsqu'elle vaut zéro,
 	 * 	on évalue et renvoie l'heuristique associée au plateau courant.
 	 * @return Couple (Heuristique du meilleur coup ; liste des meilleurs coups).
 	 */
 	public Couple<Integer, ArrayList<Couple<Integer, Integer>>>
 		placeDecision(int alpha, int beta, boolean ownTurn, int depth){
 		
 		ArrayList<Couple<Integer, Integer>> resList = new ArrayList<Couple<Integer, Integer>>();
		int value, tmp;
		ArrayList<Couple<Integer, Integer>> moveList;
		
		if(!game.canPlay(game.getCurrentPlayer()) || (depth == 0)){
			game.prevPlayer();
			Couple<Integer, ArrayList<Couple<Integer, Integer>>>res = new Couple<>(heur.heuristicPlace(game), resList);
			game.nextPlayer();		
			return res;
		}
		
		if(ownTurn) value = -100000;
		else value = 100000;
		
		moveList = board.placePossiblity();
		for(Couple<Integer, Integer> curr : moveList){
			if(game.placePenguin(curr.getFirst(), curr.getSecond())) {
				if(ownTurn) {
					if(game.movePhase()){
						tmp = moveDecision(alpha, beta, false, depth-1).getFirst();
					} else {
						tmp = placeDecision(alpha, beta, false, depth-1).getFirst();
					}
					
					// val = max(tmp, val);
					if(tmp >= value){
						/* Le coup est acceptable. On l'ajoute à la liste de coups.
						 * Si la nouvelle heuristique est meilleure, on vide d'abord la liste.
						 */
						if (tmp != value) resList.clear();
						
						resList.add(curr.clone());
						value = tmp;
					} /* Sinon : le coup est moins bon. On ne change pas l'heuristique
					   * ni la liste de coups mémorisés.
					   */
					
					game.cancelPlace();
					if(value >= beta){
						return new Couple<>(value, resList);	// coupure beta.
					}
					alpha = Math.max(alpha, value);
						
				} else {
					/* Ne devrait pas vraiment être accédé.
					 * La valuation des coups de placement se fait avec
					 * une profondeur de 1.
					 */
					if(game.movePhase()){
						tmp = moveDecision(alpha, beta, true, depth-1).getFirst();
					} else {
						tmp = placeDecision(alpha, beta, true, depth-1).getFirst();
					}
					
					// value = min(tmp, value)
					if(tmp <= value){
						/* Le coup est acceptable. On l'ajoute à la liste de coups.
						 * Si la nouvelle heuristique est meilleure, on vide d'abord la liste.
						 */
						if (tmp != value) resList.clear();
						
						resList.add(curr.clone());
						value = tmp;
					} /* Sinon : le coup est moins bon. On ne change pas l'heuristique
					   * ni la liste de coups mémorisés.
					   */
					
					game.cancelPlace();
					if(alpha >= value){
						return new Couple<>(value, resList);	// coupure alpha.
					}
					beta = Math.min(beta, value);
				}
				
			}
		}
 		
 		return new Couple<>(value, resList);
 	}
	
}