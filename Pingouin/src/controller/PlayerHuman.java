package controller;

import model.Game;

import java.awt.Color;

/**
 * Classe de joueur humain. Joue un coup à partir d'un clic.
 * @author Vincent
 */
public class PlayerHuman extends Player {

	public PlayerHuman(Game game, int penguinsNumber, Color color, String name) {
		super(game, penguinsNumber, color, name);
	}
}
