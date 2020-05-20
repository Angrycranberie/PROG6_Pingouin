package controller;

import model.Game;
import model.Penguin;

import controller.ai.AIAccess;
import controller.ai.AISomme;

/**
 * Classe Player. Contient l'ensemble des méthodes et éléments associés à un joueur
 * @author Charly
 */
public class Player implements Cloneable {
	private Game game;
	private String name; // Nom du joueur.
	private int fishScore; // Score associé au nombre de poissons obtenu.
	private int tileScore; // Score associé au nombre de cases obtenues.
	private int amountPlaced; // Nombre courant de pingouins placés.
	private Penguin penguins[]; // Liste des pingouins du joueur.
	private int penguinsCount; // Nombre de pingouins du joueur.
	private int color; // Couleur du joueur.
	private boolean playing; // Si le joueur est toujours dans la partie ou non.
	private int isAI;

	// Constantes de couleurs du joueur.
	public static final int COLOR_CLASSIC = 0; // Noir pingouin
	public static final int COLOR_CHICK = 1; // Jaune poussin
	public static final int COLOR_SEA = 2; // Bleu océan
	public static final int COLOR_POLAR = 3; // Cyan polaire

	/**
	 * Création du joueur.
	 * @param penguinsCount Indique le nombre de pingouin du joueur
	 * @param color Couleur du joueur.
	 */

	public Player(int penguinsCount, int color, String name) {
		this.fishScore = this.tileScore = this.amountPlaced = 0;
		this.penguinsCount = penguinsCount;
		this.color = color;
		penguins = new Penguin[penguinsCount];
		this.name = name;
		playing = true;
		
	}

	// SETTERS

	public void changeTileScore(int i) {
		tileScore = i;
	}
	
	public void changeFishScore(int i) {
		fishScore = i;
	}
	
	public void changeAmountPlaced(int i) {
		amountPlaced = i;
	}
	
	public void changePenguins(Penguin[] p) { penguins = p; }
	
	public void changePlaying(boolean b) {	playing = b; }
	
	public void setGame(Game g){ game = g; }

	/**
	 * Retourne le jeu.
	 * @return Le Jeu.
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Retourne le score principal du joueur, associé au nombre de poissons récoltés.
	 * @return Nombre de poissons récoltés.
	 */
	public int getFishScore() {
		return fishScore;
	}
	
	/**
	 * Indique si le joueur peut jouer ou non.
	 * @return Vrai (true) si le joueur peut joueur ; faux (false) sinon.
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * Fixe la valeur playing à false.
	 */
	public void stopPlaying(){
		playing = false;
	}
	
	/**
	 * Retourne le score secondaire du joueur, associé au nombre de tuiles récupérées.
	 * @return Nombre de tuiles récupérées.
	 */
	public int getTileScore() {
		return tileScore;
	}
	
	/**
	 * Retourne la couleur du joueur.
	 * @return Couleur du joueur.
	 */
	public int getColor() {
		return color;
	}
	
	/**
	 * Retourne les coordonnées des pingouins du joueur.
	 * @return Tableau des coordonnées des pingouins du joueur.
	 */
	public Penguin[] getPenguins(){
		return penguins;
	}

	/**
	 * Retourne le pingouin dont le numéro est passé en argument.
	 * @param n Numéro du pingouin à retourner (à partir de 0).
	 * @return Pingouin.
	 */
	public Penguin getPenguin(int n) {
		try {
			return penguins[n];
		} catch (Exception e) {
			System.err.println(e.toString());
			return null;
		}
	}
	
	/**
	 * Retourne le nombre de pingouins du joueur.
	 * @return Nombre de pingouins du joueur.
	 */
	public int getPenguinsCount() {
		return penguinsCount;
	}
	
	/**
	 * Retourne le nombre de pingouins placés par le joueur.
	 * @return Le nombre de pingouins placés par le joueur.
	 */
	public int getAmountPlaced() {
		return amountPlaced;
	}
	
