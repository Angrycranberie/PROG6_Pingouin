package model;

public class Tile {
	private int fishNumber;		// Nombre de poissons sur la tuile.
	private boolean occupied;	// Si la case est occupée par un pingouin ou non.

	/**
	 * Constructeur de la classe pour une tuiles Tile.
	 * On vérifie que le nombre de poisson entré est bien entre 1 et 3.
	 */
	Tile(int fishNumber) {
		if (fishNumber < 1) this.setFishNumber(1);
		else this.setFishNumber(Math.min(fishNumber, 3));
		this.free();
	}
	
	/**
	 * Affecte un nombre de poisson donné à la tuile.
	 * @param fishNumber Nombre de poissons à attribuer à la tuile (1, 2 ou 3).
	 */
	public void setFishNumber(int fishNumber) {
		this.fishNumber = fishNumber;
		occupied = false;
	}

	/**
	 * Renvoie le nombre de poissons d'une tuile.
	 * @return Nombre de poissons de la tuile (1, 2 ou 3).
	 */
	public int getFishNumber() { return this.fishNumber; }
	
	/**
	 * Définit la tuile comme étant occupée par un pingouin.
	 */
	public void occupy() { this.occupied = true; }
	
	/**
	 * Définit la tuile comme étant libre (sans pingouin).
	 */
	public void free() { this.occupied = false; }
	
	/**
	 * Indique si une tuile est occupée par un pingouin.
	 * @return Vrai (true) si la tuile est occupée ; faux (false) sinon.
	 */
	public boolean occupied() {
		return this.occupied;
	}	
}
