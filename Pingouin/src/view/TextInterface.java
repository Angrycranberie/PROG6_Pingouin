package view;

import java.util.Scanner;

import controller.ControlerMediator;
import model.Game;

public class TextInterface {
	Game game;
	ControlerMediator controler;
	
	TextInterface(Game g, ControlerMediator c){
		game = g;
		controler = c;
	}
	
	public static void start(Game g, ControlerMediator c){
		TextInterface vue = new TextInterface (g, c);
		Scanner s = new Scanner(System.in);
		while(true){
			if(c.getToPlace() == 0){
				if(!c.startTurn()){
					s.close();
					g.endGame();
					return;
				}
			}
			g.getBoard().printBoard(2);
			System.out.print("Commande > ");
			try {
				c.keyInput(s.nextInt());
			} catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Erreur : Coordonnées incorrecte. Rejouez.");
			} catch(NullPointerException e){
				System.out.println("Erreur : Cette case est vide. Rejouez.");
			} catch(Exception e){
				System.out.println("Erreur inconnue : " + e.getLocalizedMessage() + ". Rejouez.");
			}
			System.out.println();
		}
	}
}
