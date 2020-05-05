package controller.ai;

import java.util.Random;
import java.util.ArrayList;

import Arbitre.Board;
import Arbitre.History;
import Arbitre.Move;
import Arbitre.Player;
import ai.Node;
import ai.ConfigVector;
import ai.ConfigNode;

/*
 * Classe AISmart
 * 
 * 
 */
public class AISmart implements Player {
	Board game;
	Random r;
	DecisionTree decTree;
	
	/* Crée une IA et son arbre associé.
	 * Le booleen start indique si elle joue en premier (true) ou non (false)
	 */
	public AISmart(Board game, boolean start) {
		this.game = game;
		r = new Random();
		decTree = new DecisionTree(game, start);
	}

	@Override
	public void takeTurn(History h, Board gameboard) {
		
		int l = game.get_height(), c = 0;
		boolean ok = false;
		ConfigVector init = new ConfigVector(game, 1);
		ConfigVector next;
		
		Node<ConfigNode> current = decTree.getNode(init);
		ArrayList<Node<ConfigNode>> winners = new ArrayList<Node<ConfigNode>>();

		for(int i = 0; i < current.nodes().size() ; i++) {
			// si le coup est gagnant, je l'ajoute dans la liste des coups susceptibles d'être joués
			if(current.nodes().get(i).getElement().win()) {
				ok = true;
				winners.add((Node<ConfigNode>) current.nodes().get(i));
			}
		}
		
		if(!ok){
			System.err.println("Erreur - Aucun coup gagnant n'a été trouvé");
			System.exit(1);
		}
		
		// si j'ai plusieurs coups gagnants, je choisis au hasard le coup à jouer
		if(winners.size() > 1) next = winners.get(r.nextInt(winners.size())).getElement().config();
		else next = winners.get(0).getElement().config();
		
		/* parcours de la partie commune aux deux vecteurs */
		boolean shared = true;
		int i = 1;
		while(shared && i < next.vect.size()) {
			if(shared && next.vect.get(i) != init.vect.get(i)) shared = false;
			else {
				/* si on a 0, on avance d'une colonne ; si on a 1, on monte d'une ligne */
				if(next.vect.get(i) == 0) c++;
				else l--;
				i++;
			}
		}
		
		/* on passe les 0 dans le vecteur next */
		while(i < next.vect.size() && next.vect.get(i) == 0) {
			c++;
			i++;
		}
		
		/* parcours du bloc de 1 pour trouver la case mangée */
		while(i < next.vect.size() && next.vect.get(i) == 1) {
			l--;
			i++;
		}
		
		if(game.play(l, c)){
			h.addMove(new Move(l, c));
			System.out.println("Coup jou� en (" + l + ", " + c +")");
		}
		
		return;
	}


	@Override
	public boolean move() {
		// TODO Auto-generated method stub
		return false;
	}

}
