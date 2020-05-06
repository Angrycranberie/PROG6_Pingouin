package controller;

import java.awt.Color;

import model.Game;

/**
 * Classe d'IA difficile. Essaye d'isoler des bouts de banquise et maximise la récolte.
 * @author yeauhant
 */
public class AITrap extends Player {

	public AITrap(Game game, int penguinsNumber, Color color, String name) {
		super(game, penguinsNumber, color, name);
	}

}
