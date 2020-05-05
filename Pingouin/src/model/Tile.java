package model;

public class Tile {
	private int value;
	private boolean occupied;
	
	/**
	 * affecte une valeur à une tuile
	 * @param value
	 */
	public Tile(int value) {
		this.value = value;
		occupied = false;
	}
	
	/**
	 * rend une tuile occupée par un pingouin
	 */
	public void take() {
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
	public boolean isOccupied() {
		return occupied;
	}	
}
