package model;

public class Penguin implements Cloneable{
	private int x;
	private int y;
	
	/**
	 * Crée un pingouin 
	 * @param x coord x du pingouin
	 * @param y coord y du pingouin
	 */
	public Penguin(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Retourne la coordonnée x du pingouin
	 * @return coord x
	 */
	public int coord_x() {
		return x;
	}
	
	/**
	 * Retourne la coordonnée y du pingouin
	 * @return coord y
	 */
	public int coord_y() {
		return y;
	}
	
	/**
	 * Change les coordonnées du pingouin
	 * @param x la nouvelle coordonnée x
	 * @param y la nouvelle coordonnée y
	 */
	public void changePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Penguin clone() {
		return new Penguin(x,y);
	}
}
