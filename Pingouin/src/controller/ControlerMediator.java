package controller;

import model.Board;
import model.Game;
import model.Penguin;
import model.Tile;
import view.EventCollector;

/**
 * Classe de contrôleur principal. Interprète les clics et frappes au clavier.
 * Appelle les methodes qui executent les actions.
 * @author Vincent
 * @author Yoann
 */
public class ControlerMediator implements EventCollector {

	Game game;
	Board board;
	
	public ControlerMediator(Game g){
		game = g;
		board = g.getBoard();
	}
	
	@Override
	public void mouseClick(int l, int c) {
		
	}

	@Override
	public void tictac() {
		
	}
	
	/**
	 * Place un pingouin du joueur courant aux coordonnées d'entrée.
	 * @return Vrai si le pingouin a bien été placé, faux sinon.
	 * @param x Coordonnée x où l'on souhaite placer le pingouin.
	 * @param y Coorfonnée y où l'on souhaite placer le pingouin.
	 */
	public boolean placePinguin(int x, int y){
		boolean val = false;
		Player p = game.getCurrentPlayer();
		if(p.getAmountPlaced() < p.getPenguinsNumber()){
			Tile t = board.getTile(x, y);
			if(!t.occupied()){
				p.penguins()[p.getAmountPlaced()] = new Penguin(x, y);
				board.occupyWithPenguin(x, y);
				p.addAmount(1);
				val = true;
				game.nextPlayer();
			} else {
				System.out.println("La case est occupée.");
			}
		} else {
			System.out.println("Tous les pingouins sont déjà placés pour ce joueur.");
			game.nextPlayer();
		}
		return val;
	}
	
	
}
