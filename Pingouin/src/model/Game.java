package model;

import controller.Player;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Classe Game. Gère une partie du jeu : ordre des tours, coups sur le plateau.
 * @author Charly
 */
public class Game implements Cloneable{
	private Player[] players;
	private int playerCount;
	private int currentPlayerNumber;
	private Board board;
	private PropertyChangeSupport supportCount;
	private History history;
	private int toPlace;
	
	public static final int GOOD_TRAVEL = 0;
	public static final int PENGUIN_IN_TRAVEL = 1;
	public static final int HOLE_IN_TRAVEL = 2;
	public static final int TRAVEL_NOT_ALIGNED = 3;
	
	public static final int GOOD_PENGUIN = 4;
	public static final int WRONG_PENGUIN = 5;
	
	public static final int GOOD_PLACE = 0;
	public static final int ONLY_ONE_FISH = 1;
	public static final int ALREADY_OCCUPY = 2;
	
	public int error;
	
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
		players = new Player[4];
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;
		players[3] = p4;
		for(int i = 0 ; i < playerCount ; i++){
			players[i].setGame(this);
		}
		currentPlayerNumber = 1;
		board = new Board();
		supportCount = new PropertyChangeSupport(this);
		history = new History();
		setToPlace(getPlayerCount()*(getCurrentPlayer().getPenguinsNumber()));
	}
	
	private void changeBoard(Board b) {
		board = b;
	}
	
	private void changeCurrentPlayerNumber(int i) {
		currentPlayerNumber = i;
	}
	
	private void changeHistory(History h) {
		history = h;
	}
	
	private void changeToPlace(int i) {
		toPlace = i;
	}
	
	private void changeSupportCount(PropertyChangeSupport s) {
		supportCount = s;
	}
	
	/**
	 * Ajoute un observateur des changements du jeu.
	 * @param pcl Observateur à ajouter.
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {	supportCount.addPropertyChangeListener(pcl);	}

	/**
	 * Retire un observateur des changements du jeu.
	 * @param pcl Observateur à retirer.
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) { supportCount.removePropertyChangeListener(pcl); }
	
	/**
	 * retourne le numéro du joueur courant
	 * @return numéro joueur courant
	 */
	public int currentPlayerNumber() {
		return currentPlayerNumber;
	}
	
	/**
	 * Renvoie le plateau associé au jeu.
	 * @return Plateau de jeu.
	 */
	public Board getBoard(){
		return board;
	}
	
	/**
	 * Renvoie le nombre de joueurs dans la partie.
	 * @return Nombre de joueurs.
	 */
	public int getPlayerCount(){
		return playerCount;
	}

	public int getToPlace() {
		return toPlace;
	}

	public void setToPlace(int toPlace) {
		this.toPlace = toPlace;
	}
	
	/**
	 * Indique si l'on est en phase de placement de pingouins.
	 * @return Vrai si la partie est en phase de placement, Faux sinon.
	 */
	public boolean placePhase(){
		return toPlace != 0;
	}
	
	/**
	 * Indique si l'on est en phase de déplacement.
	 * @return Vrai si la partie est en phase de déplacement, Faux sinon.
	 * Ceci correspond à !placePhase().
	 */
	public boolean movePhase(){
		return toPlace == 0;
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
		error = GOOD_TRAVEL;
		Player p = getCurrentPlayer();
		if (hasPenguinGoodOwning(p,x1,y1)) {
			Game oldGame = this;
			Tile t = board.makeMove(x1, y1, x2, y2);
			if (t != null) {
				p.changeScore(t.getFishNumber());
				p.addTile();
				p.movePenguin(x1, y1, x2, y2);
				supportCount.firePropertyChange("game", oldGame, this);
				Move m = new Move(x1, y1, x2, y2, currentPlayerNumber, t.getFishNumber());
				history.addMove(m);
				return true;
			}
			else {
				switch(board.error) {
				case 1: // board.PENGUIN_IN_TRAVEL
					error = PENGUIN_IN_TRAVEL;
				case 2: // board.HOLE_IN_TRAVEL
					error = HOLE_IN_TRAVEL;
				case 3: // board.TRAVEL_NOT_ALIGNED
					error = TRAVEL_NOT_ALIGNED;
				default:
				}
				return false;
			}
		}
		else {
			System.out.println("Ce pingouin ne vous appartient pas.");
			error = WRONG_PENGUIN;
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
		error = GOOD_PENGUIN;
		Penguin penguins[] = p.penguins();
		int nbPenguins = p.getPenguinsNumber();
		for(int i = 0; i < nbPenguins ; i++) {
			if(penguins[i].coord_x() == x1 && penguins[i].coord_y() == y1) {
				return true;
			}
		}
		error = WRONG_PENGUIN;
		return false;
	}
	
	/**
	 * retourne le joueur courant
	 * @return joueur courant
	 */
	public Player getCurrentPlayer() { return players[currentPlayerNumber - 1]; }
	
	/**
	 * Passe au joueur suivant, s'il existe.
	 * @return Vrai si on a choisi un joueur suivant, Faux sinon.
	 */
	public boolean nextPlayer() {
		for(int i = 1 ; i <= playerCount ; i++) {
			int loopPlayerNumber = (currentPlayerNumber - 1 + i) % playerCount;
			if (players[loopPlayerNumber].isPlaying()) {
				supportCount.firePropertyChange("currentPlayerNumber", currentPlayerNumber, loopPlayerNumber+1);
				currentPlayerNumber = loopPlayerNumber + 1;
				return true;
			}
		}
		return false;
	}
	
	public int[][] legitMovePossibility(Penguin p){
		return board.movePossibility(p.coord_x(),p.coord_y());
	}	/**

	/**
	 * Place un pingouin du joueur courant aux coordonnées d'entrée.
	 * Passe au tour suivant si réussi.
	 * @return Vrai si le pingouin a bien été placé, faux sinon.
	 * @param x Coordonnée x où l'on souhaite placer le pingouin.
	 * @param y Coorfonnée y où l'on souhaite placer le pingouin.
	 */
	public boolean placePenguin(int x, int y){
		error = GOOD_PLACE;
		boolean val = false;
		Player p = getCurrentPlayer();
		if(p.getAmountPlaced() < p.getPenguinsNumber()){
			Tile t = board.getTile(x, y);
			if(!t.occupied()){
				if(t.getFishNumber() == 1){
					p.penguins()[p.getAmountPlaced()] = new Penguin(x, y);
					board.occupyWithPenguin(x, y);
					p.addAmount(1);
					val = true;
					nextPlayer();
				} else {
					System.out.print("Les pingouins doivent être placés sur" +
							" une case de valeur 1.");
					error = ONLY_ONE_FISH;
				}
			} else {
				System.out.print("La case est occupée.");
				error = ALREADY_OCCUPY;
			}
		} else {
			System.out.print("Tous les pingouins sont déjà placés pour ce joueur.");
			nextPlayer();
		}
		return val;
	}
	
	/**
	 * Détermine si le joueur p peut jouer un coup.
	 * @param p Joueur à examiner.
	 * @return Vrai s'il peut jouer, Faux sinon.
	 */
	/* à déplacer dans Player ? */
	public boolean canPlay(Player p) {
		boolean possibility = false;
		int movePossibility[][];
		Penguin penguins[] = p.penguins();
		for(int i = 0 ; i < p.getPenguinsNumber() ; i++) {
			movePossibility = legitMovePossibility(penguins[i]);
			possibility = possibility || (movePossibility[0][0] != -1);
		}
		return possibility;
	}
	
	/**
	 * Retire les pingouins du joueur p de la partie, lui attribue les tuiles
	 * sous ces pingouins, puis retire le joueur de la partie.
	 * @param p Joueur à faire terminer la partie.
	 */
	/* A déplacer dans Player ? */
	public void endPlayer(Player p){
		Penguin [] listPenguin = p.penguins();
		Penguin curr;
		Tile rmTile;
		for(int i = 0 ; i < p.getPenguinsNumber() ; i++){
			curr = listPenguin[i];
			rmTile = board.removeTile(curr.coord_x(), curr.coord_y());
			p.changeScore(rmTile.getFishNumber());
			p.addTile();
		}
		p.stopPlaying();
	}
	
	/**
	 * Affiche le tableau des scores.
	 */
	public void printScoreboard(){
		Player currPlayer;
		
		System.out.println("~~~ Tableau des scores ~~~");
		for(int i = 0 ; i < playerCount ; i++){
			currPlayer = players[i];
			System.out.println("Joueur " + currPlayer.getName() + " : " + 
					currPlayer.getFishScore() + " points ; " + 
					currPlayer.getTileScore() + " tuiles.");
		}
	}
	
	/**
	 * Termine la partie. Affiche le score des joueurs et le gagnant.
	 * La gestion des égalités, s'il y a plus de 2 joueurs, n'est pas
	 * fonctionnelle.
	 */
	public void endGame(){
		printScoreboard();
		Player currPlayer = players[0];
		Player topPlayer = currPlayer;
		int scoreMax = topPlayer.getFishScore();
		int tileMax = topPlayer.getTileScore();
		for(int i = 1 ; i < playerCount ; i++){
			currPlayer = players[1];
			if(scoreMax < currPlayer.getFishScore()){
				topPlayer = currPlayer;
				scoreMax = topPlayer.getFishScore();
				tileMax = topPlayer.getTileScore();
			} else if (scoreMax == currPlayer.getFishScore()){
				if(tileMax < currPlayer.getTileScore()){
					topPlayer = currPlayer;
					scoreMax = topPlayer.getFishScore();
					tileMax = topPlayer.getTileScore();
				} else if (tileMax == currPlayer.getTileScore()) {
					System.out.println("Egalité ! Les joueurs " + topPlayer.getName() 
							+ " et " + currPlayer.getName() + " gagnent !");
					return;
				}
			}
		}
		System.out.println(topPlayer.getName() + " gagne la partie !");
	}
	
	@Override
	protected Game clone() {
		Player p[] = new Player[4];
		for(int i = 0 ; i < 4 ; i++) {
			if(i < playerCount) {
				p[i] = players[i].clone();
			}
			else {
				p[i] = null;
			}
		}
		Game c = new Game(playerCount,p[0],p[1],p[2],p[3]);
		c.changeBoard(board.clone());
		c.changeHistory(history.clone());
		c.changeCurrentPlayerNumber(currentPlayerNumber);
		c.changeToPlace(toPlace);
		c.changeSupportCount(supportCount);
		
		for(int i = 0 ; i < playerCount ; i++) {
			p[i].setGame(c);
		}
		return c;
	}
}
