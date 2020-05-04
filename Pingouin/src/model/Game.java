package model;

public class Game {
	private Player p[];
	private int nbPlayer;
	private Board gameBoard;
	private int currentPlayerNumber;
	
	/**
	 * initialise le jeu
	 * @param nbPlayer nombre de joueur
	 * @param p1 joueur 1
	 * @param p2 joueur 2
	 * @param p3 joueur 3
	 * @param p4 joueur 4
	 */
	public Game(int nbPlayer,Player p1,Player p2,Player p3,Player p4) {
		currentPlayerNumber = 1;
		this.nbPlayer = nbPlayer;
		p = new Player[nbPlayer];
		if(nbPlayer >= 1) {
			p[0] = p1;
		}
		if(nbPlayer >= 2) {
			p[1] = p2;
		}
		if(nbPlayer >= 3) {
			p[2] = p3;
		}
		if(nbPlayer >= 4) {
			p[3] = p4;
		}
		gameBoard = new Board();
	}
	
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
	public boolean makeMove(int x1, int y1, int x2, int y2) {
		Player p = currentPlayer();
		if(goodPenguin(p,x1,y1)) {
			Tile t = gameBoard.makeMove(x1, y1, x2, y2);
			if(t != null) {
				p.changeScore(t.value());
				p.addTile();
				p.movePenguin(x1, y1, x2, y2);
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
	private boolean goodPenguin(Player p,int x1, int y1) {
		int penguins[][] = p.penguins();
		int nbPenguins = p.nbPenguins();
		for(int i = 0; i < nbPenguins ; i++) {
			if(penguins[i][0] == x1 && penguins[i][1] == y1) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * retourne le joueur courant
	 * @return joueur courant
	 */
	private Player currentPlayer() {
		return p[currentPlayerNumber - 1];
	}
	
	/**
	 * détermine le prochain joueur
	 * @return indique si il y a ou non un prochain joueur
	 */
	public boolean nextPlayer() {
		for(int i = 1 ; i <= 4 ; i++) {
			if(p[(currentPlayerNumber - 1 + i) % nbPlayer].playing()) {
				currentPlayerNumber = (currentPlayerNumber - 1 + i) % nbPlayer + 1;
				return true;
			}
		}
		return false;
	}
	
	
}
