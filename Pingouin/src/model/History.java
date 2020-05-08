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
	
	public History(Board boardGame,Player p[]) {
		this.boardGame = boardGame;
		past = new Move[10];
		futur = new Move[10];
		pastIndex = 0;
		futurIndex = 0;
		this.p = p;
	}
	
	public void addMove(Move m) {
		addMovePast(m);
		futurIndex = 0;
	}
	
	public void resizing(boolean choicePast) {
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
	
	private void addMovePast(Move m) {
		resizing(true);
		past[pastIndex] = m;
	}
	
	private void addMoveFutur(Move m) {
		resizing(false);
		futur[futurIndex] = m;
	}
	
	
	public void backInPast(int index) {
		while(pastIndex >= index) {
			removeMoveFromPast();
		}
	}
	
	public void backInFutur(int index) {
		while(futurIndex >= index) {
			removeMoveFromFutur();
		}
	}
	
	private void removeMoveFromPast() {
		addMoveFutur(past[pastIndex]);
		past[pastIndex].undo(boardGame, p);
		pastIndex--;
	}
	
	private void removeMoveFromFutur() {
		addMovePast(futur[futurIndex]);
		futur[futurIndex].redo(boardGame, p);
		futurIndex--;
	}
}
