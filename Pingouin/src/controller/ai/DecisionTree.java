package controller.ai;

import java.util.Hashtable;

import Arbitre.Board;
import Arbitre.History;
import Arbitre.Move;

public class DecisionTree {

	Tree<ConfigNode> tree;
	Hashtable<Integer, Node<ConfigNode>> hashVec;
	/* La table associe une configuration à un noeud de l'arbre.
	 * On utilisera la methode hashCode de la classe Vector pour contourner
	 * le problème du stockage d'objet dans une hashtable.	
	*/
	private Board game;
	private History hist;
	
	
	/* Construit l'arbre de decision associé à une IA.
	 * start vaut true si cette IA commence la partie, false sinon.
	 */
 	public DecisionTree(Board Game, boolean start){
		this.game = Game.clone();
		hashVec = new Hashtable<Integer, Node<ConfigNode>> ();
		hist = new History(game.get_height(), game.get_width());
		
		initTree(start);
		System.out.println("Terminé - " + hashVec.values().size());
	}
	
 	/* Manipulation de la table des noeuds */
 	public Node<ConfigNode> getNode(ConfigVector key){
 		return hashVec.get(key.hashCode());
 	}
 	
 	public Node<ConfigNode> getNode(Integer key){
 		return hashVec.get(key);
 	}
 	
 	/* Initialise les appels, selon le joueur qui commence. */
	private void initTree(boolean start){
		
		if(start){
			tree = new Tree<ConfigNode>(nodeSelf().getElement());
		} else {
			tree = new Tree<ConfigNode>(nodeOppo().getElement());
		}
	}
	
	/* Construit un noeud de l'arbre associé au plateau courant game, 
	 * et considérant que l'IA a le tour en cours.
	 */
	private Node<ConfigNode> nodeSelf(){

		ConfigVector vect = new ConfigVector(game, 1);

		/* On vérifie si l'on a déjà calculé ce noeud */
		if(hashVec.containsKey(vect.hashCode())){
			/* Le noeud existe déjà, on le renvoie. */
			return getNode(vect);
		}
		
		/* Le noeud n'existe pas. On le calcule. */
		boolean win = false;
		ConfigNode elm = new ConfigNode(win, vect);
		tree = new Tree<ConfigNode>(elm);
		Node<ConfigNode> root = tree.root();
		Node<ConfigNode> fils;		
		
		hashVec.put(vect.hashCode(), tree.root());
		/* L'adversaire vient de jouer le coup mortel. On a donc gagné. */
		if(game.end()){
			elm.ModifWin(!win);
			return root;
		}
		
		/* On parcours tous les fils possibles de ce noeud et on regarde leur valeur */
		for(int i = 0 ; i < game.get_height() ; i++){
			for(int j = 0 ; j < game.get_width() ; j++){
				
				if(game.play(i, j)) {
					hist.addMove(new Move(i, j));
					fils = nodeOppo();
					root.addNode(fils);
					win = win || fils.getElement().win();
					game = hist.undo(hist.past.size()-1, game);
				}
			}
		}
		
		elm.ModifWin(win);
		
		return root;
	}
	
	/* Construit un noeud de l'arbre associé au plateau courant game, 
	 * et considérant que l'adversaire a le tour en cours.
	 */
	private Node<ConfigNode> nodeOppo(){
		ConfigVector vect = new ConfigVector(game, 0);

		/* On vérifie si l'on a déjà calculé ce noeud */
		if(hashVec.containsKey(vect.hashCode())){
			/* Le noeud existe déjà, on le renvoie. */
			return getNode(vect);
		}
		
		/* Le noeud n'existe pas. On le calcule. */
		boolean win = true;
		ConfigNode elm = new ConfigNode(win, vect);
		tree = new Tree<ConfigNode>(elm);
		Node<ConfigNode> root = tree.root();
		Node<ConfigNode> fils;		
		
		hashVec.put(vect.hashCode(), tree.root());
		/* On vient de joue)r le coup mortel. On a donc gagné. */
		if(game.end()){
			elm.ModifWin(!win);
			return root;
		}
		/* On parcours tous les fils possibles de ce noeud et on regarde leur valeur */
		for(int i = 0 ; i < game.get_height() ; i++){
			for(int j = 0 ; j < game.get_width() ; j++){
				
				if(game.play(i, j)) {
					hist.addMove(new Move(i, j));
					fils = nodeSelf();
					root.addNode(fils);
					win = win && fils.getElement().win();
					game = hist.undo(hist.past.size()-1, game);
				}
			}
		}
		
		elm.ModifWin(win);
		
		return root;
	}
}