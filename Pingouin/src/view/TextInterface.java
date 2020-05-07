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
					g.endGame();
					return;
				}
			}
			g.getBoard().printBoard(1);
			System.out.println("Commande > ");
			c.keyInput(s.nextInt());
		}
	}
}
