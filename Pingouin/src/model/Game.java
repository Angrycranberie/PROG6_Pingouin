package model;

import controller.AIRandom;
import controller.Player;
import controller.PlayerHuman;
import controller.ai.AIAccess;
import controller.ai.AISomme;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.Scanner;

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
	private int moveCount;

	/* Constantes de statut pour feedback. */
	public static final int PENGUIN_PLACED = 10; // Pingouin correctement placé.
	public static final int ONLY_ONE_FISH = 11; // Le pingouin n'a pas été placé car la case avait plus d'un poisson.
	public static final int ALREADY_OCCUPIED = 12; // Il y a déjà un pingouin sur la case.
	public static final int START_MOVE = 13; // Debut de la phase de déplacement.

	public static final int NO_TARGET = 20; // Aucun pingouin sur la case ciblée.
	public static final int HAS_TARGET = 21; // Un pingouin est présent sur la case ciblée.
	public static final int SAME_TARGET = 22; // La cible d'un déplacement est la même que sa source.
	
	public static final int PENGUIN_SELECTED = 30; // Pingouin correctement sélectionné.
	public static final int WRONG_PENGUIN = 31; // Le pingouin n'appartient pas au joueur courant.

	public static final int HAS_TILE = 40;
	public static final int NO_TILE = 41;

	public static final int TRAVEL_DONE = 50; // Le déplacement a été effectué.
	public static final int PENGUIN_IN_PATH = 51; // Un pingouin se trouve sur le chemin du déplacement.
	public static final int HOLE_IN_PATH = 52; // Un case infranchissable se trouve sur le chemin du déplacement.
	public static final int PATH_NOT_ALIGNED = 53; // La trajectoire du pingouin n'est pas rectiligne.
	
	public static final int GAME_END = 60; // La partie est terminée.
	
	public static final int AI_STARTS = 70; // L'IA commence son tour.
	public static final int AI_DONE = 71; // L'IA vient de finir son tour.
	
	/* * * */

	public int status;
	
	/**
	 * initialise le jeu
	 * @param playerCount nombre de joueur
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
		setToPlace(0);
		for(int i = 0 ; i < playerCount ; i++){
			players[i].setGame(this);
			setToPlace(getToPlace() + players[i].getPenguinsCount());
		}
		currentPlayerNumber = 1;
		board = new Board();
		supportCount = new PropertyChangeSupport(this);
		history = new History();
		moveCount = 0;
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
		Player p = getCurrentPlayer();
		if (hasPenguinGoodOwning(p,x1,y1)) {
			Tile t = board.makeMove(x1, y1, x2, y2);
			if (t != null) {
				System.out.println("Réussi");
				p.changeScore(t.getFishNumber());
				p.addTile();
				p.movePenguin(x1, y1, x2, y2);
				supportCount.firePropertyChange("moveCount", moveCount, moveCount+1);
				moveCount++;
				Move m = new Move(x1, y1, x2, y2, currentPlayerNumber, t.getFishNumber());
				history.addMove(m);
				setErr(TRAVEL_DONE);
				return true;
			}
			else {
				switch(board.error) {
				case 1: // board.PENGUIN_IN_TRAVEL
					setErr(PENGUIN_IN_PATH);
				case 2: // board.HOLE_IN_TRAVEL
					setErr(HOLE_IN_PATH);
				case 3: // board.TRAVEL_NOT_ALIGNED
					setErr(PATH_NOT_ALIGNED);
				default:
				}
				return false;
			}
		}
		else {
			System.out.println("Ce pingouin ne vous appartient pas.");
			setErr(WRONG_PENGUIN);
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
		Penguin[] penguins = p.getPenguins();
		for(int i = 0; i < p.getAmountPlaced() ; i++) {
			if(penguins[i].getX() == x1 && penguins[i].getY() == y1) {
				setErr(PENGUIN_SELECTED);
				return true;
			}
		}
		setErr(WRONG_PENGUIN);
		return false;
	}
	
	/**
	 * retourne le joueur courant
	 * @return joueur courant
	 */
	public Player getCurrentPlayer() { return players[currentPlayerNumber - 1]; }

	/**
	 * Retourne les joueurs de la partie.
	 * @return Tableau de joueurs.
	 */
	public Player[] getPlayers() { return players; }

	/**
	 * Retourne le joueur dont le numéro est passé en argument.
	 * @param n Numéro du joueur (à partir de 0).
	 * @return Joueur.
	 */
	public Player getPlayer(int n) {
		try {
			return players[n];
		} catch (Exception e) {
			System.err.println(e.toString());
			return null;
		}
	}
	
	/**
	 * Passe au joueur suivant, s'il existe.
	 * @return Vrai si on a choisi un joueur suivant, Faux sinon.
	 */
	public boolean nextPlayer() {
		for(int i = 1 ; i <= playerCount ; i++) {
			int loopPlayerNumber = (currentPlayerNumber - 1 + i) % playerCount;
			if (players[loopPlayerNumber].isPlaying()) {
				supportCount.firePropertyChange("moveCount", moveCount, loopPlayerNumber+1);
				currentPlayerNumber = loopPlayerNumber + 1;
				return true;
			}
		}
		return false;
	}
	
	public int[][] legitMovePossibility(Penguin p){
		return board.movePossibility(p.getX(),p.getY());
	}

	/**
	 * Place un pingouin du joueur courant aux coordonnées d'entrée.
	 * Passe au tour suivant si réussi.
	 * @return Vrai si le pingouin a bien été placé, faux sinon.
	 * @param x Coordonnée x où l'on souhaite placer le pingouin.
	 * @param y Coorfonnée y où l'on souhaite placer le pingouin.
	 */
	public boolean placePenguin(int x, int y){
		boolean val = false;
		Player p = getCurrentPlayer();
		if(p.getAmountPlaced() < p.getPenguinsCount()){
			Tile t = board.getTile(x, y);
			if(t == null) {
				System.out.println("La case est vide.");
				return false;
			}
			if(!t.occupied()){
				if(t.getFishNumber() == 1){
					p.getPenguins()[p.getAmountPlaced()] = new Penguin(x, y);
					board.occupyWithPenguin(x, y);
					p.addAmount(1);
					setToPlace(getToPlace()-1);
					val = true;
					nextPlayer();
					setErr(PENGUIN_PLACED);
				} else {
					System.out.print("Les pingouins doivent être placés sur" +
							" une case de valeur 1.");
					setErr(ONLY_ONE_FISH);
				}
			} else {
				System.out.print("La case est occupée.");
				setErr(ALREADY_OCCUPIED);
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
		if(placePhase()) return true;
		boolean possibility = false;
		int[][] movePossibility;
		Penguin[] penguins = p.getPenguins();
		for(int i = 0; i < p.getAmountPlaced() ; i++) {
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
		Penguin[] listPenguin = p.getPenguins();
		Penguin curr;
		Tile rmTile;
		for(int i = 0; i < p.getPenguinsCount() ; i++){
			curr = listPenguin[i];
			rmTile = board.removeTile(curr.getX(), curr.getY());
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
	
	/**
	 * Annule n coups dans la partie
	 * @param n Nombre de coups à annuler.
	 */
	public void undo(int n){
		try{
			history.backInPast(history.getPastIndex()-n, board, players);
		} catch (ArrayIndexOutOfBoundsException e){
			// Il faudrait prévenir l'appelant d'une erreur.
		}
	}

	public void redo(int n){
		try{
			history.backInFutur(history.getPastIndex()-n, board, players);
		} catch (ArrayIndexOutOfBoundsException e){
			// Il faudrait prévenir l'appelant d'une erreur.
		}
	}
	
	@Override
	public Game clone() {
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

	/**
	 * Passe au joueur précédent.
	 */
	public void prevPlayer() {
		for(int i = 1 ; i <= playerCount ; i++) {
			int loopPlayerNumber = (currentPlayerNumber - i);
			if (loopPlayerNumber <= 0) loopPlayerNumber += playerCount;
			if (players[loopPlayerNumber-1].isPlaying()) {
				supportCount.firePropertyChange("currentPlayerNumber", currentPlayerNumber, loopPlayerNumber + 1);
				currentPlayerNumber = loopPlayerNumber;

			}
		}
	}

	/**
	 * Annule le dernier placement de pingouin. Rend la main à ce joueur.
	 */
	public void cancelPlace() {
		prevPlayer();
		Player p = getCurrentPlayer();
		p.addAmount(-1);
		Penguin lastP = p.getPenguin(p.getAmountPlaced());
		board.freeFromPenguin(lastP.getX(), lastP.getY());
		p.getPenguins()[p.getAmountPlaced()] = null; 
	}

	/**
	 * Indique si la case non-nulle donnée en argument est occupée
	 * par un pingouin ou non.
	 * @param x1 Coordonnée x de la case à tester.
	 * @param y1 Coordonnée y de la case à tester.
	 * @return Vrai si la case est occupée ; faux sinon.
	 */
	public boolean occupied(int x1, int y1) {
		boolean res = board.getTile(x1, y1).occupied();
		if(!res) setErr(NO_TARGET);
		else setErr(HAS_TARGET);
		return res;
	}
	
	/**
	 * Indique si la case donnée en argument est nulle ou non.
	 * @param x1 Coordonnée x de la case à tester.
	 * @param y1 Coordonnée y de la case à tester.
	 * @return Faux si la case est nulle, Vrai sinon.
	 */
	public boolean exists(int x1, int y1) {
		boolean res = board.getTile(x1, y1) != null;
		if(res) setErr(HAS_TILE);
		else setErr(NO_TILE);
		return res;
	}
	
	public void setErr(int e){
		supportCount.firePropertyChange("status", status, e);
		status = e;
	}

	public boolean save(String fileName) {
		
		PrintWriter saveBot;
		
		try {
			saveBot = new PrintWriter(fileName, "UTF-8") ;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		} 
		
		saveBot.flush(); // on vide le fichier si jamais il a déjà été utilisé
		
		// Sauvegarde du nombre de joueur
		saveBot.println("# Nombre de joueur");
		saveBot.println(playerCount);
		System.out.println("playerCount saved");
		
		// Sauvegarde des joueurs
		for(int  i = 0; i < playerCount; i++) {
			saveBot.println("# Joueur "+i);
			// Sauvegarde des informations du joueur 
			saveBot.println(players[i].toString());
			// Sauvegarde de ses pingouins
			for(int j = 0; j < players[i].getPenguinsCount() ; j++) {
				saveBot.println(players[i].getPenguins()[j].toString());
			}
			
			System.out.println("Player "+i+" saved");
		}
		
		// Sauvegarde du joueur courant
		saveBot.println("# Numéro du joueur courant");
		saveBot.println(currentPlayerNumber);
		System.out.println("currentPlayerNumber saved");
		
		// Sauvegarde du plateau
		saveBot.println("# Board");
		for(int i = 0; i < Board.LENGTH; i++) {
			for(int j = 0; j < Board.WIDTH; j++) {
				try {
					saveBot.println(board.getTile(j, i).toString());
				} catch (NullPointerException e) {
					// Soit on est arrivé sur une tuile null (plus en jeu), soit on est au bout du tableau
					saveBot.println();
				} catch (Exception e) {
					System.err.println(e.getMessage());
					saveBot.close();
					return false;
				}
			}
		}
		System.out.println("board saved");
		
		// Sais pas si doit être sauvegardé
		//PropertyChangeSupport supportCount;
		
		// Sauvegarde de l'historique
		saveBot.println("# Historique");
		saveBot.println(history.getPastIndex());
		for(int i = 0; i < history.getPast().length; i++) {
			try {
				saveBot.println(history.getPast()[i].toString());
			} catch (NullPointerException e) {
				// On est arrivé au bout du tableau
				// Si le tableau est vide
				if(i==0) saveBot.println();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				saveBot.close();
				return false;
			}
		}
		saveBot.println(history.getFuturIndex());
		for(int i = 0; i < history.getFutur().length; i++) {
			try {
				saveBot.println(history.getFutur()[i].toString());
			} catch (NullPointerException e) {
				// On est arrivé au bout du tableau
				// Si le tableau est vide
				if(i==0) saveBot.println();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				saveBot.close();
				return false;
			}
		}
		System.out.println("history saved");
		
		// Sauvegarde de la phase
		saveBot.println("# Pingouin restants à placer");
		saveBot.println(toPlace);
		System.out.println("toPlace saved");
			
		saveBot.close();
		return true;
	}
	
	public boolean load(String fileName) {
		Scanner loadBot;
		
		loadBot = new Scanner(fileName);
		
		// récupération du nombre de joueur
		if(loadBot.hasNext("# Nombre de joueur")) {
			loadBot.next("# Nombre de joueur");
			if(loadBot.hasNextInt()) {
				playerCount = loadBot.nextInt();
			}
			else {
				System.out.println("Erreur : on attendez un entier signifiant le nombre joueur");
				loadBot.close();
				return false;
			}
		}
		else {
			System.out.println("Erreur : on attendez le nombre de joueur dans la partie");
			loadBot.close();
			return false;
		}
		
		// récupération des joueurs
		players = null;
		Player player;
		int isAI;
		String name;
		int color;
		int penguinsCount;
		Penguin p[];
		int x,y;
		
		players = new Player[playerCount];
		for(int  i = 0; i < playerCount; i++) {
			if(loadBot.hasNext("# Joueur " + i)) {
				loadBot.next("# Joueur " + i);
				if(loadBot.hasNextInt()) {
					isAI = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez un entier représentant l'IA du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextLine()) {
					name = loadBot.nextLine();
				}
				else {
					System.out.println("Erreur : on attendez une chaine représentant le nom du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					penguinsCount = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez un entier signifiant le nombre de pingouins du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					color = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez un entier signifiant la couleur du joueur");
					loadBot.close();
					return false;
				}
				player = new Player(penguinsCount,color,name);
				switch(isAI) {
				case 0:
					player = new PlayerHuman(penguinsCount,color,name);
					break;
				case 1:
					player = new AIRandom(penguinsCount,color,name);
					break;
				case 2:
					player = new AISomme(penguinsCount,color,name);
					break;
				case 3:
					player = new AIAccess(penguinsCount,color,name);
					break;
				default:
						break;
				}
				if(loadBot.hasNextInt()) {
					player.changeFishScore(loadBot.nextInt());
				}
				else {
					System.out.println("Erreur : on attendez un entier signifiant le nombre de points du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					player.changeTileScore(loadBot.nextInt());
				}
				else {
					System.out.println("Erreur : on attendez un entier signifiant le nombre de tuiles récupérées");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					player.changeAmountPlaced(loadBot.nextInt());
				}
				else {
					System.out.println("Erreur : on attendez un entier signifiant le nombre de pingouins placés");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextBoolean()) {
					player.changePlaying(loadBot.nextBoolean());
				}
				else {
					System.out.println("Erreur : on attendez un booleen signifiant si le joueur joue encore ou pas");
					loadBot.close();
					return false;
				}
				
				p = new Penguin[penguinsCount];
				for(int j = 0; j < penguinsCount ; j++) {
					if(loadBot.hasNextInt()) {
						x = loadBot.nextInt();
					}
					else {
						System.out.println("Erreur : on attendez un entier signifiant la coordonnée x d'un pingouin");
						loadBot.close();
						return false;
					}
					if(loadBot.hasNextInt()) {
						y = loadBot.nextInt();
					}
					else {
						System.out.println("Erreur : on attendez un entier signifiant la coordonnée y d'un pingouin");
						loadBot.close();
						return false;
					}
					p[j] = new Penguin(x,y);
				}
				
				player.changePenguins(p);
				players[i] = player;
			}
			else {
				System.out.println("Erreur : on attendez un joueur");
				loadBot.close();
				return false;
			}
		}
		
		// récupération du joueur courant
		if(loadBot.hasNext("# Numéro du joueur courant")) {
			loadBot.next("# Numéro du joueur courant");
			if(loadBot.hasNextInt()) {
				currentPlayerNumber = loadBot.nextInt();
			}
			else {
				System.out.println("Erreur : on attendez un entier signifiant le numéro du joueur courant");
				loadBot.close();
				return false;
			}
		}
		else {
			System.out.println("Erreur : on attendez le joueur courant");
			loadBot.close();
			return false;
		}
		
		// récupération du plateau
		board = new Board();
		Tile tab[][] = new Tile[Board.LENGTH][Board.WIDTH];
		int fishNumber;
		boolean occupied;
		if(loadBot.hasNext("# Board")) {
			loadBot.next("# Board");
			for(int i = 0; i < Board.LENGTH; i++) {
				for(int j = 0; j < Board.WIDTH; j++) {
					if(loadBot.hasNextInt()) {
						fishNumber = loadBot.nextInt();
					}
					else {
						System.out.println("Erreur : on attendez un entier signifiant le nombre de poisson de la tuile");
						loadBot.close();
						return false;
					}
					if(loadBot.hasNextBoolean()) {
						occupied = loadBot.nextBoolean();
					}
					else {
						System.out.println("Erreur : on attendez le booléen représentant si un pingouin est sur la tuile");
						loadBot.close();
						return false;
					}
					tab[i][j] = new Tile(fishNumber);
					if(occupied) tab[i][j].occupy();
					else tab[i][j].free();
				}
			}
		}
		else {
			System.out.println("Erreur : on attendez un plateau");
			loadBot.close();
			return false;
		}
		board.changeTab(tab);
		
		// récupération de l'historique
		history = new History();
		int pastIndex,futurIndex;
		int x1,y1,x2,y2,playerNumber;
		Move past[],futur[];
		if(loadBot.hasNext("# Historique")) {
			loadBot.next("# Historique");
			if(loadBot.hasNextInt()) {
				pastIndex = loadBot.nextInt();
			}
			else {
				System.out.println("Erreur : on attendez la longueur de past");
				loadBot.close();
				return false;
			}
			history.changePastIndex(pastIndex);
			past = new Move[pastIndex+1];
			for(int i = 0; i < pastIndex; i++) {
				if(loadBot.hasNextInt()) {
					x1 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez x1");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					y1 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez y1");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					x2 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez x2");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					y2 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez y2");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					playerNumber = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez le numéro du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					fishNumber = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez le nombre de poisson");
					loadBot.close();
					return false;
				}
				past[i] = new Move(x1,y1,x2,y2,playerNumber,fishNumber);
			}
			history.changePast(past);
			
			if(loadBot.hasNextInt()) {
				futurIndex = loadBot.nextInt();
			}
			else {
				System.out.println("Erreur : on attendez la longueur de futur");
				loadBot.close();
				return false;
			}
			history.changeFuturIndex(pastIndex);
			futur = new Move[futurIndex+1];
			for(int i = 0; i < futurIndex; i++) {
				if(loadBot.hasNextInt()) {
					x1 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez x1");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					y1 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez y1");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					x2 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez x2");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					y2 = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez y2");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					playerNumber = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez le numéro du joueur");
					loadBot.close();
					return false;
				}
				if(loadBot.hasNextInt()) {
					fishNumber = loadBot.nextInt();
				}
				else {
					System.out.println("Erreur : on attendez le nombre de poisson");
					loadBot.close();
					return false;
				}
				futur[i] = new Move(x1,y1,x2,y2,playerNumber,fishNumber);
			}
			history.changeFutur(futur);
		}else {
			System.out.println("Erreur : on attendez l'historique");
			loadBot.close();
			return false;
		}
			
		// récupération de la phase de jeu
		if(loadBot.hasNext("# Pingouin restants à placer")) {
			loadBot.next("# Pingouin restants à placer");
			if(loadBot.hasNextInt()) {
				toPlace = loadBot.nextInt();
			}
			else {
				System.out.println("Erreur : on attendez un entier signifiant le nombre de pigouin à placer");
				loadBot.close();
				return false;
			}
		}
		else {
			System.out.println("Erreur : on attendez le nombre de pingouin à placer");
			loadBot.close();
			return false;
		}
		
		
		loadBot.close();
		return false;
	}
}
