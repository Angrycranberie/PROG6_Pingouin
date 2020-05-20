package controller.ai;

import model.Game;
import model.Penguin;
import controller.Player;

/**
 * Classe d'heuristique basique. L'heuristique d'un joueur est :
 * (Somme des poissons sous des pingouins du joueur) - (Somme
 *  des poissons sous des pingouins des adversaires).
 * Ainsi, le placement des pingouins est aléatoire et les déplacements
 * ciblent les cases ayant trois poissons.
 * Cette heuristique naïve est surtout là pour effectuer des tests.
 * @author yeauhant
 */
public class HeurSomme extends Heuristic{

	@Override
	public int heuristicMove(Game g){
		int somme = 0;
		int curr;
		
		Player selfPlayer = g.getCurrentPlayer();
		
		for(Player currPlayer : g.getPlayers()){
			if(currPlayer != null) {
				for(Penguin selec : currPlayer.getPenguins()){
					if(selec != null){
						if(currPlayer.isPlaying()){
					
						curr = g.getBoard().getTile(selec.getX(), selec.getY()).getFishNumber();
						if(currPlayer == selfPlayer) somme += curr;
						else somme -= curr;
						}
					}
				}
			}
		}
		
		return somme;
	}
	
	@Override
	public int heuristicPlace(Game g){
		return heuristicMove(g);
	}
}
