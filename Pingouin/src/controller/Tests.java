package controller;

import java.awt.Color;

import model.Game;

public class Tests {

	/**
	 * Classe de test du contrôleur.
	 * @param args Aucun argument n'est attendu.
	 */
	public static void main(String[] args) {
		Player p1 = new Player(4, new Color(240, 46, 0), "Joueur 1");
		Player p2 = new Player(4, new Color(46, 240, 0), "Joueur 2");
		Game g = new Game(2, p1, p2, null, null);
		
		ControlerMediator controler = new ControlerMediator(g);
		
		/* Test de placement de pingouins.
		 * Doit générer deux erreurs "Case occupée" et trois erreurs "Déjà placés".
		 */
		System.out.println(controler.placePinguin(0, 0));
		System.out.println(controler.placePinguin(0, 0));
		System.out.println(controler.placePinguin(0, 1));
		System.out.println(controler.placePinguin(0, 2));
		System.out.println(controler.placePinguin(0, 3));
		System.out.println(controler.placePinguin(0, 0));
		System.out.println(controler.placePinguin(0, 4));
		System.out.println(controler.placePinguin(0, 5));
		System.out.println(controler.placePinguin(0, 6));
		System.out.println(controler.placePinguin(0, 7));
		System.out.println(controler.placePinguin(1, 2));
		System.out.println(controler.placePinguin(2, 3));
		System.out.println(controler.placePinguin(3, 4));
		
		g.getBoard().printBoard(1);
		


		p1 = new Player(4, new Color(240, 46, 0), "Joueur 1");
		p2 = new Player(4, new Color(46, 240, 0), "Joueur 2");
		g = new Game(2, p1, p2, null, null);
		controler = new ControlerMediator(g);
		
		/* Tests de placements extrêmes */
		System.out.println(controler.placePinguin(6, 2));
		System.out.println(controler.placePinguin(7, 3));
		System.out.println(controler.placePinguin(6, 4));
		System.out.println(controler.placePinguin(7, 5));
		System.out.println(controler.placePinguin(6, 6));
		System.out.println(controler.placePinguin(7, 7));
		System.out.println(controler.placePinguin(7, 1));
		System.out.println(controler.placePinguin(6, 0));
		
		g.getBoard().printBoard(1);
		

		p1 = new Player(4, new Color(240, 46, 0), "Joueur 1");
		p2 = new Player(4, new Color(46, 240, 0), "Joueur 2");
		g = new Game(2, p1, p2, null, null);
		controler = new ControlerMediator(g);
		
		System.out.println(controler.placePinguin(7, 7));
		System.out.println(controler.placePinguin(6, 7));
		System.out.println(controler.placePinguin(5, 7));
		System.out.println(controler.placePinguin(4, 7));
		System.out.println(controler.placePinguin(3, 7));
		System.out.println(controler.placePinguin(2, 7));
		System.out.println(controler.placePinguin(1, 7));
		System.out.println(controler.placePinguin(0, 7));
		
		g.getBoard().printBoard(1);
		
		System.out.println(controler.move(7, 7, 3, 0));
		System.out.println(controler.move(3, 0, 1, 0));
		g.getBoard().printBoard(1);
		
	}

}
