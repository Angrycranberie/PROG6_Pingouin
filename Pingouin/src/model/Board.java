package model;
import java.util.Random;
import java.util.Scanner;

/**
 * Classe Board. Correspond à un tableau de jeu 8*8 qui contient les cases du jeu
 * @author Charly
 *
 */
public class Board {
	// Constantes d'alignement des tuiles.
	public static final int NULL_ALIGN = 0;
	public static final int HORIZONTAL_ALIGN = 1;
	public static final int SLASH_ALIGN = 2;
	public static final int ANTISLASH_ALIGN = 3;

	private Tile[][] tab; // Représentation du plateau sous forme de tableau.

	/** Constructeur du plateau.
	 * On modélise ce dernier par un tableau de 8*8 cases puis on génère aléatoirement son contenu.
	 */
	public Board() {
		tab = new Tile[8][8];
		shuffle();
	}
	
	/**
	 * Permet à un pingouin d'occuper une tuile.
	 * @param x Coordonnée x de la tuile à occuper.
	 * @param y Coordonnée y de la tuile à occuper.
	 */
	public void occupyWithPenguin(int x, int y) { tab[y][x].occupy(); }
	
	/**
	 * Libère une tuile d'un pingouin.
	 * @param x Coordonnée x de la tuile à libérer.
	 * @param y Coordonnée y de la tuile à libérer.
	 */
	public void freeFromPenguin(int x, int y) { tab[y][x].free(); }
	
	/**
	 * Retire une tuile du plateau.
	 * @param x Coordonnée x de la tuile à enlever.
	 * @param y Coordonnée y de la tuile à enlever.
	 * @return Tuile enlevée du plateau.
	 */
	public Tile removeTile(int x, int y) {
		Tile res = tab[y][x];
		tab[y][x] = null;
		return res;
	}
	
	/**
	 * Distribue aléatoirement les poissons sur le plateau de jeu.
	 */
	public void shuffle() {
		// Nombre de cases comportant respectivement un, deux et trois poissons.
		int threeFish = 10, twoFish = 20, oneFish = 30;

		// Variable de tirage et du nombre de poissons effectif à ajouter à la tuile.
		int draw, fishNumber;

		Random r = new Random();
		for (int y = 0 ; y < 8 ; y++) {
			for (int x = 0 ; x < 8 ; x++) {
				if (y % 2 != 0 || x != 7) {
					draw = r.nextInt(threeFish + twoFish + oneFish) + 1;  // 1 <= draw <= oneFish + twoFish + threeFish.
					if (draw <= oneFish) { // 1 <= draw <= oneFish ; une valeur possible.
						fishNumber = Tile.ONE_FISH;
						oneFish--;
					} else if(draw <= oneFish + twoFish) { // oneFish < draw <= oneFish + twoFish ; deux valeurs possibles.
						fishNumber = Tile.TWO_FISH;
						twoFish--;
					} else { // oneFish + twoFish < draw <= oneFish + twoFish + threeFish ; trois valeurs possibles.
						fishNumber = Tile.THREE_FISH;
						threeFish--;
					}
					tab[y][x] = new Tile(fishNumber);
				}
			}
		}
	}
	
	/**
	 * Test de la fonction "shuffle".
	 * Vérifie si elle a bien remis toutes les tuiles et qu'il y a le bon nombre de tuiles pour chaque valeur.
	 * @return Indique si le test a réussi.
	 */
	private boolean test_shuffle() {
		shuffle();
		int threeFish, twoFish, oneFish;
		threeFish = twoFish = oneFish = 0;
		for (int y = 0 ; y < 8 ; y++) {
			for (int x = 0 ; x < 8 ; x++) {
				if (tab[y][x] != null) {
					switch (tab[y][x].getFishNumber()) {
						case 1: oneFish++; break;
						case 2: twoFish++; break;
						case 3: threeFish++; break;
						default:
							System.out.println(
									tab[y][x].getFishNumber()+" n'est pas une valeur valide, coord : x "+x+" y "+y
							);
							return false;
					}
				}
			}
		}
		return threeFish == 10 && twoFish == 20 && oneFish == 30;
	}
	
