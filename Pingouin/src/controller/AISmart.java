package controller;

import java.awt.Color;

import model.Game;

/**
 * Classe d'IA intermédiaire. Joue des coups en privilégiant les cases donnant
 * le plus de poissons.
 * @author Vincent
 */
public class AISmart extends Player {

	public AISmart(Game game, int penguinsNumber, Color color, String name) {
		super(game, penguinsNumber, color, name);
	}


}
