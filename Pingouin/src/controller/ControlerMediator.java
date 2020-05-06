package controller;

import model.Board;
import model.Game;
import model.Penguin;
import model.Tile;
import view.EventCollector;
import view.UserInterface;

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
	public void addUI(UserInterface ui) {

	}

	@Override
	public void timedAction() {
		
	}
	
	/**
	 * Place un pingouin du joueur courant aux coordonnées d'entrée.
	 * Passe au tour suivant si réussi.
	 * @return Vrai si le pingouin a bien été placé, faux sinon.
	 * @param x Coordonnée x où l'on souhaite placer le pingouin.
	 * @param y Coorfonnée y où l'on souhaite placer le pingouin.
	 */
	/* à déplacer dans Game ?*/
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
	
	/**
	 * Effectue le mouvement d'un pingouin d'une tuile à une autre.
	 * Si réussi, passe au joueur suivant.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 * @return Vrai (true) si le mouvement a été fait ; faux (false) sinon.
	 */
	public boolean move(int x1, int y1, int x2, int y2){
		if(game.movePenguin(x1, y1, x2, y2)){
			game.nextPlayer();
			return true;
		}
		return false;
	}
}