	/**
	 * Vérifie si deux tuiles sont alignées.
	 * @param x1 Coordonnée x de la tuile 1.
	 * @param y1 Coordonnée y de la tuile 1.
	 * @param x2 Coordonnée x de la tuile 2.
	 * @param y2 Coordonnée x de la tuile 2.
	 * @return 0 : pas d'alignement ou erreur de tuile ; 1 : horizontal ; 2 : en slash ; 3 en antislash.
	 */
	public int areAligned(int x1, int y1, int x2, int y2) {
		// Vérification de la validité des tuiles.
		boolean trueTile = tab[y1][x1] != null && tab[y2][x2] != null;

		// Recherche d'un alignement horizontal.
		boolean horizontal = y1 == y2;

		// Recherche d'un alignement diagonal droit (slash).
		boolean slash;
		if (y1 < y2) {
			if(y1 % 2 == 0) slash = x1 + ((y1 - y2) / 2) == x2;
			else slash = x1 + ((y1 + 1 - y2) / 2) - 1 == x2;
		} else {
			if(y2 % 2 == 0) slash = x2 + ((y2 - y1) / 2) == x1;
			else slash = x2 + ((y2 + 1 - y1) / 2) - 1 == x1;
		}

		// Recherche d'un alignement diagonal gauche (antislash).
		boolean antislash;
		if (y1 < y2) {
			if(y1 % 2 == 0) antislash = x1 + ((y2 - y1 - 1) / 2) + 1 == x2;
			else antislash = x1 + ((y2  - y1) / 2)  == x2;
		} else {
			if(y2 % 2 == 0) antislash = x2 + ((y1 - y2 - 1) / 2) + 1== x1;
			else antislash = x2 + ((y1 - y2) / 2)  == x1;
		}

		if (trueTile && (horizontal || slash || antislash)) {
			if (horizontal) return Board.HORIZONTAL_ALIGN;
			else if (slash) return Board.SLASH_ALIGN;
			else return Board.ANTISLASH_ALIGN;
		} else return Board.NULL_ALIGN;
	}
	
