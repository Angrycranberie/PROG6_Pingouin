import java.util.Random;
import java.util.Scanner;

/**
 * Classe de gestion du plateau. Contient les methodes vérifiant l'alignement
 * entre cases du plateau, et celles jouant des coups.
 * @author Charly
 */
public class Board {
	Tile tab[][];

	/**
	 * Constructeur. Crée un tableau non initialisé de tiles 8*8
	 */
	public Board() {
		tab = new Tile[8][8];
	}
	
	/**
	 * Rend la case (x, y) occupée
	 * @param x Coordonnée x de la case
	 * @param y Coordonnée y de la case
	 */
	public void takePosition(int x, int y) {
		tab[y][x].changeStatue();
	}

	/**
	 * Retire la case (x, y) du jeu
	 * @param x Coordonnée x de la case
	 * @param y Coordonnée y de la case
	 * @return La case retirée du jeu
	 */
	public Tile removeTile(int x, int y) {
		Tile res = tab[y][x];
		tab[y][x] = null;
		return res;
	}
	
	/**
	 * Génère un nouveau plateau de jeu de façon aléatoire.
	 * Les proportions des valeurs des cases sont fixes.
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
					tab[y][x] = new Tile();
					tab[y][x].setValue(value);
				}
			}
		}
	}
	/**
	 * Fonction vérifiant les proportions de tiles dans le plateau.
	 * @return Vrai si les proportions sont correctes, faux sinon.
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
	 * Determine si les cases (x1, y1) et (x2, y2) sont alignées.
	 * @param x1 Coordonnée x de la case 1
	 * @param y1 Coordonnée y de la case 1
	 * @param x2 Coordonnée x de la case 2
	 * @param y2 Coordonnée y de la case 2
	 * @return Un entier indiquant l'alignement :
	 *	0 si non-aligné ou l'une des cases ne contient pas de tuile
	 * 	1 si alignés à l'horizontal	
	 *	2 si alignés selon la diagonale [Bas-gauche ; Haut-droite] (comme un slash / )
	 *	3 si alignés selon la diagonale [Haut-gauche ; Bas-droite] (comme un antislash \ )
	 */
	public int align(int x1 , int y1 , int x2 , int y2) {
		// Exception non levée si coorodonnées out of bound.
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
	 * Methode de test. Vérifie l'alignement de chaque case.
	 * @param print Vrai si l'on veut afficher les tests sur la sortie standard, Faux sinon.
	 * @return Vrai si tous les tests sont passés, Faux sinon.
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
	
	// d�termine si un d�placement entre les deux propositions donn�es est valide
	/**
	 * Determine si le déplacement de (x1, y1) à (x2, y2) est autorisé ou non.
	 * @param x1 Coordonnée x de la case 1
	 * @param y1 Coordonnée y de la case 1
	 * @param x2 Coordonnée x de la case 2
	 * @param y2 Coordonnée y de la case 2
	 * @return Vrai si le déplacement est possible, Faux sinon.
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
					
					if(tab[y][x] == null || tab[y][x].occupied()) {
						return false;
					}
				}
			}
			else {
				for(int x = x1 - 1 ; x >= x2 ; x--) {
					if((x != x2 || y != y2) && align(x,y,x2,y2) != align(x,y,x1,y1)) {
						System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
					}
					if(tab[y][x] == null || tab[y][x].occupied()) {
						return false;
					}
				}
			}
			return true;
		}
		else if(resAlign == 2) { // align�s en /
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
					if(tab[y][x] == null || tab[y][x].occupied()) {
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
					if(tab[y][x] == null || tab[y][x].occupied()) {
						return false;
					}
					if(y % 2 == 0) {
						x++;
					}
				}
			}
			return true;
		}
		else if(resAlign == 3){ // align�s en \
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
					if(tab[y][x] == null || tab[y][x].occupied()) {
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
					if(tab[y][x] == null || tab[y][x].occupied()) {
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
	 * 
	 * @param print
	 * @return
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
	
	// effectue le d�placement de (x1,y1) vers (x2,y2) si les r�gles sont suivis, retourne la tuile de d�part
	// en l'enlevant du plateau et rend la tuile d'arriv�e occup�e
	// si le d�placement n'�tait pas valide la tuil retourn� est null et aucune modification du plateau n'est faites
	public Tile makeMove(int x1, int y1 , int x2 , int y2) {
		
		if(legitTravel(x1,y1,x2,y2) && tab[y1][x1].occupied()) {
			takePosition(x2,y2);
			return removeTile(x1,y1);
		}
		return null;
	}
	
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
	
	// affiche le plateau
	// option = 0 valeurs des poissons sur le plateau
	// option = 1 affiche les positions prise par les joueurs
	public void printBoard(int option) {
		for(int y = 0 ; y < 8 ; y++) {
			if(y % 2 == 0) {
				System.out.print(" ");
			}
			for(int x = 0 ; x < 8 ; x++) {
				if(tab[y][x] != null) {
					if(option == 0) {
						System.out.print(tab[y][x].value() + " ");
					}
					else if(option == 1) {
						if(tab[y][x].occupied()) {
							System.out.print("B ");
						}
						else {
							System.out.print(". ");
						}
						
					}
				}
			}
			System.out.println();
		}
	}
	
	// permet l'affichage lors des test
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
				else if(option == 2) { // deplacement l�gal
					if(x == x1 && y == y1) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].occupied()) {
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
						else if(tab[y][x].occupied()) {
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
						else if(tab[y][x].occupied()) {
							System.out.print("B ");
						}
						else{
							System.out.print(". ");
						}
					}
				}
				else if(option == 3){ // d�placement
					if(x == x1 && y == y1) {
						if(tab[y][x] == null) {
							System.out.print("V ");
						}
						else if(tab[y][x].occupied()) {
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
						else if(tab[y][x].occupied()) {
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
						else if(tab[y][x].occupied()) {
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
	
	// lance les diff�rentes fonctions de test de la classe
	public void test() {
		boolean result = true;
		Scanner s = new Scanner(System.in);
		
		takePosition(0,1);
		takePosition(0,3);
		takePosition(3,2);
		takePosition(5,3);
		takePosition(3,1);
		
		printBoard(1);
		
		// v�rifie qu'il y a bien le nombre correct de pi�ces des diff�rentes valeurs
		System.out.println("tester le m�lange ?(1 ou 0)");
		if(s.nextInt() == 1){
			result = test_shuffle();
			System.out.println(result);
		}
		
		System.out.println("tester la d�tection d'alignement ?(1 ou 0)"); // v�rifie la fonction qui d�termine si deux pi�ces sont align�es ou pas
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
		
		System.out.println("tester la d�tection de voyages possibles  ?(1 ou 0)"); // v�rifie la fonction qui d�termine si un d�placement entre deux pi�ces est possible
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
		
		System.out.println("tester les d�placement ?(1 ou 0)"); // v�rifie la fonction qui ex�cute un d�placement
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
		
		s.close();
	}
}
