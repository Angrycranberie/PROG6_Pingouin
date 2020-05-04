package Modèle;

public class Move {
	int x1;
	int y1;
	int x2;
	int y2;
	int player;
	int nbFish;
	
	/**
	 * création d'une action de la part du joueur n°player qui bouge de la tuile 1 vers la 2 en obtenant nbFish
	 * @param x1 coordonnée x de la tuile 1
	 * @param y1 coordonnée y de la tuile 1
	 * @param x2 coordonnée x de la tuile 2
	 * @param y2 coordonnée y de la tuile 2
	 * @param player numéro du joueur
	 * @param nbFish nombre de poisson obtenu suite à ce coup
	 */
	public Move(int x1,	int y1, int x2,	int y2,	int player, int nbFish) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.player = player;
		this.nbFish = nbFish;
	}
	
	/**
	 * annulation du coup
	 * @param gameBoard plateau de jeu
	 * @param p ensemble des joueurs
	 */
	public void reverseMove(Board gameBoard,Player p[]) {
		p[player-1].movePenguin(x2, y2, x1, y1);
		gameBoard.reverseMove(x1,y1,x2,y2,nbFish);
		p[player-1].changeScore(-nbFish);
		p[player-1].removeTile();
	}
	
	public void remakeMove(Board gameBoard,Player p[]) {
		if(gameBoard.makeMove(x1,y1,x2,y2).value() == nbFish) {
			p[player-1].movePenguin(x1, y1, x2, y2);
			p[player-1].changeScore(nbFish);
			p[player-1].addTile();
		}
		
	}
	
	
}
