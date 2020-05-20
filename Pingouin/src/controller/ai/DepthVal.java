package controller.ai;

import model.Board;
import model.Game;
import model.Tile;

/**
 * Classe calculant la profondeur à prendre pour l'évaluation Minmax.
 * @author yeauhant
 *
 */
public class DepthVal {
	
	/**
	 * Calcule la profondeur à prendre pour l'évaluation Minmax.
	 * Elle dépend du nombre de cases déjà prises et du
	 * nombre de pingouins (donc de joueurs) encore présents.
	 * Elle pourrait aussi dépendre de la taille du plateau,
	 * si ce paramètre était configurable.
	 * @param g Partie sur laquelle on appelle le Minmax.
	 * @return Profondeur à parcourir.
	 */
	public static int depthCalc(Game g){
		int val = 2;
		int removedTile = 0;
		int pingAmount = 0;
		Tile currT;
		
		for(int i = 0 ; i < Board.WIDTH ; i++){
			for(int j = 0 ; j < Board.LENGTH ; j++){
				currT = g.getBoard().getTile(i, j);
				if(currT == null) removedTile++;
				else if (currT.occupied()) pingAmount++;
			}
		}
		
		removedTile -= 4;	// Les 4 cases toujours à null.
		
		if(pingAmount <= 4) val += 2;
		if(removedTile >= 20) val += 1;
		if(removedTile >= 30) val += 2;
		if(removedTile >= 40) val += 2;
		
		
		return val;
	}
}
