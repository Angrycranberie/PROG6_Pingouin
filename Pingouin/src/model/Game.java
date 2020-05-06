package model;

import controller.Player;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Classe Game. Gère une partie du jeu : ordre des tours, coups sur le plateau.
 * @author Charly
 */
public class Game {
	private Player[] players;
	private int playerCount;
	private int currentPlayerNumber;
	private Board board;
	private PropertyChangeSupport support;
	
	/**
	 * initialise le jeu
	 * @param nbPlayer nombre de joueur
	 * @param p1 joueur 1
	 * @param p2 joueur 2
	 * @param p3 joueur 3
	 * @param p4 joueur 4
	 */
	public Game(int playerCount, Player p1, Player p2, Player p3, Player p4) {
		this.playerCount = playerCount;
		players = new Player[playerCount];
		if(playerCount >= 1) players[0] = p1;
		if(playerCount >= 2) players[1] = p2;
		if(playerCount >= 3) players[2] = p3;
		if(playerCount >= 4) players[3] = p4;
		currentPlayerNumber = 1;
		board = new Board();
		support = new PropertyChangeSupport(this);
	}

	/**
	 * Ajoute un observateur des changements du jeu.
	 * @param pcl Observateur à ajouter.
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {	support.addPropertyChangeListener(pcl);	}

	/**
	 * Retire un observateur des changements du jeu.
	 * @param pcl Observateur à retirer.
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) { support.removePropertyChangeListener(pcl); }
	
	/**
	 * retourne le numéro du joueur courant
	 * @return numéro joueur courant
	 */
	public int currentPlayerNumber() {
		return currentPlayerNumber;
	}
	
	/**
	 * effectue le mouvement du pingouin de la tuile 1 vers la 2
	 * @param x1 coordonnée x de la tuile 1
	 * @param y1 coordonnée y de la tuile 1
	 * @param x2 coordonnée x de la tuile 2
	 * @param y2 coordonnée y de la tuile 2
	 * @return indique si le mouvement s'est fait ou non
	 */
	public boolean movePenguin(int x1, int y1, int x2, int y2) {
		Player p = getCurrentPlayer();
		if (hasPenguinGoodOwning(p,x1,y1)) {
			Game oldGame = this;
			Tile t = board.makeMove(x1, y1, x2, y2);
			if (t != null) {
				p.changeScore(t.getFishNumber());
				p.addTile();
				p.movePenguin(x1, y1, x2, y2);
				support.firePropertyChange("game", oldGame, this);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * indique si le pingouin situé sur la tuile donnée est bien le pingouin du joueur courant
	 * @param x1 coordonnée x de la tuile
	 * @param y1 coordonnée y de la tuile
	 * @return true le pingouin appartient au joueur courant false sinon
	 */
	public boolean hasPenguinGoodOwning(Player p, int x1, int y1) {
		Penguin penguins[] = p.penguins();
		int nbPenguins = p.getPenguinsNumber();
		for(int i = 0; i < nbPenguins ; i++) {
			if(penguins[i].coord_x() == x1 && penguins[i].coord_y() == y1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * retourne le joueur courant
	 * @return joueur courant
	 */
	public Player getCurrentPlayer() { return players[currentPlayerNumber - 1]; }
	
	/**
	 * détermine le prochain joueur
	 * @return indique si il y a ou non un prochain joueur
	 */
	public boolean nextPlayer() {
		for(int i = 1 ; i <= playerCount ; i++) {
			int loopPlayerNumber = (currentPlayerNumber - 1 + i) % playerCount;
			if (players[loopPlayerNumber].isPlaying()) {
				support.firePropertyChange("currentPlayerNumber", currentPlayerNumber, loopPlayerNumber+1);
				currentPlayerNumber = loopPlayerNumber + 1;
				return true;
			}
		}
		return false;
	}
	
	public int[][] legitMovePossibility(Penguin p){
		return gameBoard.movePossibility(p.coord_x(),p.coord_y());
	}
	
	public boolean canPlay(Player p) {
		boolean possibility = false;
		int movePossibility[][];
		Penguin penguins[] = p.penguins();
		for(int i = 0 ; i < p.getPenguinsNumber() ; i++) {
			movePossibility = legitMovePossibility(penguins[i]);
			possibility = possibility || movePossibility[0][0] == -1;
		}
		return possibility;
	}
}
