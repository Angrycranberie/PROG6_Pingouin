package model;

import controller.Player;

/**
 * Classe Move. Correspond à des coups joués par un joueur. Utilisé par History.
 * @author Charly
 *
 */
public class Move implements Cloneable{
	int x1, y1, x2, y2; // Coordonnées de départ et d'arrivée du coup.
	int player; // Numéro du joueur jouant le coup.
	int fishNumber; // Nombre de poissons obtenu suite à ce coup.
	
	/**
	 * Création d'une action de la part du joueur de numéro "player" qui bouge d'une tuile à une autre en obtenant "fishNumber" poissons.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 * @param player Numéro du joueur jouant le coup.
	 * @param nbFish Nombre de poissons obtenu suite à ce coup.
	 */
	public Move(int x1,	int y1, int x2,	int y2,	int player, int nbFish) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.player = player;
		this.fishNumber = nbFish;
	}
	
	/**
	 * Annulation du coup.
	 * @param b Plateau de jeu.
	 * @param p Ensemble des joueurs.
	 */
	public void undo(Board b, Player[] p) {
		p[player-1].movePenguin(x2, y2, x1, y1);
		b.reverseMove(x1,y1,x2,y2, fishNumber);
		p[player-1].changeScore(-fishNumber);
		p[player-1].removeTile();
	}

	/**
	 * Réfection du coup.
	 * @param b Plateau de jeu.
	 * @param p Ensemble des joueurs.
	 */
	public void redo(Board b, Player[] p) {
		if (b.makeMove(x1,y1,x2,y2).getFishNumber() == fishNumber) {
			p[player-1].movePenguin(x1, y1, x2, y2);
			p[player-1].changeScore(fishNumber);
			p[player-1].addTile();
		}
	}
	
	@Override
	protected Move clone() {
		Move m = new Move(x1, y1, x2, y2,player,fishNumber);
		return m;
	}

	public String toString() {
		return x1+" "+y1+" "+x2+" "+y2+" "+player+" "+fishNumber;
	}
}
