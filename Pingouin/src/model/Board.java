package model;
import java.util.Random;
import java.util.Scanner;

public class Board {
	private Tile tab[][];
	
	public Board() {
		tab = new Tile[8][8];
		shuffle();
	}
	
	/**
	 * place un pingouin sur la tuile donnée
	 * @param x coord x de la tuile
	 * @param y coord y de la tuile
	 */
	public void takePosition(int x, int y) {
		tab[y][x].take();
	}
	
	/**
	 * libère une tuile d'un pingouin
	 * @param x coord x de la tuile
	 * @param y coord y de la tuile
	 */
	public void quitPosition(int x, int y) {
		tab[y][x].quit();
	}
	
	/**
	 * retire une tuile du plateau
	 * @param x coord x de la tuile
	 * @param y coord y de la tuile
	 * @return tuile enlevée
	 */
	public Tile removeTile(int x, int y) {
		Tile res = tab[y][x];
		tab[y][x] = null;
		return res;
	}
	
	/**
	 * distribue aléatoirement les pièces sur le plateau de jeu
	 */
	public void shuffle() {
		int trois = 10;
		int deux = 20;
		int un = 30;
		int tirage;
		int value;
		Random r = new Random();
		for(int y = 0 ; y < 8 ; y++) {
			for(int x = 0 ; x < 8 ; x++) {
				if(y % 2 != 0 || x != 7) {
					tirage = r.nextInt(trois + deux + un) + 1;  // 1 <= tirage <= un + deux + trois 
					if(tirage <= un) { // 1 <= tirage <= un  ,  un valeurs possibles
						value = 1;
						un--;
					}
					else if(tirage <= un + deux) { // un < tirage <= un + deux  ,  deux valeurs possibles
						value = 2;
						deux--;
					}
					else { // un + deux < tirage <= un + deux + trois   ,   trois valeurs possibles
						value = 3;
						trois--;
					}
					tab[y][x] = new Tile(value);
				}
			}
		}
	}
	
	/**
	 * test la fonction shuffle, si elle a bien remis toute les tuiles et qu'il y a le bon nombre de tuile de chaque valeur
	 * @return indique si le test a réussi
	 */
	private boolean test_shuffle() {
		shuffle();
		int trois = 0;
		int deux = 0;
		int un = 0;
		for(int y = 0 ; y < 8 ; y++) {
			for(int x = 0 ; x < 8 ; x++) {
				if(tab[y][x] != null) {
					if(tab[y][x].value() == 1) {
						un++;
					}
					else if(tab[y][x].value() == 2) {
						deux++;
					}
					else if(tab[y][x].value() == 3) {
						trois++;
					}
					else {
						System.out.println(tab[y][x].value() + " n'est pas une valeur valide, coord : x " + x +" y " + y);
						return false;
					}
				}
			}
		}
		return trois == 10 && deux == 20 && un == 30;
	}
	
	/**
	 * vérifie si deux tuiles sont alignées
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 * @return 0 pas alignées ou au moins une tuile n'est pas présente, 1 alignées horizontalement , 2 en slash , 3 en antislash
	 */
	public int align(int x1 , int y1 , int x2 , int y2) {
		boolean trueTile = tab[y1][x1] != null && tab[y2][x2] != null;
		boolean horizontal = y1 == y2;
		boolean slash;
		if(y1 < y2) {
			if(y1 % 2 == 0) {
				slash = x1 + ((y1 - y2) / 2) == x2;
			}
			else {
				slash = x1 + ((y1 + 1 - y2) / 2) - 1 == x2;
			}
		}
		else {
			if(y2 % 2 == 0) {
				slash = x2 + ((y2 - y1) / 2) == x1;
			}
			else {
				slash = x2 + ((y2 + 1 - y1) / 2) - 1 == x1;
			}
		}
		boolean antiSlash;
		if(y1 < y2) {
			if(y1 % 2 == 0) {
				antiSlash = x1 + ((y2 - y1 - 1) / 2) + 1 == x2;
			}
			else {
				antiSlash = x1 + ((y2  - y1) / 2)  == x2;
			}
		}
		else {
			if(y2 % 2 == 0) {
				antiSlash = x2 + ((y1 - y2 - 1) / 2) + 1== x1;
			}
			else {
				antiSlash = x2 + ((y1 - y2) / 2)  == x1;
			}
		}
		if(trueTile && (horizontal || slash || antiSlash)) {
			if(horizontal) {
				return 1;
			}
			else if(slash) {
				return 2;
			}
			else{
				return 3;
			}
		}
		else{
			return 0;
		}
	}
	
