package controller.ai;

import java.util.ArrayList;

import controller.ai.Node;

/**
 * Classe d'arbre de branchement variable et d'élément de 
 * classe E (défini à sa construction).
 * @author Vincent
 * @author Yoann
 * @param <E> Type des éléments de l'arbre.
 */
public class Tree<E> {
	
	private Node<E> root;
	
	/**
	 * Constructeur. Crée un arbre dont la racine est element.
	 * @param element Racine de l'arbre.
	 */
	public Tree(E element) {
		root = new Node<E>(element);
	}
	
	/**
	 * Renvoie toutes les occurences de element dans l'arbre.
	 * @param element Element à chercher.
	 * @return Liste de noeuds de type E dont la valeur est element.
	 */
	public ArrayList<Node<E>> occurence(E element){
		return occurence(element, root);
	}
	
	/**
	 * Methode récursive calculant les occurences de element dans l'arbre n.
	 * Appelée par occurence(E).
	 * @param element Element à chercher.
	 * @param n Racine de l'arbre de recherche.
	 * @return Liste de noeuds de type E dont la valeur est element.
	 */
	private ArrayList<Node<E>> occurence(E element, Node<E> n){
		ArrayList<Node<E>> current = new ArrayList<Node<E>>();
		
		if(n.getElement() == element) current.add(n);
		
		for(int i = 0; i < current.size() ; i++) current.addAll(occurence(element, (Node<E>) n.nodes().get(i)));
		
		return current;
	}
	
	/**
	 * Renvoie le noeud racine de l'arbre.
	 * @return Racine de l'arbre.
	 */
	public Node<E> root(){
		return root;
	}
	
	
}
