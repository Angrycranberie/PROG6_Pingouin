
/*
 * Objet Tile. Correspond à une case du plateau.
 * Contient le nombre de poissons de la case, et si elle est occupée par un pingouin.
 * Les cases retirées de la partie sont des objets null
 */
public class Tile {
	private int value;
	private boolean occupied;
	
	public void setValue(int value) {
		this.value = value;
		occupied = false;
	}
	
	public void changeStatue() {
		occupied = true;
	}
	
	public int value() {
		return value;
	}
	
	public boolean occupied() {
		return occupied;
	}	
}