	/**
	 * Test de la fonction "areAligned".
	 * @param print Indique si la fonction doit afficher les tests ou non.
	 * @return Indique si le test a réussi.
	 */
	private boolean test_areAligned(boolean print) {
		boolean res = true;
		// test horizontaux
		res = areAligned(0, 0, 6, 0) != 0;
		if(print) {
			printTest(1,0,0,6,0);
		}
		if(!res) {
			System.out.println("(0,0,6,0) faux");
		}
		
		res = res && areAligned(0,0,1,0) != 0;
		if(print) {
			printTest(1,0,0,1,0);
		}
		if(!res) {
			System.out.println("(0,0,1,0) faux");
		}
		
		res = res && areAligned(3,3,0,3) != 0;
		if(print) {
			printTest(1,3,3,0,3);
		}
		if(!res) {
			System.out.println("(3,3,0,3) faux");
		}
		
		res = res && areAligned(7,3,0,3) != 0;
		if(print) {
			printTest(1,7,3,0,3);
		}
		
		if(!res) {
			System.out.println("(7,3,0,3) faux");
		}
		
		res = res && areAligned(7,3,6,3) != 0;
		if(print) {
			printTest(1,7,3,6,3);
		}
		
		if(!res) {
			System.out.println("(7,3,6,3) faux");
		}
		
		// test slash
		res = res && areAligned(0,0,1,1) != 0;
		if(print) {
			printTest(1,0,0,1,1);
		}
		if(!res) {
			System.out.println("(0,0,1,1) faux");
		}
		
		res = res && areAligned(0,0,4,7) != 0;
		if(print) {
			printTest(1,0,0,4,7);
		}
		if(!res) {
			System.out.println("(0,0,4,7) faux");
		}
		
		res = res && areAligned(6,7,5,6) != 0;
		if(print) {
			printTest(1,6,7,5,6);
		}
		if(!res) {
			System.out.println("(6,7,5,6) faux");
		}
		
		res = res && areAligned(6,7,2,0) != 0;
		if(print) {
			printTest(1,6,7,2,0);
		}
		if(!res) {
			System.out.println("(6,7,2,0) faux");
		}
		
		res = res && areAligned(6,7,4,3) != 0;
		if(print) {
			printTest(1,6,7,4,3);
		}
		if(!res) {
			System.out.println("(6,7,4,3) faux");
		}
		
		// test antislash
		res = res && areAligned(0,0,0,1) != 0;
		if(print) {
			printTest(1,0,0,0,1);
		}
		if(!res) {
			System.out.println("(0,0,0,1) faux");
		}
		
		res = res && areAligned(6,0,6,1) != 0;
		if(print) {
			printTest(1,6,0,6,1);
		}
		if(!res) {
			System.out.println("(6,0,6,1) faux");
		}
		
		res = res && areAligned(6,0,3,7) != 0;
		if(print) {
			printTest(1,6,0,3,7);
		}
		if(!res) {
			System.out.println("(6,0,3,7) faux");
		}
		
		res = res && areAligned(0,7,3,0) != 0;
		if(print) {
			printTest(1,0,7,3,0);
		}
		if(!res) {
			System.out.println("(0,7,3,0) faux");
		}
		
		res = res && areAligned(0,7,0,6) != 0;
		if(print) {
			printTest(1,0,7,0,6);
		}
		if(!res) {
			System.out.println("(0,7,0,6) faux");
		}
		
		res = res && areAligned(2,7,4,3) != 0;
		if(print) {
			printTest(1,2,7,4,3);
		}
		if(res == false) {
			System.out.println("(2,7,4,3) faux");
		}
		
		// test non align�
		res = res && areAligned(0,0,2,1) == 0;
		if(print) {
			printTest(1,0,0,2,1);
		}
		if(!res) {
			System.out.println("(0,0,2,1) faux");
		}
		
		res = res && areAligned(0,0,0,2) == 0;
		if(print) {
			printTest(1,0,0,0,2);
		}
		if(!res) {
			System.out.println("(0,0,0,2) faux");
		}
		
		res = res && areAligned(7,7,7,5) == 0;
		if(print) {
			printTest(1,7,7,7,5);
		}
		if(!res) {
			System.out.println("(7,7,7,5) faux");
		}
		
		res = res && areAligned(1,7,1,4) == 0;
		if(print) {
			printTest(1,1,7,1,4);
		}
		if(!res) {
			System.out.println("(1,7,1,4) faux");
		}
		
		res = res && areAligned(2,4,7,5) == 0;
		if(print) {
			printTest(1,2,4,7,5);
		}
		if(!res) {
			System.out.println("(2,4,7,5) faux");
		}
		
		return res;
	}
	
