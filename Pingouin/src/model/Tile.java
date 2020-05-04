package model;

/**
 * Classe Tile. Une case du jeu, avec sa valeur et une donnée indiquant si elle est
 * occupée ou non.
 * @author Charly
 *
 */
public class Tile {
	private int value;
	private boolean occupied;
	
	/**
	 * affecte une valeur à une tuile
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
		occupied = false;
	}
	
	/**
	 * rend une tuile occupée par un pingouin
	 */
	public void taken() {
		occupied = true;
	}
	
	/**
	 * rend une tuile libre
	 */
	public void quit() {
		occupied = false;
	}
	
	/**
	 * renvoi la valeur d'une tuile
	 * @return valeur de la tuile
	 */
	public int value() {
		return value;
	}
	
	/**
	 * indique si une tuile est occupée
	 * @return occupation de la tuile
	 */
	public boolean occupied() {
		return occupied;
	}	
}
