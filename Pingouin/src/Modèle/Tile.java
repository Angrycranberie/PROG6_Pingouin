package Modèle;

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
	public void changeStatue() {
		occupied = true;
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
