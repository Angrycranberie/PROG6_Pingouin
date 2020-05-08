package model;

import controller.Player;

/**
 * Classe History. Gère l'historique des coups. Permet d'en annuler ou refaire.
 * @author Charly
 *
 */
public class History {
	Move past[];
	int pastIndex;
	Move futur[];
	int futurIndex;
	Board boardGame;
	Player p[];
	
	/**
	 * créé l'objet History qui représente un historique des coups effectué lors d'une partie
	 * @param boardGame plateau du jeu
	 * @param p ensemble des joueurs
	 */
	public History(Board boardGame,Player p[]) {
		this.boardGame = boardGame;
		past = new Move[10];
		futur = new Move[10];
		pastIndex = 0;
		futurIndex = 0;
		this.p = p;
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
	}
	
	/**
	 * ajoute un coup au tableau représentant le futur (coups annulés)
	 * @param m coup
	 */
	private void addMoveFutur(Move m) {
		resizing(false);
		futur[futurIndex] = m;
	}
	
	/**
	 * permet de revenir avant un certain coup
	 * @param index indice du coup
	 */
	public void backInPast(int index) {
		while(pastIndex >= index) {
			removeMoveFromPast();
		}
	}
	
	/**
	 * permet de revenir après un certain coup qui a été annulé
	 * @param index indice du coup
	 */
	public void backInFutur(int index) {
		while(futurIndex >= index) {
			removeMoveFromFutur();
		}
	}
	
	/**
	 * permet de revenir d'un coup dans la partie
	 */
	private void removeMoveFromPast() {
		addMoveFutur(past[pastIndex]);
		past[pastIndex].undo(boardGame, p);
		pastIndex--;
	}
	
	/**
	 * permet d'avancer d'un coup dans la partie (coup précédement annulé)
	 */
	private void removeMoveFromFutur() {
		addMovePast(futur[futurIndex]);
		futur[futurIndex].redo(boardGame, p);
		futurIndex--;
	}
}
