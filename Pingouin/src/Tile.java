
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
