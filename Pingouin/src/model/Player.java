package model;

/**
 * Classe Player. Contient l'ensemble des méthodes et éléments associés à un joueur
 * @author Charly
 */
public class Player {
	private String name;
	private int score;
	private int nbTile;
	private int penguins[][];
	private int nbPenguins;
	private int colour;
	private boolean playing;
	
	/**
	 * créé l'objet Player 
	 * @param nbPenguins indique le nombre de pingouin du joueur
	 * @param c couleur du joueur
	 */
	public Player(int nbPenguins , int c , String name) {
		score = 0;
		nbTile = 0;
		penguins = new int[nbPenguins][2];
		this.nbPenguins = nbPenguins;
		this.name = name;
		playing = true;
	}
	
	/**
	 * retourne le score du joueur
	 * @return score du joueur
	 */
	public int score() {
		return score;
	}
	
	/**
	 * indique si le joueur peut jouer
	 * @return indique si le joueur peut jouer
	 */
	public boolean playing() {
		return playing;
	}
	
	/**
	 * retourne le nombre de tuile récupérée par le joueur
	 * @return nombre de tuile récupérée
	 */
	public int nbTile() {
		return nbTile;
	}
	
	/**
	 * retourne la couleur du joueur
	 * @return couleur du joueur
	 */
	public int colour() {
		return colour;
	}
	
	/**
	 * retourne les coordonnées des pingouins du joueur
	 * @return coordonnées des pingouins du joueur
	 */
	public int[][] penguins(){
		return penguins;
	}
	
	/**
	 * retourne le nombre de pingouins du joueur
	 * @return
	 */
	public int nbPenguins() {
		return nbPenguins;
	}
	
	/**
	 * retourne le nom du joueur
	 * @return nom du joueur
	 */
	public String name() {
		return name;
	}
	
	/**
	 * augmente le score du joueur d'un montant donné
	 * @param add montant à ajouter
	 */
	public void changeScore(int add) {
		score += add;
	}
	
	/**
	 * augmente le nombre de tuile du joueur
	 */
	public void addTile() {
		nbTile++;
	}
	
	/**
	 * décrémente le nombre de tuile du joueur
	 */
	public void removeTile() {
		nbTile--;
	}
	
	/**
	 * bouge un pingouin d'une tuile 1 vers une tuile 2
	 * @param x1 coordonnée x de la tuile 1
	 * @param y1 coordonnée y de la tuile 1
	 * @param x2 coordonnée x de la tuile 2
	 * @param y2 coordonnée y de la tuile 2
	 */
	public void movePenguin(int x1, int y1, int x2, int y2) {
		for(int i = 0 ; i < nbPenguins ; i++) {
			if(penguins[i][0] == x1 && penguins[i][1] == y1) {
				penguins[i][0] = x2;
				penguins[i][1] = y2;
			}
		}
	}
}