	/**
	 * teste la fonction align
	 * @param print indique si la fonction doit afficher les tests ou non
	 * @return indique si le test a réussi
	 */
	private boolean test_align(boolean print) {
		boolean res = true;
		// test horizontaux
		res = res && (align(0,0,6,0) != 0);
		if(print) {
			printTest(1,0,0,6,0);
		}
		if(res == false) {
			System.out.println("(0,0,6,0) faux");
		}
		
		res = res && align(0,0,1,0) != 0;
		if(print) {
			printTest(1,0,0,1,0);
		}
		if(res == false) {
			System.out.println("(0,0,1,0) faux");
		}
		
		res = res && align(3,3,0,3) != 0;
		if(print) {
			printTest(1,3,3,0,3);
		}
		if(res == false) {
			System.out.println("(3,3,0,3) faux");
		}
		
		res = res && align(7,3,0,3) != 0;
		if(print) {
			printTest(1,7,3,0,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,0,3) faux");
		}
		
		res = res && align(7,3,6,3) != 0;
		if(print) {
			printTest(1,7,3,6,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,6,3) faux");
		}
		
		// test slash
		res = res && align(0,0,1,1) != 0;
		if(print) {
			printTest(1,0,0,1,1);
		}
		if(res == false) {
			System.out.println("(0,0,1,1) faux");
		}
		
		res = res && align(0,0,4,7) != 0;
		if(print) {
			printTest(1,0,0,4,7);
		}
		if(res == false) {
			System.out.println("(0,0,4,7) faux");
		}
		
		res = res && align(6,7,5,6) != 0;
		if(print) {
			printTest(1,6,7,5,6);
		}
		if(res == false) {
			System.out.println("(6,7,5,6) faux");
		}
		
		res = res && align(6,7,2,0) != 0;
		if(print) {
			printTest(1,6,7,2,0);
		}
		if(res == false) {
			System.out.println("(6,7,2,0) faux");
		}
		
		res = res && align(6,7,4,3) != 0;
		if(print) {
			printTest(1,6,7,4,3);
		}
		if(res == false) {
			System.out.println("(6,7,4,3) faux");
		}
		
		// test antislash
		res = res && align(0,0,0,1) != 0;
		if(print) {
			printTest(1,0,0,0,1);
		}
		if(res == false) {
			System.out.println("(0,0,0,1) faux");
		}
		
		res = res && align(6,0,6,1) != 0;
		if(print) {
			printTest(1,6,0,6,1);
		}
		if(res == false) {
			System.out.println("(6,0,6,1) faux");
		}
		
		res = res && align(6,0,3,7) != 0;
		if(print) {
			printTest(1,6,0,3,7);
		}
		if(res == false) {
			System.out.println("(6,0,3,7) faux");
		}
		
		res = res && align(0,7,3,0) != 0;
		if(print) {
			printTest(1,0,7,3,0);
		}
		if(res == false) {
			System.out.println("(0,7,3,0) faux");
		}
		
		res = res && align(0,7,0,6) != 0;
		if(print) {
			printTest(1,0,7,0,6);
		}
		if(res == false) {
			System.out.println("(0,7,0,6) faux");
		}
		
		res = res && align(2,7,4,3) != 0;
		if(print) {
			printTest(1,2,7,4,3);
		}
		if(res == false) {
			System.out.println("(2,7,4,3) faux");
		}
		
		// test non align�
		res = res && align(0,0,2,1) == 0;
		if(print) {
			printTest(1,0,0,2,1);
		}
		if(res == false) {
			System.out.println("(0,0,2,1) faux");
		}
		
		res = res && align(0,0,0,2) == 0;
		if(print) {
			printTest(1,0,0,0,2);
		}
		if(res == false) {
			System.out.println("(0,0,0,2) faux");
		}
		
		res = res && align(7,7,7,5) == 0;
		if(print) {
			printTest(1,7,7,7,5);
		}
		if(res == false) {
			System.out.println("(7,7,7,5) faux");
		}
		
		res = res && align(1,7,1,4) == 0;
		if(print) {
			printTest(1,1,7,1,4);
		}
		if(res == false) {
			System.out.println("(1,7,1,4) faux");
		}
		
		res = res && align(2,4,7,5) == 0;
		if(print) {
			printTest(1,2,4,7,5);
		}
		if(res == false) {
			System.out.println("(2,4,7,5) faux");
		}
		
		return res;
	}
	
