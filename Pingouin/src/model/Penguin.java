package model;

public class Penguin {
	int x;
	int y;
	
	/**
	 * créé un pingouin 
	 * @param x coord x du pingouin
	 * @param y coord y du pingouin
	 */
	public Penguin(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * retourne la coordonnée x du pingouin
	 * @return coord x
	 */
	public int coord_x() {
		return x;
	}
	
	/**
	 * retourne la coordonnée y du pingouin
	 * @return coord y
	 */
	public int coord_y() {
		return y;
	}
	
	public void changePosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
