package model;

import java.awt.*;

public class Player {
	private String name; // Nom du joueur.
	private int fishScore; // Score associé au nombre de poissons obtenu.
	private int tileScore; // Score associé au nombre de cases obtenues.
	private int penguins[][]; // Liste des pingouins du joueur.
	private int penguinsNumber; // Nombre de pingouins du joueur.
	private Color color; // Couleur du joueur.
	private boolean playing; // Si le joueur est toujours dans la partie ou non.
	
	/**
	 * Création du joueur.
	 * @param penguinsNumber Indique le nombre de pingouin du joueur
	 * @param color Couleur du joueur.
	 */
	public Player(int penguinsNumber, Color color, String name) {
		this.fishScore = this.tileScore = 0;
		penguins = new int[penguinsNumber][2];
		this.penguinsNumber = penguinsNumber;
		this.color = color;
		this.name = name;
		playing = true;
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
	public Color getColor() {
		return color;
	}
	
	/**
	 * Retourne les coordonnées des pingouins du joueur.
	 * @return Tableau des coordonnées des pingouins du joueur.
	 */
	public int[][] getPenguins(){
		return penguins;
	}
	
	/**
	 * Retourne le nombre de pingouins du joueur.
	 * @return Nombre de pingouins du joueur.
	 */
	public int getPenguinsNumber() {
		return penguinsNumber;
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
	 * Bouge un pingouin d'une tuile vers une autre.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 */
	public void movePenguin(int x1, int y1, int x2, int y2) {
		for (int i = 0; i < penguinsNumber; i++) {
			if (penguins[i][0] == x1 && penguins[i][1] == y1) {
				penguins[i][0] = x2;
				penguins[i][1] = y2;
			}
		}
	}
}
