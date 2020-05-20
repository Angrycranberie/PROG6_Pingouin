package controller;

import model.Board;
import model.Game;
import view.EventCollector;
import view.UserInterface;

/**
 * Classe de contrôleur principal. Interprète les clics et frappes au clavier.
 * Appelle les methodes qui executent les actions.
 * @author Vincent
 * @author Yoann
 */
public class ControllerMediator implements EventCollector {
	
	Game game;
	Board board;
	int x1, y1, x2, y2;
	boolean gameStatus;
	
	public ControllerMediator(Game g){
		game = g;
		board = g.getBoard();
		x1 = y1 = x2 = y2 = -1;
		gameStatus = true;
	}
	
	/**
	 * Interprète un clic de souris sur un case du plateau :
	 * 	- En phase de placement, le joueur courant place un pinguoin.
	 * 	- En phase de déplacement, si aucun pingouin n'est sélectionné, on essaye
	 * 		de le selectionner
	 * 	- En phase de déplacement, si un pingouin est déjà sélectionné, on essaye
	 * 		de faire le déplacement.
	 */
	@Override
	public void mouseClick(int l, int c){
		boolean ret = false;

		if(!gameStatus){
			return;
		}
		
		if(x1 < 0){ /* Choix de la 1è case.. */
			x1 = c;
			y1 = l;
			if(game.placePhase()){ /* Si on est en placement, c'est la seule qu'on veut */
				try {
					if(ret = game.placePenguin(x1, y1)){
						if(game.movePhase()) game.setErr(Game.START_MOVE);
					}
				} catch(Exception e){
					// A compléter.
				} finally {
					x1 = y1 = -1;
					errorGestion();
				}
			} else {
				/* Sinon, on sélectionne la case : on vérifie qu'il y a un pingouin
				 * du joueur courant
				 */
				if(game.exists(x1, y1)){
					if(game.occupied(x1, y1)){
						if(!game.hasPenguinGoodOwning(game.getCurrentPlayer(), x1, y1)){
							x1 = y1 = -1;	/* Il n'y a pas de pingouin du joueur courant
												sur cette case. */
						}
					} else {
						x1 = y1 = -1;
					}
				} else {
					x1 = y1 = -1;
				}
				errorGestion();
			}
		} else { /* Choix de la case cible */
			x2 = c;
			y2 = l;
			try {
				if(x1 == x2 && y1 == y2){
					x1 = x2 = y1 = y2 = -1;
					game.setErr(Game.SAME_TARGET);
				} else {
					if(ret = game.movePenguin(x1, y1, x2, y2)){
						game.nextPlayer();
					}
				}
			} catch(Exception e) {
				
			} finally {
				x1 = y1 = x2 = y2 = -1;
				errorGestion();
			}
		}
		
		if (game.movePhase()) {
            if (!startTurn()) {
                game.endGame();
                game.setErr(Game.GAME_END);
                gameStatus = false;
                return;
            }
        } else {
            if (game.getCurrentPlayer().isAI()) {
                startAITurn();
            }
        }
	}

	@Override
	public void addUI(UserInterface ui) {

	}

	@Override
	public void timedAction() {
		
	}
	
	public void errorGestion() {
		if(game.placePhase()) {
			switch(game.error) {
			case 0: // game.GOOD_PLACE
				break;
			case 1: // game.ONLY_ONE_FISH
				break;
			case 2: // game.ALREADY_OCCUPY
				break;
			default:
				break;
			}
		}
		else if(game.movePhase()) {
			switch(game.error) {
			case 0: // game.GOOD_TRAVEL
				break;
			case 1: // game.PENGUIN_IN_TRAVEL
				break;
			case 2: // game.HOLE_IN_TRAVEL
				break;
			case 3: // game.TRAVEL_NOT_ALIGNED
				break;
			case 4: // game.GOOD_PENGUIN
				break;
			case 5: // game.WRONG_PENGUIN
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Lis une coordonnée entrée au clavier et l'interprête.
	 * @param val Entier lu au clavier.
	 * @return Vrai si le dernier mouvement est correct, Faux sinon.
	 * @throws Exception
	 */
	public boolean keyInput(int val) throws Exception{
		boolean ret = false;
		
		if(val < 0){
			System.out.println("Coordonnée négative. Erreur.");
		} else {
			if(x1 < 0)x1 = val;
			else if (y1 < 0){
				y1 = val;
				if(game.placePhase()){
					try {
						ret = game.placePenguin(x1, y1);
					} catch (Exception e) {
						throw(e);
					} finally {
						x1 = y1 = -1;
					}
				}
			} else if (x2 < 0) x2 = val;
			else {
				y2 = val;
				try { 
					ret = move(x1, y1, x2, y2);
				} catch(Exception e){
					throw(e);
				} finally {	//On s'assure de bien réinitialiser les coordonnées en cas d'erreur.
					x1 = y1 = x2 = y2 = -1;
				}
			}
			
		}
		return ret;
	}
		
	/**
	 * Effectue le mouvement d'un pingouin d'une tuile à une autre.
	 * Si réussi, passe au joueur suivant.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée y de la tuile d'arrivée.
	 * @return Vrai (true) si le mouvement a été fait ; faux (false) sinon.
	 */
	public boolean move(int x1, int y1, int x2, int y2){
		if(game.movePenguin(x1, y1, x2, y2)){
			game.nextPlayer();
			return true;
		}
		errorGestion();
		return false;		
	}
	
	/**
	 * Determine si le joueur courant peut encore jouer.
	 * Si oui, alors il joue un tour.
	 * Si non, on retire ses pingouins de la partie et on l'empêche de jouer à nouveau.
	 * @return Vrai si le joueur peut encore jouer, Faux sinon.
	 */
	private boolean stillPlaying(){
		Player currPlayer = game.getCurrentPlayer();
		if(!game.canPlay(currPlayer)){
			game.endPlayer(currPlayer);
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Commence le tour du joueur courant : vérifie s'il peut encore jouer.
	 * Si oui, il peut jouer. Si le joueur est une IA, on lance son tour.
	 * Si non, lui fait finir la partie, puis vérifie qu'il reste des joueurs.
	 * @return Vrai s'il reste des joueurs, Faux sinon.
	 */
	public boolean startTurn(){
		if(!stillPlaying()){
			if(!game.nextPlayer()){
				return false;
			}
			return startTurn();	// On teste aussi le joueur suivant.
		}
		if(game.getCurrentPlayer().isAI()){
			startAITurn();
			game.nextPlayer();
		}
		return true;
	}

	/**
	 * Lance le tour de l'IA.
	 */
	public void startAITurn(){
		game.setErr(Game.AI_STARTS);
		game.getCurrentPlayer().play();
		game.setErr(Game.AI_DONE);
	}
}
