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
	int x1, y1, x2, y2;
	private int toPlace;
	
	public ControlerMediator(Game g){
		game = g;
		board = g.getBoard();
		x1 = y1 = x2 = y2 = -1;
		setToPlace(game.getPlayerCount()*(game.getCurrentPlayer().getPenguinsNumber()));
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
	 * Lis une coordonnée entrée au clavier et l'interprête.
	 * @param val Entier lu au clavier.
	 * @return Vrai si le dernier mouvement est correct, Faux sinon.
	 */
	public boolean keyInput(int val){
		boolean ret = false;
		
		if(val < 0){
			System.out.println("Coordonnée négative. Erreur.");
		} else {
			if(x1 < 0)x1 = val;
			else if (y1 < 0){
				y1 = val;
				if(getToPlace() > 0){
					if(ret = placePinguin(x1, y1)) setToPlace(getToPlace() - 1);
					x1 = y1 = -1;
				}
			} else if (x2 < 0) x2 = val;
			else {
				y2 = val;
				ret = move(x1, y1, x2, y2);
				x1 = y1 = x2 = y2 = -1;
			}
			
		}
		return ret;
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
	
	/**
	 * Determine si le joueur courant peut encore jouer.
	 * Si oui, alors il joue un tour.
	 * Si non, on retire ses pingouins de la partie et on l'empêche de jouer à nouveau.
	 * @return Vrai si le joueur peut encore jouer, Faux sinon.
	 */
	private boolean stillPlaying(){
		Player currPlayer = game.getCurrentPlayer();
		if(game.canPlay(currPlayer)){
			game.endPlayer(currPlayer);
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Commence le tour du joueur courant : vérifie s'il peut encore jouer.
	 * Si non, lui fait finir la partie, puis vérifie qu'il reste des joueurs.
	 * @return Vrai s'il reste des joueurs, Faux sinon.
	 */
	public boolean startTurn(){
		if(!stillPlaying()){
			if(!game.nextPlayer()){
				return false;
			}
		}
		return true;
	}

	public int getToPlace() {
		return toPlace;
	}

	public void setToPlace(int toPlace) {
		this.toPlace = toPlace;
	}
}
