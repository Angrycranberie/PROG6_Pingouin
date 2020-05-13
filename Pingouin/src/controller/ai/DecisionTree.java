package controller.ai;

import java.util.Hashtable;

import model.Board;
import model.Game;
import model.History;
import model.Move;
import model.Penguin;

public class DecisionTree {

	private Game game;
	private Board board;
	private History hist;
	
	
	/* Construit l'arbre de decision associé à une IA.
	 * start vaut true si cette IA commence la partie, false sinon.
	 */
 	public DecisionTree(Game g){
		this.game = g.clone();
		board = game.getBoard();
		
	}
 	
	
	/* Construit un noeud de l'arbre associé au plateau courant game, 
	 * et considérant que l'IA a le tour en cours.
	 */
	private int nodeSelf(int alpha, int beta){
		
		int value = -100, x, y, j;
		int moveList[][];
		Penguin [] penguins = game.getCurrentPlayer().penguins();
		
		/*
		if(game.end()){
			elm.ModifWin(!win);
			return root;
		}
		*/
		
		/* On parcours tous les fils possibles de ce noeud et on regarde leur valeur */
		for(int i = 0 ; i < penguins.length ; i++){
			x = penguins[i].coord_x();
			y = penguins[i].coord_y();
			moveList = board.movePossibility(x, y);
			j = 0;
			while(moveList[j][0] != -1){
				if(game.movePenguin(x, y, moveList[j][0], moveList[j][1])) {
					value = Math.max(value, nodeOppo(alpha, beta));
					game.undo(1);	// Non implémenté.
					if(value >= beta){
						return value;	// coupure beta.
					}
					alpha = Math.max(alpha, value);
				}
				j++;
			}
		}	
		return value;
	}
	
	/* Construit un noeud de l'arbre associé au plateau courant game, 
	 * et considérant que l'adversaire a le tour en cours.
	 */
	private int nodeOppo(int alpha, int beta){
		
		int value = 100, x, y, j;
		int moveList[][];
		Penguin [] penguins = game.getCurrentPlayer().penguins();
		
		/*
		if(game.end()){
			elm.ModifWin(!win);
			return root;
		}
		*/
		
		/* On parcours tous les fils possibles de ce noeud et on regarde leur valeur */
		for(int i = 0 ; i < penguins.length ; i++){
			x = penguins[i].coord_x();
			y = penguins[i].coord_y();
			moveList = board.movePossibility(x, y);
			j = 0;
			while(moveList[j][0] != -1){
				if(game.movePenguin(x, y, moveList[j][0], moveList[j][1])) {
					value = Math.min(value, nodeSelf(alpha, beta));
					game.undo(1);	// Non implémenté.
					if(alpha >= value){
						return value;	// coupure alpha.
					}
					beta = Math.min(beta, value);
				}
				j++;
			}
		}	
		return value;
	}
}