	/**
	 * indique si un déplacement entre deux tuiles est valide ou non
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 * @return résultat du test
	 */
	public boolean legitTravel(int x1, int y1 , int x2 , int y2) {
		int resAlign = align(x1,y1,x2,y2);
		if(resAlign == 1) { // y1 == y2
			int y = y1;
			if(x1 < x2) {
				for(int x = x1 + 1 ; x <= x2 ; x++) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
				}
			}
			else {
				for(int x = x1 - 1 ; x >= x2 ; x--) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
				}
			}
			return true;
		}
		else if(resAlign == 2) { // alignés en /
			int x;
			if(y1 < y2) {
				x = x1;
				if(y1 % 2 == 1) {
					x--;
				}
				for(int y = y1 + 1; y <= y2 ; y++) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
					if(y % 2 == 1) {
						x--;
					}
				}
			}
			else {
				x = x1;
				if(y1 % 2 == 0) {
					x++;
				}
				for(int y = y1 - 1; y >= y2 ; y--) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
					if(y % 2 == 0) {
						x++;
					}
				}
			}
			return true;
		}
		else if(resAlign == 3){ // alignés en \
			int x;
			if(y1 < y2) {
				x = x1;
				if(y1 % 2 == 0) {
					x++;
				}
				for(int y = y1 + 1; y <= y2 ; y++) { // parcours de toute les cases entre les deux tuiles
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
					if(y % 2 == 0) {
						x++;
					}
				}
			}
			else {
				x = x1;
				if(y1 % 2 == 1) {
					x--;
				}
				for(int y = y1 - 1; y >= y2 ; y--) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].isOccupied()) {
						return false;
					}
					if(y % 2 == 1) {
						x--;
					}
				}
			}
			return true;			
		}
		else {
			return false;
		}
	}
	
	/**
	 * teste la fonction legitTravel
	 * @param print indique si la fonction doit afficher les tests ou non
	 * @return résultat du test
	 */
	private boolean test_legitTravel(boolean print) {
		boolean res = true;	
		shuffle();
		takePosition(0,1);
		takePosition(0,3);
		takePosition(3,2);
		takePosition(5,3);
		takePosition(3,1);
		
		// test horizontaux
		res = res && legitTravel(0,0,6,0);
		if(print) {
			System.out.println(legitTravel(0,0,6,0));
			printTest(2,0,0,6,0);
		}
		if(res == false) {
			System.out.println("(0,0,6,0) faux");
		}
		
		res = res && legitTravel(0,0,1,0);
		if(print) {
			System.out.println(legitTravel(0,0,1,0));
			printTest(2,0,0,1,0);
		}
		if(res == false) {
			System.out.println("(0,0,1,0) faux");
		}
		
		res = res && !legitTravel(3,3,0,3);
		if(print) {
			System.out.println(legitTravel(3,3,0,3));
			printTest(2,3,3,0,3);
		}
		if(res == false) {
			System.out.println("(3,3,0,3) faux");
		}
		
		res = res && !legitTravel(7,3,0,3);
		if(print) {
			System.out.println(legitTravel(7,3,0,3));
			printTest(2,7,3,0,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,0,3) faux");
		}
		
		res = res && legitTravel(7,3,6,3);
		if(print) {
			System.out.println(legitTravel(7,3,6,3));
			printTest(2,7,3,6,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,6,3) faux");
		}
		
		// test slash
		res = res && legitTravel(0,0,1,1);
		if(print) {
			System.out.println(legitTravel(0,0,1,1));
			printTest(2,0,0,1,1);
		}
		if(res == false) {
			System.out.println("(0,0,1,1) faux");
		}
		
		res = res && legitTravel(0,0,4,7);
		if(print) {
			System.out.println(legitTravel(0,0,4,7));
			printTest(2,0,0,4,7);
		}
		if(res == false) {
			System.out.println("(0,0,4,7) faux");
		}
		
		res = res && legitTravel(6,7,5,6);
		if(print) {
			System.out.println(legitTravel(6,7,5,6));
			printTest(2,6,7,5,6);
		}
		if(res == false) {
			System.out.println("(6,7,5,6) faux");
		}
		
		res = res && !legitTravel(6,7,2,0);
		if(print) {
			System.out.println(legitTravel(6,7,2,0));
			printTest(2,6,7,2,0);
		}
		if(res == false) {
			System.out.println("(6,7,2,0) faux");
		}
		
		res = res && legitTravel(6,7,4,3);
		if(print) {
			System.out.println(legitTravel(6,7,4,3));
			printTest(2,6,7,4,3);
		}
		if(res == false) {
			System.out.println("(6,7,4,3) faux");
		}
		
		// test antislash
		res = res && !legitTravel(0,0,0,1);
		if(print) {
			System.out.println(legitTravel(0,0,0,1));
			printTest(2,0,0,0,1);
		}
		if(res == false) {
			System.out.println("(0,0,0,1) faux");
		}
		
		res = res && legitTravel(6,0,6,1);
		if(print) {
			System.out.println(legitTravel(6,0,6,1));
			printTest(2,6,0,6,1);
		}
		if(res == false) {
			System.out.println("(6,0,6,1) faux");
		}
		
		res = res && !legitTravel(6,0,3,7);
		if(print) {
			System.out.println(legitTravel(6,0,3,7));
			printTest(2,6,0,3,7);
		}
		if(res == false) {
			System.out.println("(6,0,3,7) faux");
		}
		
		res = res && !legitTravel(0,7,3,1);
		if(print) {
			System.out.println(legitTravel(0,7,3,1));
			printTest(2,0,7,3,1);
		}
		if(res == false) {
			System.out.println("(0,7,3,1) faux");
		}
		
		res = res && legitTravel(0,7,0,6);
		if(print) {
			System.out.println(legitTravel(0,7,0,6));
			printTest(2,0,7,0,6);
		}
		if(res == false) {
			System.out.println("(0,7,0,6) faux");
		}
		
		res = res && legitTravel(2,7,4,3);
		if(print) {
			System.out.println(legitTravel(2,7,4,3));
			printTest(2,2,7,4,3);
		}
		if(res == false) {
			System.out.println("(2,7,4,3) faux");
		}
		
		return res;
	}

	/**
	 * effectue le déplacement du pingouin de la tuile 1 vers la tuile 2 si il est valide
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 * @return tuile 1 si le déplacement s'est fait, null sinon
	 */
	public Tile makeMove(int x1, int y1 , int x2 , int y2) {
		if(legitTravel(x1,y1,x2,y2) && tab[y1][x1].isOccupied()) {
			takePosition(x2,y2);
			return removeTile(x1,y1);
		}
		return null;
	}
	
	/**
	 * teste la fonction makeMove
	 * @param print indique si la fonction doit afficher les tests ou non
	 * @return résultat du test
	 */
	private boolean test_makeMove(boolean print) {
		boolean res = true;	
		boolean r;
		shuffle();
		takePosition(0,0);
		takePosition(4,1);
		takePosition(2,2);
		takePosition(1,3);
		takePosition(0,6);
		takePosition(2,6);
		takePosition(3,6);
		
		
		// test horizontaux
		res = (r = makeMove(1,3,5,3) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,1,3,5,3);
		}
		if(r == false) {
			System.out.println("(1,3,5,3) faux");
		}
		
		res = (r = makeMove(2,6,0,2) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,2,6,0,2);
		}
		if(r == false) {
			System.out.println("(2,6,0,2) faux");
		}
		
		res = (r = makeMove(2,6,1,4) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,2,6,1,4);
		}
		if(r == false) {
			System.out.println("(2,6,1,4) faux");
		}
		
		res = (r = makeMove(3,6,5,2) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,3,6,5,2);
		}
		if(r == false) {
			System.out.println("(3,6,5,2) faux");
		}
		
		res = (r = makeMove(3,6,4,4) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,3,6,4,4);
		}
		if(r == false) {
			System.out.println("(3,6,4,4) faux");
		}
		
		res = (r = makeMove(0,6,1,6) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,0,6,1,6);
		}
		if(r == false) {
			System.out.println("(0,6,1,6) faux");
		}
		
		res = (r = makeMove(1,6,4,6) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,1,6,4,6);
		}
		if(r == false) {
			System.out.println("(1,6,4,6) faux");
		}
		
		res = (r = makeMove(1,6,2,6) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,1,6,2,6);
		}
		if(r == false) {
			System.out.println("(1,6,2,6) faux");
		}
		
		res = (r = makeMove(0,0,5,0) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,0,0,5,0);
		}
		if(r == false) {
			System.out.println("(0,0,5,0) faux");
		}
		
		res = (r = makeMove(4,1,4,0) != null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,4,1,4,0);
		}
		if(r == false) {
			System.out.println("(4,1,4,0) faux");
		}
		
		res = (r = makeMove(0,1,3,0) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,0,1,3,0);
		}
		if(r == false) {
			System.out.println("(0,1,3,0) faux");
		}
		
		res = (r = makeMove(5,7,6,4) == null) && res;
		if(print) {
			System.out.println(r);
			printTest(3,5,7,6,4);
		}
		if(r == false) {
			System.out.println("(5,7,6,4) faux");
		}
		return res;
	}
	
	/**
	 * annule un mouvement d'une tuile 1 vers une tuile 2
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 * @param nbFish nombre de poisson sur la tuile 1
	 */
	public void reverseMove(int x1, int y1 , int x2 , int y2, int nbFish) {
		if(tab[y2][x2].isOccupied() && tab[y1][x1] == null) {
			// on quitte la case d'arrivée
			tab[y2][x2].quit();
			
			// on recréé la case de départ avec le bon nombre de poissons
			tab[y1][x1] = new Tile(nbFish);
			
			// on met le pingouin dessus
			tab[y1][x1].take();
		}
	}
	
	/**
	 * affiche le plateau de jeu
	 * @param option 0 affiche les valeurs des tuiles, 1 affiche les cases occupées et vides
	 */
	public void printBoard(int option) {
		for(int y = 0 ; y < 8 ; y++) {
			if(y % 2 == 0) {
				System.out.print(" ");
			}
			for(int x = 0 ; x < 8 ; x++) {
				if(option == 0) {
					if(tab[y][x] != null) {
						System.out.print(tab[y][x].value() + " ");
					}
					else {
						System.out.print("  ");
					}
				}
				else if(option == 1) {
					if(tab[y][x] == null) {
						System.out.print("O ");
					}
					else if(tab[y][x].isOccupied()) {
						System.out.print("B ");
					}
					else {
						System.out.print(". ");
					}
						
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * affiche le plateau en fonction du test effectué
	 * @param option détermine quel test est effectué : 1 test alignement, 2 déplacement valide , 3 exécution du déplacement
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 */
	public void printTest(int option, int x1, int y1 , int x2, int y2) {
		for(int y = 0 ; y < 8 ; y++) {
			if(y % 2 == 0) {
				System.out.print(" ");
			}
			for(int x = 0 ; x < 8 ; x++) {
				if(option == 1) { // alignement
					if(x == x1 && y == y1) {
						System.out.print("1 ");
					}
					else if(x == x2 && y == y2) {
						System.out.print("2 ");
					}
					else {
						System.out.print(". ");
					}
				}
				else if(option == 2) { // deplacement légal
					if(x == x1 && y == y1) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("X ");
						}
						else{
							System.out.print("@ ");
						}
					}
					else if(x == x2 && y == y2) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("X ");
						}
						else{
							System.out.print("@ ");
						}
					}
					else {
						if(tab[y][x] == null) {
							System.out.print("O ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("B ");
						}
						else{
							System.out.print(". ");
						}
					}
				}
				else if(option == 3){ // déplacement
					if(x == x1 && y == y1) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("X ");
						}
						else{
							System.out.print("@ ");
						}
					}
					else if(x == x2 && y == y2) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("X ");
						}
						else{
							System.out.print("@ ");
						}
					}
					else {
						if(tab[y][x] == null) {
							System.out.print("O ");
						}
						else if(tab[y][x].isOccupied()) {
							System.out.print("B ");
						}
						else{
							System.out.print(". ");
						}
					}
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	

	public int[][] movePossibility(int x1, int y1) {
		// création du tableau à rendre
		int result[][] = new int[60][2];
		int index = 0;
		
		int x,y;
		
		boolean cond = true;
		// coups à l'horizontal partie droit
		y = y1;
		for(x = x1 + 1 ; x < 8 && cond; x++) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
			}
			else {
				cond = false;
			}
		}
		
		// coups en antislash partie basse
		cond = true;
		x = x1;
		if(y1 % 2 == 0) {
			x++;
		}
		for(y = y1 + 1; y < 8 && x < 8 && cond; y++) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
				}
			else {
				cond = false;
			}
			if(y % 2 == 0) {
				x++;
			}
		}
		
		// coups en slash partie basse
		cond = true;
		x = x1;
		if(y1 % 2 == 1) {
			x--;
		}
		for(y = y1 + 1; y < 8 && x >= 0 && cond; y++) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
				}
			else {
				cond = false;
			}
			if(y % 2 == 1) {
				x--;
			}
		}
		
		// coups à l'horizontal partie gauche
		cond = true;
		y = y1;
		for(x = x1 - 1 ; x >= 0  && cond; x--) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
			}
			else {
				cond = false;
			}
		}
		
		// coups en antislash partie haute
		cond = true;
		x = x1;
		if(y1 % 2 == 1) {
			x--;
		}
		for(y = y1 - 1; y >= 0 && x >= 0 && cond; y--) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
			}
			else {
				cond = false;
			}
			if(y % 2 == 1) {
				x--;
			}
		}
		
		// coups en slash partie haute
		cond = true;
		x = x1;
		if(y1 % 2 == 0) {
			x++;
		}
		for(y = y1 - 1; y >= 0 && x < 8 && cond; y--) {
			if(tab[y][x] != null && !tab[y][x].isOccupied()) {
				result[index][0] = x;
				result[index][1] = y;
				index++;
			}
			else {
				cond = false;
			}
			if(y % 2 == 0) {
				x++;
			}
		}
		
		return result;
	}
	
	public boolean test_movePossibility(boolean print) {
		shuffle();
		boolean result = true;
		
		boolean not_end;
		boolean equality;
		int length;
		int possibility[][];
		
		// test (0,0)
		if(print) {
			System.out.println("debut test (0,0)");
		}
		not_end = true;
		equality = true;
		length = 60;
		possibility = movePossibility(0,0);
		int result_0_0[][] = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{1,1},{1,2},{2,3},{2,4},{3,5},{3,6},{4,7},{0,1}};
		for(int i = 0; i < 60 && not_end; i++) {
			if(possibility[i] != null && i < result_0_0.length) {
				if(print) {
					System.out.println("recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
				}
				if(possibility[i][0] != result_0_0[i][0] || possibility[i][1] != result_0_0[i][1]) {
					if(print) {
						System.out.println("attendu x : " + result_0_0[i][0] + " y : " + result_0_0[i][1] + " ; recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
					}
					equality = false;
				}
			}
			else {
				not_end = false;
				length = i;
			}
		}
		equality = equality && result_0_0.length == length;
		if(!equality) {
			if(print) {
				System.out.println("erreur en (0,0)");
			}
		}
		result = result && equality;
		
		
		// test (4,3)
		if(print) {
			System.out.println("debut test (4,3)");
		}
		not_end = true;
		equality = true;
		length = 60;
		possibility = movePossibility(4,3);
		int result_4_3[][] = {{5,3},{6,3},{7,3},{4,4},{5,5},{5,6},{6,7},{3,4},{3,5},{2,6},{2,7},{3,3},{2,3},{1,3},{0,3},{3,2},{3,1},{2,0},{4,2},{5,1},{5,0}};
		for(int i = 0; i < 60 && not_end; i++) {
			if(possibility[i] != null && i < result_4_3.length) {
				if(print) {
					System.out.println("recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
				}
				if(possibility[i][0] != result_4_3[i][0] || possibility[i][1] != result_4_3[i][1]) {
					if(print) {
						System.out.println("attendu x : " + result_4_3[i][0] + " y : " + result_4_3[i][1] + " ; recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
					}
					equality = false;
				}
			}
			else {
				not_end = false;
				length = i;
			}
		}
		equality = equality && result_4_3.length == length;
		if(!equality) {
			if(print) {
				System.out.println("erreur en (4,3)");
			}
		}
		result = result && equality;
		
		
		// test (2,6)
		if(print) {
			System.out.println("debut test (2,6)");
		}
		not_end = true;
		equality = true;
		length = 60;
		possibility = movePossibility(2,6);
		int result_2_6[][] = {{3,6},{4,6},{5,6},{6,6},{3,7},{2,7},{1,6},{0,6},{2,5},{1,4},{1,3},{0,2},{0,1},{3,5},{3,4},{4,3},{4,2},{5,1},{5,0}};
		for(int i = 0; i < 60 && not_end; i++) {
			if(possibility[i] != null && i < result_2_6.length) {
				if(print) {
					System.out.println("recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
				}
				if(possibility[i][0] != result_2_6[i][0] || possibility[i][1] != result_2_6[i][1]) {
					if(print) {
						System.out.println("attendu x : " + result_2_6[i][0] + " y : " + result_2_6[i][1] + " ; recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
					}
					equality = false;
				}
			}
			else {
				not_end = false;
				length = i;
			}
		}
		equality = equality && result_2_6.length == length;
		if(!equality) {
			if(print) {
				System.out.println("erreur en (2,6)");
			}
		}
		result = result && equality;

		// test (2,6) avec pingouins et trous
		if(print) {
			System.out.println("debut test (2,6) bis");
		}
		takePosition(2,7);
		takePosition(5,0);
		removeTile(1,3);
		
		not_end = true;
		equality = true;
		length = 60;
		possibility = movePossibility(2,6);
		int result_2_6_bis[][] = {{3,6},{4,6},{5,6},{6,6},{3,7},{1,6},{0,6},{2,5},{1,4},{3,5},{3,4},{4,3},{4,2},{5,1}};
		for(int i = 0; i < 60 && not_end; i++) {
			if(possibility[i] != null && i < result_2_6_bis.length) {
				if(print) {
					System.out.println("recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
				}
				if(possibility[i][0] != result_2_6_bis[i][0] || possibility[i][1] != result_2_6_bis[i][1]) {
					if(print) {
						System.out.println("attendu x : " + result_2_6_bis[i][0] + " y : " + result_2_6_bis[i][1] + " ; recu x : " + possibility[i][0] + " y : " + possibility[i][1]);
					}
					equality = false;
				}
			}
			else {
				not_end = false;
				length = i;
			}
		}
		equality = equality && result_2_6_bis.length == length;
		if(!equality) {
			if(print) {
				System.out.println("erreur en (2,6) bis");
			}
		}
		result = result && equality;
				
		return result;
	}
	
	/**
	 * lance les tests de la classe
	 */
	public void test() {
		boolean result = true;
		Scanner s = new Scanner(System.in);
		
		// vérifie qu'il y a bien le nombre correct de pièces des différentes valeurs
		System.out.println("tester le mélange ?(1 ou 0)");
		if(s.nextInt() == 1){
			result = test_shuffle();
			System.out.println(result);
		}
		
		System.out.println("tester la détection d'alignement ?(1 ou 0)"); // vérifie la fonction qui détermine si deux pièces sont alignées ou pas
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_align(true);
				System.out.println(result);
			}
			else {
				result = test_align(false);
				System.out.println(result);
			}
		}
		
		System.out.println("tester la détection de voyages possibles  ?(1 ou 0)"); // vérifie la fonction qui détermine si un déplacement entre deux pièces est possible
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_legitTravel(true);
				System.out.println(result);
			}
			else {
				result = test_legitTravel(false);
				System.out.println(result);
			}
		}
		
		System.out.println("tester les déplacement ?(1 ou 0)"); // vérifie la fonction qui exécute un déplacement
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_makeMove(true);
				System.out.println(result);
			}
			else {
				result = test_makeMove(false);
				System.out.println(result);
			}
		}
		
		System.out.println("tester les prévisions des coups possibles ?(1 ou 0)"); // vérifie la fonction qui cherche les coups possibles
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_movePossibility(true);
				System.out.println(result);
			}
			else {
				result = test_movePossibility(false);
				System.out.println(result);
			}
		}
		
		s.close();
	}
}