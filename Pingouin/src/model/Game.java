package model;

import controller.Player;

/**
 * Classe Game. Gère une partie du jeu : ordre des tours, coups sur le plateau.
 * @author Charly
 */
public class Game {
	private Player[] players;
	private int playerCount;
	private Board gameBoard;
	private int currentPlayerNumber;
	
	/**
	 * Initialisation du jeu.
	 * @param playerCount Nombre de joueurs effectif.
	 * @param p1 Joueur n°1.
	 * @param p2 Joueur n°2.
	 * @param p3 Joueur n°3.
	 * @param p4 Joueur n°4.
	 */
	public Game(int playerCount, Player p1, Player p2, Player p3, Player p4) {
		currentPlayerNumber = 1;
		this.playerCount = playerCount;
		players = new Player[playerCount];
		if(playerCount >= 1) players[0] = p1;
		if(playerCount >= 2) players[1] = p2;
		if(playerCount >= 3) players[2] = p3;
		if(playerCount >= 4) players[3] = p4;
		gameBoard = new Board();
	}
	
	/**
	 * Retourne le numéro du joueur courant.
	 * @return Numéro du joueur courant.
	 */
	public int getCurrentPlayerNumber() { return currentPlayerNumber; }
	
	/**
	 * Renvoie le plateau associé au jeu.
	 * @return Plateau de jeu.
	 */
	public Board getBoard(){
		return gameBoard;
	}
	/**
	 * Effectue le mouvement d'un pingouin d'une tuile à une autre.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 * @return Vrai (true) si le mouvement a été fait ; faux (false) sinon.
	 */
	public boolean movePenguin(int x1, int y1, int x2, int y2) {
		Player p = getCurrentPlayer();
		if (hasPenguinGoodOwning(p,x1,y1)) {
			Tile t = gameBoard.makeMove(x1, y1, x2, y2);
			if (t != null) {
				p.changeScore(t.getFishNumber());
				p.addTile();
				p.movePenguin(x1, y1, x2, y2);
				return true;
			}
		}
		return false;
	}

	/**
	 * Indique si le pingouin situé sur la tuile donnée est bien le pingouin du joueur courant.
	 * @param x1 Coordonnée x de la tuile où se situe le pingouin.
	 * @param y1 Coordonnée y de la tuile où se situe le pingouin.
	 * @return Vrai (true) si le pingouin appartient au joueur courant ; faux (false) sinon.
	 */
	private boolean hasPenguinGoodOwning(Player p, int x1, int y1) {
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
	 * Retourne directement le joueur courant (et non son numéro).
	 * @return Joueur courant.
	 */
	public Player getCurrentPlayer() { return players[currentPlayerNumber - 1]; }
	
	/**
	 * Détermine quel est le prochain joueur.
	 * Par effet de bord, on incrémente le numéro du joueur courant.
	 * @return Vrai (true) s'il y a bien un prochain joueur ; faux (false) sinon.
	 */
	public boolean nextPlayer() {
		for (int i = 1; i <= 4; i++) {
			if (players[(currentPlayerNumber - 1 + i) % playerCount].isPlaying()) {
				currentPlayerNumber = (currentPlayerNumber - 1 + i) % playerCount + 1;
				return true;
			}
		}
		return false;
	}
	
	public int[][] legitMovePossibility(Penguin p){
		return gameBoard.movePossibility(p.coord_x(),p.coord_y());
	}
}
