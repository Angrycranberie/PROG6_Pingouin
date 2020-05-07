package controller;

import java.awt.Color;

/**
 * Classe d'IA intermédiaire. Joue des coups en privilégiant les cases donnant
 * le plus de poissons.
 * @author Vincent
 */
public class AISmart extends Player {

	public AISmart(int penguinsNumber, Color color, String name) {
		super(penguinsNumber, color, name);
	}
}