	/**
	 * Augmente le nombre de pingouins placés par le joueur de i.
	 * @param i Valeur de l'augmentation.
	 */
	public void addAmount(int i) {
		amountPlaced += i;
	}
	
	/**
	 * Retourne le nom du joueur.
	 * @return Nom du joueur.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Augmente (ou diminue) le score du joueur d'un montant donné.
	 * @param n Montant à additionner au score.
	 */
	public void changeScore(int n) {
		fishScore += n;
	}
	
	/**
	 * Augmente le nombre de tuiles du joueur.
	 */
	public void addTile() {
		tileScore++;
	}
	
	/**
	 * Diminue le nombre de tuiles du joueur.
	 */
	public void removeTile() {
		tileScore--;
	}
	
	/**
	 * Indique si le joueur est un IA ou non.
	 * @return Vrai si le joueur est une IA, Faux sinon.
	 */
	public boolean isAI() {
		return isAI != 0;
	}
	
	/**
	 * Fixe la valeur d'IA du joueur.
	 * @param v Valeur d'IA du joueur (Vrai si c'en est une, Faux sinon).
	 */
	public void setAI(int AI){
		this.isAI = AI;
	}
	
	/**
	 * Bouge un pingouin d'une tuile vers une autre.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 */
	public void movePenguin(int x1, int y1, int x2, int y2) {
		for (int i = 0; i < penguinsCount; i++) {
			if(penguins[i].getX() == x1 && penguins[i].getY() == y1) {
				penguins[i].changePosition(x2,y2);
			}
		}
	}
	
	/**
	 * Place un pingouin au premier tour de jeu (pour joueur IA) 
	 * @return
	 */
	public boolean positionPenguin() {
		return false;
	}
	
	/**
	 * Place un pingouin au premier tour de jeu en (x,y) (pour joueur Humain)
	 * @param x coordonnées x de la case de départ
	 * @param y coordonnées y de la case de départ
	 * @return
	 */
	public boolean positionPenguin(int x, int y) {
		return false;
	}
	
	/**
	 * Joue un coup (pour joueur IA)
	 * @return un booléen disant si le coup a bien été joué ou non
	 */
	public boolean play() {
		return false;
	}
	
	/**
	 * Joue un coup (pour joueur Humain)
	 * Déplace le pingouin de coordonnées (x1,y1) en (x2,y2) si le coup est possible
	 * @param x1 coordonnées x du pingouin
	 * @param y1 coordonnées y du pingouin
	 * @param x2 coordonnées x de la case d'arrivée
	 * @param y2 coordonnées y de la case d'arrivée
	 * @returnun booléen disant si le coup a été joué ou non
	 */
	public boolean play(int x1, int y1, int x2, int y2) {
		return false;
	}
	
	@Override
	public Player clone() {
		Player p = null;
		if(!isAI()) {
			p = new Player(penguinsCount, color, name);
		}
		else {
			switch(isAI) {
			case 1:
				p = new AIRandom(penguinsCount, color, name);
				break;
			case 2:
				p = new AISomme(penguinsCount, color, name);
				break;
			case 3:
				p = new AIAccess(penguinsCount, color, name);
				break;
			default:
				break;
			}
		}
		p.changeTileScore(tileScore);
		p.changeFishScore(fishScore);
		p.changeAmountPlaced(amountPlaced);
		p.changePlaying(playing);
		
		Penguin pe[] = new Penguin[penguinsCount];
		for(int i = 0 ; i < penguinsCount ; i++) {
			if(penguins[i] == null) pe[i] = null;
			else pe[i] = penguins[i].clone();
		}
		p.changePenguins(pe);
		return p;
	}
	
	/**
	 * Renvoie les informations de Player en un String hormis Game et Penguins[] 
	 */
	public String toString() {
		return isAI + "\n" + name + "\n" + penguinsCount + "\n" + color + "\n" + fishScore + "\n" + tileScore + "\n" + amountPlaced + "\n" + playing + "\n";
	}
}
