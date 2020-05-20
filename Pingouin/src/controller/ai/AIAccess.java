package controller.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import controller.Player;

import model.Game;
import controller.ai.HeurAccess;

public class AIAccess extends Player{

	Game game;
	Random r;
	DecisionTree decTree;
	Heuristic heur;
	
	/* Crée une IA et son arbre associé.
	 * Le booleen start indique si elle joue en premier (true) ou non (false)
	 */
	public AIAccess(int penguinsNumber, Color color, String name) {
		super(penguinsNumber, color, name);
		setAI(2);
		r = new Random();
		heur = new HeurAccess();
	}

	public void setGame(Game g){
		game = g;
	}
	
	@Override
	public boolean play(){
		decTree = new DecisionTree(game, heur);
		
		if(game.placePhase()){
			return placeTurn();
		} else {
			return moveTurn();
		}
	}
	
	private boolean placeTurn(){
		ArrayList<Couple<Integer, Integer>> resList = 
				decTree.placeDecision(-10000, 10000, true, 1).getSecond();
		
		Couple<Integer, Integer> move = resList.get(r.nextInt(resList.size()));
		return game.placePenguin(move.getFirst(), move.getSecond());
	}
	
	private boolean moveTurn(){
		
		ArrayList<Couple<Couple<Integer, Integer>, Couple<Integer, Integer>>> resList =
				decTree.moveDecision(-10000, 10000, true, 4).getSecond();
		
		Couple<Couple<Integer, Integer>, Couple<Integer, Integer>> move
			= resList.get(r.nextInt(resList.size()));
		
		Couple<Integer, Integer> from = move.getFirst();
		Couple<Integer, Integer> target = move.getSecond();
		
		return game.movePenguin(from.getFirst(), from.getSecond(), target.getFirst(), target.getSecond());
		
	}	
		
}
