package model;

import controller.Player;

/**
 * Classe History. Gère l'historique des coups. Permet d'en annuler ou refaire.
 * @author Charly
 *
 */
public class History implements Cloneable{
	private Move past[];
	private int pastIndex;
	private Move futur[];
	private int futurIndex;
	
	/**
	 * créé l'objet History qui représente un historique des coups effectué lors d'une partie
	 * @param boardGame plateau du jeu
	 * @param p ensemble des joueurs
	 */
	public History() {
		past = new Move[10];
		futur = new Move[10];
		pastIndex = 0;
		futurIndex = 0;
	}
	
	private void changePast(Move p[]) {
		past = p;
	}
	
	private void changeFutur(Move f[]) {
		futur = f;
	}
	
	private void changePastIndex(int i) {
		pastIndex = i;
	}
	
	private void changeFuturIndex(int i) {
		futurIndex = i;
	}
	
	public int getPastIndex(){
		return pastIndex;
	}
	
	public int getFuturIndex(){
		return futurIndex;
	}
	
	/**
	 * méthode qui permet d'ajouté un coup qui vient d'être effectué
	 * @param m coup
	 */
	public void addMove(Move m) {
		addMovePast(m);
		futurIndex = 0;
	}
	
	/**
	 * redimensionne les tableaux utilisés par l'objet History
	 * @param choicePast (0 -> redimensionne le tableau futur, 1 -> redimensionne le tableau past)
	 */
	private void resizing(boolean choicePast) {
		if(choicePast) {
			if(pastIndex >= past.length) {
				Move temp[] = new Move[past.length*2];
				for(int i = 0; i < past.length ; i++) {
					temp[i] = past[i];
				}
				past = temp;
			}
		}
		else {
			if(futurIndex >= futur.length) {
				Move temp[] = new Move[futur.length*2];
				for(int i = 0; i < futur.length ; i++) {
					temp[i] = futur[i];
				}
				futur = temp;
			}
		}
	}
	
	/**
	 * ajoute un coup au tableau représentant le passé
	 * @param m coup
	 */
	private void addMovePast(Move m) {
		resizing(true);
		past[pastIndex] = m;
		pastIndex++;
	}
	
	/**
	 * ajoute un coup au tableau représentant le futur (coups annulés)
	 * @param m coup
	 */
	private void addMoveFutur(Move m) {
		resizing(false);
		futur[futurIndex] = m;
		futurIndex++;
	}
	
	/**
	 * permet de revenir avant un certain coup
	 * @param index indice du coup
	 */
	public void backInPast(int index,Board boardGame, Player p[]) {
		while(pastIndex >= index) {
			removeMoveFromPast(boardGame,p);
		}
	}
	
	/**
	 * permet de revenir après un certain coup qui a été annulé
	 * @param index indice du coup
	 */
	public void backInFutur(int index,Board boardGame, Player p[]) {
		while(futurIndex >= index) {
			removeMoveFromFutur(boardGame,p);
		}
	}
	
	/**
	 * permet de revenir d'un coup dans la partie
	 */
	private void removeMoveFromPast(Board boardGame, Player p[]) {
		pastIndex--;
		addMoveFutur(past[pastIndex]);
		past[pastIndex].undo(boardGame, p);
		
	}
	
	/**
	 * permet d'avancer d'un coup dans la partie (coup précédement annulé)
	 */
	private void removeMoveFromFutur(Board boardGame, Player p[]) {
		futurIndex--;
		addMovePast(futur[futurIndex]);
		futur[futurIndex].redo(boardGame, p);
		
	}
	
	@Override
	protected History clone() {
		History h = new History();
		
		Move p[] = new Move[past.length];
		for(int i = 0; i < pastIndex; i++) {
			p[i] = past[i].clone();
		}
		h.changePast(p);
		
		Move f[] = new Move[futur.length];
		for(int i = 0; i < futurIndex; i++) {
			p[i] = futur[i].clone();
		}
		h.changeFutur(f);
		h.changePastIndex(pastIndex);
		h.changeFuturIndex(futurIndex);
		return h;
	}
}
