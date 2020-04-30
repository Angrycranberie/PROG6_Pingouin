package model;

/**
 * Classe lançant différents tests sur le plateau : mélange, alignement, déplacements
 * @author Charly
 */
public class Tests {

	public static void main(String[] args) {
		Board b = new Board();
		b.shuffle();
		b.test();
	}
}
