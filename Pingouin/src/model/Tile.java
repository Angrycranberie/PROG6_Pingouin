package model;

/**
 * Classe Tile. Une case du jeu, avec sa valeur et une donnée indiquant si elle est
 * occupée ou non.
 * @author Charly
 *
 */
public class Tile implements Cloneable {
	// Constantes du nombre de poissons par tuile.
	public static final int ONE_FISH = 1;
	public static final int TWO_FISH = 2;
	public static final int THREE_FISH = 3;

	private int fishNumber;		// Nombre de poissons sur la tuile.
	private boolean occupied;	// Si la case est occupée par un pingouin ou non.

	/**
	 * Constructeur d'une tuile.
	 * On vérifie que le nombre de poisson entré est bien entre 1 et 3.
	 * Si ce n'est pas le cas, on ramène ce nombre dans la plage correcte.
	 */
	public Tile(int fishNumber) {
		if (fishNumber < 1) this.setFishNumber(Tile.ONE_FISH);
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

	/**
	 * Renvoie une version String des valeurs de la tuile
	 * @return renvoie une représentation dans un String de la tuile 
	 */
	public String toString() {
		 return fishNumber+" "+occupied;
	}
	
	@Override
	protected Tile clone() {
		Tile t = new Tile(fishNumber);
		if(occupied) {
			t.occupy();
		}
		else {
			t.free();
		}
		return t;
	}
}
