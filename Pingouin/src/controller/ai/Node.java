package controller.ai;

import java.util.ArrayList;

/**
 * Classe de noeud d'un arbre. Le noeud stocke une valeur de type E
 * et sa liste de fils.
 * @author Vincent
 * @author Yoann
 * @param <E> Type des éléments du noeud.
 */
public class Node<E> {

	private E element;
	private ArrayList<Node<E>> nodes;
	
	/**
	 * Construit un noeud de valeur elm.
	 * @param elm Valeur du noeud.
	 */
	public Node(E elm) {
		element = elm;
		nodes = new ArrayList<Node<E>>();
	}
	
	/**
	 * Renvoie l'élément du noeud.
	 * @return Element du noeud.
	 */
	public E getElement() {
		return element;
	}
	
	/**
	 * Renvoie la liste des fils du noeud.
	 * @return Liste des fils du noeud
	 */
	public ArrayList<Node<E>> nodes(){
		return nodes;
	}
	
	/**
	 * Ajoute l'élément à la liste des fils.
	 * @param element Valeur de l'élément à ajouter.
	 */
	public void addNode(E element) {
		nodes.add(new Node<E>(element));
	}
	
	/**
	 * Ajoute un noeud à la liste des fils.
	 * @param element Noeud à ajouter.
	 */
	public void addNode(Node<E> element){
		nodes.add(element);
	}
	
	/* Test si deux noeuds sont égaux c'est à dire si ils ont le même élément */
	/**
	 * Teste si deux noeuds ont le même élément. S'en remet à la methode equals
	 * fournie par le type E.
	 * @param n 2è noeud à tester.
	 * @return Vrai si les noeuds ont le même élément, Faux sinon.
	 */
	public boolean equals(Node<E> n) {
		return (element.equals(n.element));
	}
	
	/* Renvoie l'élément du noeud en String */
	/**
	 * Renvoie l'élément du noeud en String. S'en remet à la méthode toString
	 * du type E
	 */
	public String toString() {
		return element.toString();
	}
	
}