	/**
	 * Indique si un déplacement entre deux tuiles est valide ou non.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée x de la tuile d'arrivée.
	 * @return Vrai (true) si un déplacement est valide ; faux (false) sinon.
	 */
	public boolean isMoveLegit(int x1, int y1, int x2, int y2) {
		int resAlign = areAligned(x1, y1, x2, y2), x, y;
		switch (resAlign) {
			case Board.HORIZONTAL_ALIGN:
				y = y1;
				if (x1 < x2) {
					for (x = x1+1; x <= x2; x++) {
						if ((x != x2 || y != y2) && areAligned(x, y, x2, y2) != areAligned(x, y, x1, y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if (tab[y][x] == null || tab[y][x].occupied()) return false;
					}
				} else {
					for (x = x1-1; x >= x2; x--) {
						if ((x != x2 || y != y2) && areAligned(x,y,x2,y2) != areAligned(x,y,x1,y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if (tab[y][x] == null || tab[y][x].occupied()) return false;
					}
				}
				return true;
			case Board.SLASH_ALIGN:
				if (y1 < y2) {
					x = x1;
					if (y1 % 2 == 1) x--;
					for (y = y1+1; y <= y2; y++) {
						if ((x != x2 || y != y2) && areAligned(x, y, x2, y2) != areAligned(x, y, x1, y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if (tab[y][x] == null || tab[y][x].occupied()) return false;
						if (y % 2 == 1) x--;
					}
				} else {
					x = x1;
					if (y1 % 2 == 0) x++;
					for (y = y1-1; y >= y2; y--) {
						if ((x != x2 || y != y2) && areAligned(x,y,x2,y2) != areAligned(x,y,x1,y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if (tab[y][x] == null || tab[y][x].occupied()) return false;
						if (y % 2 == 0) x++;
					}
				}
				return true;
			case Board.ANTISLASH_ALIGN:
				if (y1 < y2) {
					x = x1;
					if(y1 % 2 == 0) x++;
					for (y = y1+1; y <= y2; y++) { // parcours de toute les cases entre les deux tuiles
						if ((x != x2 || y != y2) && areAligned(x, y, x2, y2) != areAligned(x, y, x1, y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if(tab[y][x] == null || tab[y][x].occupied()) return false;
						if(y % 2 == 0) x++;
					}
				} else {
					x = x1;
					if (y1 % 2 == 1) x--;
					for(y = y1-1; y >= y2; y--) {
						if ((x != x2 || y != y2) && areAligned(x, y, x2, y2) != areAligned(x, y, x1, y1))
							System.out.println("erreur -> x : " + x + " y : " + y + " x1 : " + x1 + "  y1 : " + y1 + " x2 : " + x2 + "  y2 : " + y2);
						if(tab[y][x] == null || tab[y][x].occupied()) return false;
						if(y % 2 == 1) x--;
					}
				}
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Teste la fonction "isMoveLegit".
	 * @param print Indique si la fonction doit afficher les tests ou non.
	 * @return Vrai (true) si le test s'est bien passé ; faux (false) sinon.
	 */
	private boolean test_isMoveLegit(boolean print) {
		boolean res = true;	
		shuffle();
		occupyWithPenguin(0,1);
		occupyWithPenguin(0,3);
		occupyWithPenguin(3,2);
		occupyWithPenguin(5,3);
		occupyWithPenguin(3,1);
		
		// test horizontaux
		res = res && isMoveLegit(0,0,6,0);
		if(print) {
			System.out.println(isMoveLegit(0,0,6,0));
			printTest(2,0,0,6,0);
		}
		if(res == false) {
			System.out.println("(0,0,6,0) faux");
		}
		
		res = res && isMoveLegit(0,0,1,0);
		if(print) {
			System.out.println(isMoveLegit(0,0,1,0));
			printTest(2,0,0,1,0);
		}
		if(res == false) {
			System.out.println("(0,0,1,0) faux");
		}
		
		res = res && !isMoveLegit(3,3,0,3);
		if(print) {
			System.out.println(isMoveLegit(3,3,0,3));
			printTest(2,3,3,0,3);
		}
		if(res == false) {
			System.out.println("(3,3,0,3) faux");
		}
		
		res = res && !isMoveLegit(7,3,0,3);
		if(print) {
			System.out.println(isMoveLegit(7,3,0,3));
			printTest(2,7,3,0,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,0,3) faux");
		}
		
		res = res && isMoveLegit(7,3,6,3);
		if(print) {
			System.out.println(isMoveLegit(7,3,6,3));
			printTest(2,7,3,6,3);
		}
		
		if(res == false) {
			System.out.println("(7,3,6,3) faux");
		}
		
		// test slash
		res = res && isMoveLegit(0,0,1,1);
		if(print) {
			System.out.println(isMoveLegit(0,0,1,1));
			printTest(2,0,0,1,1);
		}
		if(res == false) {
			System.out.println("(0,0,1,1) faux");
		}
		
		res = res && isMoveLegit(0,0,4,7);
		if(print) {
			System.out.println(isMoveLegit(0,0,4,7));
			printTest(2,0,0,4,7);
		}
		if(res == false) {
			System.out.println("(0,0,4,7) faux");
		}
		
		res = res && isMoveLegit(6,7,5,6);
		if(print) {
			System.out.println(isMoveLegit(6,7,5,6));
			printTest(2,6,7,5,6);
		}
		if(res == false) {
			System.out.println("(6,7,5,6) faux");
		}
		
		res = res && !isMoveLegit(6,7,2,0);
		if(print) {
			System.out.println(isMoveLegit(6,7,2,0));
			printTest(2,6,7,2,0);
		}
		if(res == false) {
			System.out.println("(6,7,2,0) faux");
		}
		
		res = res && isMoveLegit(6,7,4,3);
		if(print) {
			System.out.println(isMoveLegit(6,7,4,3));
			printTest(2,6,7,4,3);
		}
		if(res == false) {
			System.out.println("(6,7,4,3) faux");
		}
		
		// test antislash
		res = res && !isMoveLegit(0,0,0,1);
		if(print) {
			System.out.println(isMoveLegit(0,0,0,1));
			printTest(2,0,0,0,1);
		}
		if(res == false) {
			System.out.println("(0,0,0,1) faux");
		}
		
		res = res && isMoveLegit(6,0,6,1);
		if(print) {
			System.out.println(isMoveLegit(6,0,6,1));
			printTest(2,6,0,6,1);
		}
		if(res == false) {
			System.out.println("(6,0,6,1) faux");
		}
		
		res = res && !isMoveLegit(6,0,3,7);
		if(print) {
			System.out.println(isMoveLegit(6,0,3,7));
			printTest(2,6,0,3,7);
		}
		if(res == false) {
			System.out.println("(6,0,3,7) faux");
		}
		
		res = res && !isMoveLegit(0,7,3,1);
		if(print) {
			System.out.println(isMoveLegit(0,7,3,1));
			printTest(2,0,7,3,1);
		}
		if(res == false) {
			System.out.println("(0,7,3,1) faux");
		}
		
		res = res && isMoveLegit(0,7,0,6);
		if(print) {
			System.out.println(isMoveLegit(0,7,0,6));
			printTest(2,0,7,0,6);
		}
		if(res == false) {
			System.out.println("(0,7,0,6) faux");
		}
		
		res = res && isMoveLegit(2,7,4,3);
		if(print) {
			System.out.println(isMoveLegit(2,7,4,3));
			printTest(2,2,7,4,3);
		}
		if(res == false) {
			System.out.println("(2,7,4,3) faux");
		}
		
		return res;
	}

	/**
	 * Effectue le déplacement du pingouin de sa tuile vers une autre tuile si cela est possible.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée x de la tuile d'arrivée.
	 * @return La tuile de départ si le déplacement s'est fait ; null sinon.
	 */
	public Tile makeMove(int x1, int y1, int x2, int y2) {
		if (isMoveLegit(x1, y1, x2, y2) && tab[y1][x1].occupied()) {
			occupyWithPenguin(x2,y2);
			return removeTile(x1,y1);
		}
		return null;
	}
	
	/**
	 * Teste la fonction "makeMove".
	 * @param print Indique si la fonction doit afficher les tests ou non.
	 * @return Vrai (true) si le test a réussi ; faux (false) sinon.
	 */
	private boolean test_makeMove(boolean print) {
		boolean res = true;	
		boolean r;
		shuffle();
		occupyWithPenguin(0,0);
		occupyWithPenguin(4,1);
		occupyWithPenguin(2,2);
		occupyWithPenguin(1,3);
		occupyWithPenguin(0,6);
		occupyWithPenguin(2,6);
		occupyWithPenguin(3,6);
		
		
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
	 * Annule un coup d'une tuile vers une autre.
	 * @param x1 Coordonnée x de la tuile de départ.
	 * @param y1 Coordonnée y de la tuile de départ.
	 * @param x2 Coordonnée x de la tuile d'arrivée.
	 * @param y2 Coordonnée x de la tuile d'arrivée.
	 * @param nbFish Nombre de poissons sur la tuile de départ.
	 */
	public void reverseMove(int x1, int y1, int x2, int y2, int nbFish) {
		if(tab[y2][x2].occupied() && tab[y1][x1] == null) {
			tab[y2][x2].free(); // On quitte la tuile d'arrivée.
			tab[y1][x1] = new Tile(nbFish); // On recrée la case de départ avec le bon nombre de poissons.
			tab[y1][x1].occupy(); // On met le pingouin dessus.
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
						System.out.print(tab[y][x].getFishNumber() + " ");
					}
					else {
						System.out.print("  ");
					}
				}
				else if(option == 1) {
					if(tab[y][x] == null) {
						System.out.print("O ");
					}
					else if(tab[y][x].occupied()) {
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
				else if(option == 3){ // déplacement
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
				result = test_areAligned(true);
				System.out.println(result);
			}
			else {
				result = test_areAligned(false);
				System.out.println(result);
			}
		}
		
		System.out.println("tester la détection de voyages possibles  ?(1 ou 0)"); // vérifie la fonction qui détermine si un déplacement entre deux pièces est possible
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_isMoveLegit(true);
				System.out.println(result);
			}
			else {
				result = test_isMoveLegit(false);
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
		
		s.close();
	}
}
