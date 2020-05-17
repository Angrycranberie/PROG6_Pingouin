package controller.ai;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import model.Game;
import controller.Player;

/*
 * Classe AISmart
 * 
 * 
 */
public class AISmart extends Player {
	Game game;
	Random r;
	DecisionTree decTree;
	
	/* Crée une IA et son arbre associé.
	 * Le booleen start indique si elle joue en premier (true) ou non (false)
	 */
	public AISmart(int penguinsNumber, Color color, String name) {
		super(penguinsNumber, color, name);
		r = new Random();
	}

	@Override
	public void play(){
		decTree = new DecisionTree(game);
		
		if(game.placePhase()){
			placeTurn();
		} else {
			moveTurn();
		}
	}
	
	private boolean placeTurn(){
		ArrayList<Couple<Integer, Integer>> resList = 
				decTree.placeDecision(-10000, 10000, true, 4).getSecond();
		
		Couple<Integer, Integer> move = resList.get(r.nextInt(resList.size()));
		return game.placePinguin(move.getFirst(), move.getSecond());
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
