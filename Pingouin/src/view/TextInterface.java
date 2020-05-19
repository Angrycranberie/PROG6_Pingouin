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
			
			if(!g.placePhase()){
				if(!c.startTurn()){
					s.close();
					g.endGame();
					return;
				}
			} else {
				if(g.getCurrentPlayer().isAI()){
					c.startAITurn();
				}
			}
			g.getBoard().printBoard(2);
			if(!g.getCurrentPlayer().isAI()){
				System.out.print("Commande (" + g.getCurrentPlayer().getName() + ") > ");
				try {
					c.keyInput(s.nextInt());
				} catch(ArrayIndexOutOfBoundsException e){
					System.out.print("Erreur : Coordonnées incorrecte. Rejouez.");
				} catch(NullPointerException e){
					System.out.print("Erreur : Cette case est vide. Rejouez.");
				} catch(Exception e){
					System.out.print("Erreur inconnue : " + e.getLocalizedMessage() + ". Rejouez.");
				}
			}
			System.out.println();
		}
	}
}
