package controller;

import java.awt.Color;

/**
 * Classe d'IA difficile. Essaye d'isoler des bouts de banquise et maximise la récolte.
 * @author yeauhant
 */
public class AITrap extends Player {

	public AITrap(int penguinsNumber, Color color, String name) {
		super(penguinsNumber, color, name);
		setAI(3);
	}
}
