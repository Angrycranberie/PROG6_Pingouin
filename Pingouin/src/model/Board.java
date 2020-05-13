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
	 * Renvoie la tuile de coordonnées x, y.
	 * @param x Coordonnée x de la tuile cherchée.
	 * @param y Coordonnée y de la tuile cherchée.
	 * @return La tuile cherchée.
	 */
	public Tile getTile(int x, int y){
		return tab[y][x];
	}
	
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
	 * retourne la prochaine tuile dans une direction et un sens donné en partant d'une tuile, retourne null si la tuile est hors plateau
	 * @param x Coordonnée x de la tuile de départ
	 * @param y Coordonnée y de la tuile de départ
	 * @param way direction 
	 * @param direction Sens (0 haut-gauche , 1 bas-droite)
	 * @return tuile dans la direction et sens demandé
	 */
	public int[] nextTile(int x, int y, int way, boolean direction) {
		int next[] = {-1,-1};
		switch(way) {
		case HORIZONTAL_ALIGN:
			next[1] = y;
			if(direction) {
				next[0] = x + 1;
			}
			else {
				next[0] = x - 1;
			}
			break;
		case SLASH_ALIGN:
			if(direction) {
				next[1] = y + 1;
				if(y % 2 == 1) {
					next[0] = x - 1;
				}
				else {
					next[0] = x;
				}
			}
			else {
				next[1] = y - 1;
				if(y % 2 == 0) {
					next[0] = x + 1;
				}
				else {
					next[0] = x;
				}
			}
			break;
		case ANTISLASH_ALIGN:
			if(direction) {
				next[1] = y + 1;
				if(y % 2 == 0) {
					next[0] = x + 1;
				}
				else {
					next[0] = x;
				}
			}
			else {
				next[1] = y - 1;
				if(y % 2 == 1) {
					next[0] = x - 1;
				}
				else {
					next[0] = x;
				}
			}
			break;
		}
		
		if((next[0] < 8 && next[0] >= 0) && (next[1] < 8 && next[1] >= 0) && (next[1] % 2 == 1 || next[0] < 7)) {
			return next;
		}
		else {
			return null;
		}
	}
	
	private boolean test_nextTile(boolean print) {
		boolean res = true;	
		
		int result_2_3[] = {3,3};
		res = res && runTestNextTile(result_2_3,HORIZONTAL_ALIGN,true,2,3,print);
		
		int result_2_2[] = {1,2};
		res = res && runTestNextTile(result_2_2,HORIZONTAL_ALIGN,false,2,2,print);
		
		int result_4_2_true[] = {5,3};
		res = res && runTestNextTile(result_4_2_true,ANTISLASH_ALIGN,true,4,2,print);

		int result_4_2_false[] = {4,1};
		res = res && runTestNextTile(result_4_2_false,ANTISLASH_ALIGN,false,4,2,print);
		
		int result_3_4_true[] = {4,5};
		res = res && runTestNextTile(result_3_4_true,SLASH_ALIGN,true,3,4,print);
		
		int result_3_4_false[] = {3,3};
		res = res && runTestNextTile(result_3_4_false,SLASH_ALIGN,false,3,4,print);
		
		
		int result_7_7[] = null;
		res = res && runTestNextTile(result_7_7,HORIZONTAL_ALIGN,true,7,7,print);
		
		int result_0_5[] = null;
		res = res && runTestNextTile(result_0_5,HORIZONTAL_ALIGN,false,0,5,print);
		
		int result_0_7_true[] = null;
		res = res && runTestNextTile(result_0_7_true,ANTISLASH_ALIGN,true,0,7,print);
		
		int result_0_7_false[] = null;
		res = res && runTestNextTile(result_0_7_false,ANTISLASH_ALIGN,false,0,7,print);
		
		int result_7_1[] = null;
		res = res && runTestNextTile(result_7_1,SLASH_ALIGN,false,7,1,print);
		
		int result_3_7[] = null;
		res = res && runTestNextTile(result_3_7,SLASH_ALIGN,true,3,7,print);
		
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
		if(y1 > y2) {
			int temp = y2;
			y2 = y1;
			y1 = temp;
			temp = x2;
			x2 = x1;
			x1 = temp;
		}
		
		// Vérification de la validité des tuiles.
		boolean trueTile = tab[y1][x1] != null && tab[y2][x2] != null;

		// Recherche d'un alignement horizontal.
		boolean horizontal = y1 == y2;

		// Recherche d'un alignement diagonal droit (slash).
		boolean slash;
		if(y1 % 2 == 0) 
			slash = x1 + ((y1 - y2) / 2) == x2;
		else 
			slash = x1 + ((y1 + 1 - y2) / 2) - 1 == x2;

		// Recherche d'un alignement diagonal gauche (antislash).
		boolean antislash;
		if(y1 % 2 == 0) 
			antislash = x1 + ((y2 - y1 - 1) / 2) + 1 == x2;
		else 
			antislash = x1 + ((y2  - y1) / 2)  == x2;

		if (trueTile && (horizontal || slash || antislash)) {
			if (horizontal) return Board.HORIZONTAL_ALIGN;
			else if (slash) return Board.SLASH_ALIGN;
			else return Board.ANTISLASH_ALIGN;
		} else return Board.NULL_ALIGN;
	}
	
	/**
	 * Test de la fonction "areAligned". (test : nbFonc = 0)
	 * @param print Indique si la fonction doit afficher les tests ou non.
	 * @return Indique si le test a réussi.
	 */
	private boolean test_areAligned(boolean print) {
		boolean res = true;
		
		// test horizontaux
		res = res && runTest(0,print,0,0,6,0,true);
		
		res = res && runTest(0,print,0,0,1,0,true);
		
		res = res && runTest(0,print,3,3,0,3,true);
		
		res = res && runTest(0,print,7,3,0,3,true);
		
		res = res && runTest(0,print,7,3,6,3,true);
		
		// test slash
		res = res && runTest(0,print,0,0,1,1,true);
		
		res = res && runTest(0,print,0,0,4,7,true);
		
		res = res && runTest(0,print,6,7,5,6,true);
		
		res = res && runTest(0,print,6,7,2,0,true);
		
		res = res && runTest(0,print,6,7,4,3,true);
		
		// test antislash
		res = res && runTest(0,print,0,0,0,1,true);
		
		res = res && runTest(0,print,6,0,6,1,true);
		
		res = res && runTest(0,print,6,0,3,7,true);
		
		res = res && runTest(0,print,0,7,3,0,true);
		
		res = res && runTest(0,print,0,7,0,6,true);
		
		res = res && runTest(0,print,2,7,4,3,true);
		
		// test non alignés
		res = res && runTest(0,print,0,0,2,1,false);
		
		res = res && runTest(0,print,0,0,0,2,false);
		
		res = res && runTest(0,print,7,7,7,5,false);
		
		res = res && runTest(0,print,1,7,1,4,false);
		
		res = res && runTest(0,print,2,4,7,5,false);
		
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
		int resAlign = areAligned(x1, y1, x2, y2);
		int coord[] = {x1,y1};
		switch (resAlign) {
			case Board.HORIZONTAL_ALIGN:
				while(coord[0] != x2 || coord[1] != y2) {
					coord = nextTile(coord[0],coord[1],HORIZONTAL_ALIGN,x2 > x1);
					if (tab[coord[1]][coord[0]] == null || tab[coord[1]][coord[0]].occupied()) 
						return false;
				}
				return true;
			case Board.SLASH_ALIGN:
				while(coord[0] != x2 || coord[1] != y2) {
					coord = nextTile(coord[0],coord[1],SLASH_ALIGN,y2 > y1);
					if (tab[coord[1]][coord[0]] == null || tab[coord[1]][coord[0]].occupied()) 
						return false;
				}
				return true;
			case Board.ANTISLASH_ALIGN:
				while(coord[0] != x2 || coord[1] != y2) {
					coord = nextTile(coord[0],coord[1],ANTISLASH_ALIGN,y2 > y1);
					if (tab[coord[1]][coord[0]] == null || tab[coord[1]][coord[0]].occupied()) 
						return false;
				}
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Teste la fonction "isMoveLegit". (test : nbFonc = 1)
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
		res = res && runTest(1,print,0,0,6,0,true);
		
		res = res && runTest(1,print,0,0,1,0,true);
		
		res = res && runTest(1,print,3,3,0,3,false);
		
		res = res && runTest(1,print,7,3,0,3,false);
		
		res = res && runTest(1,print,7,3,6,3,true);
		
		// test slash
		res = res && runTest(1,print,0,0,1,1,true);
		
		res = res && runTest(1,print,0,0,4,7,true);
		
		res = res && runTest(1,print,6,7,5,6,true);
		
		res = res && runTest(1,print,6,7,2,0,false);
		
		res = res && runTest(1,print,6,7,4,3,true);
		
		// test antislash
		res = res && runTest(1,print,0,0,0,1,false);
		
		res = res && runTest(1,print,6,0,6,1,true);
		
		res = res && runTest(1,print,6,0,3,7,false);
		
		res = res && runTest(1,print,0,7,3,1,false);
		
		res = res && runTest(1,print,0,7,0,6,true);
		
		res = res && runTest(1,print,2,7,4,3,true);
		
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
	 * Teste la fonction "makeMove". ( test : nbFonc = 2)
	 * @param print Indique si la fonction doit afficher les tests ou non.
	 * @return Vrai (true) si le test a réussi ; faux (false) sinon.
	 */
	private boolean test_makeMove(boolean print) {
		boolean res = true;
		shuffle();
		occupyWithPenguin(0,0);
		occupyWithPenguin(4,1);
		occupyWithPenguin(2,2);
		occupyWithPenguin(1,3);
		occupyWithPenguin(0,6);
		occupyWithPenguin(2,6);
		occupyWithPenguin(3,6);

		
		
		// test horizontaux
		res = res && runTest(2,print,1,3,5,3,true);
		
		res = res && runTest(2,print,2,6,0,2,false);
		
		res = res && runTest(2,print,2,6,1,4,true);
		
		res = res && runTest(2,print,3,6,5,2,false);
		
		res = res && runTest(2,print,3,6,4,4,true);
		
		res = res && runTest(2,print,0,6,1,6,true);
		
		res = res && runTest(2,print,1,6,4,6,false);
		
		res = res && runTest(2,print,1,6,2,6,false);
		
		res = res && runTest(2,print,0,0,5,0,true);
		
		res = res && runTest(2,print,4,1,4,0,true);
		
		res = res && runTest(2,print,0,1,3,0,false);
		
		res = res && runTest(2,print,5,7,6,4,false);
		
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
	 * 2 affiche un plateau plus large fait pour être joué.
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
						
				} else if (option == 2){
					if(tab[y][x] == null){
						System.out.print("0  ");
					} else {
						System.out.print(tab[y][x].getFishNumber());
						if(tab[y][x].occupied()){
							System.out.print("B ");
						} else {
							System.out.print("  ");
						}
					}
				}
			}
			System.out.println();
		}
	}
	
	private int possibilityDirection(int x,int y,int way,boolean direction,int result[][],int index) {
		int coord[] = {-1,-1};
		boolean cond = true;
		coord[0] = x;
		coord[1] = y;
		while(coord != null && cond) {
			coord = nextTile(coord[0],coord[1],way,direction);
			if (coord != null && tab[coord[1]][coord[0]] != null && !tab[coord[1]][coord[0]].occupied()) {
				result[index][0] = coord[0];
				result[index][1] = coord[1];
				index++;
			}
			else {
				cond = false;
			}
		}
		return index;
	}
	
	/**
	 * Determine la liste de coups possible depuis la position donnée.
	 * @param x1 Coordonnée x de la position de départ.
	 * @param y1 Coordonnée y de la position de départ.
	 * @return La liste de coups possibles. Cette liste a le format suivant :
	 * 	res[i][0] : Coordonnée x de la destination du coup n°i.
	 * 	res[j][1] : Coordonnée y de la destination du coup n°i.
	 * 	Les coordonnées valent -1 si i n'est pas un coup.
	 */
	public int[][] movePossibility(int x1, int y1) {
		// création du tableau à rendre
		int result[][] = new int[60][2];
		for(int i = 0 ; i < 60 ; i++){
			for (int j = 0 ; j < 2 ; j++){
				result[i][j] = -1;
			}
		}
		int index = 0;
		
		// coups à l'horizontal partie droit
		index = possibilityDirection(x1,y1,HORIZONTAL_ALIGN,true,result,index);
		
		// coups en antislash partie basse
		index = possibilityDirection(x1,y1,ANTISLASH_ALIGN,true,result,index);
		
		// coups en slash partie basse
		index = possibilityDirection(x1,y1,SLASH_ALIGN,true,result,index);
		
		// coups à l'horizontal partie gauche
		index = possibilityDirection(x1,y1,HORIZONTAL_ALIGN,false,result,index);
		
		// coups en antislash partie haute
		index = possibilityDirection(x1,y1,ANTISLASH_ALIGN,false,result,index);
		
		// coups en slash partie haute
		index = possibilityDirection(x1,y1,SLASH_ALIGN,false,result,index);
		
		for(int i = index ; i < 60 ; i++) {
			result[i][0] = -1;
			result[i][1] = -1;
		}
		
		return result;
	}
	
	private boolean test_movePossibility(boolean print) {
		shuffle();
		boolean result = true;
		
		// test (0,0)
		if(print) {
			System.out.println("debut test (0,0)");
		}

		
		int result_0_0[][] = {{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},{1,1},{1,2},{2,3},{2,4},{3,5},{3,6},{4,7},{0,1}};
		result = result && runTestPossibility(result_0_0,0,0,print);
		
		
		// test (4,3)
		if(print) {
			System.out.println("debut test (4,3)");
		}
		
		int result_4_3[][] = {{5,3},{6,3},{7,3},{4,4},{5,5},{5,6},{6,7},{3,4},{3,5},{2,6},{2,7},{3,3},{2,3},{1,3},{0,3},{3,2},{3,1},{2,0},{4,2},{5,1},{5,0}};
		result = runTestPossibility(result_4_3,4,3,print) && result;
		
		
		// test (2,6)
		if(print) {
			System.out.println("debut test (2,6)");
		}
		
		int result_2_6[][] = {{3,6},{4,6},{5,6},{6,6},{3,7},{2,7},{1,6},{0,6},{2,5},{1,4},{1,3},{0,2},{0,1},{3,5},{3,4},{4,3},{4,2},{5,1},{5,0}};
		result = result && runTestPossibility(result_2_6,2,6,print);

		
		// test (2,6) avec pingouins et trous
		if(print) {
			System.out.println("debut test (2,6) bis");
		}
		occupyWithPenguin(2,7);
		occupyWithPenguin(5,0);
		removeTile(1,3);
		
		int result_2_6_bis[][] = {{3,6},{4,6},{5,6},{6,6},{3,7},{1,6},{0,6},{2,5},{1,4},{3,5},{3,4},{4,3},{4,2},{5,1}};
		result = result && runTestPossibility(result_2_6_bis,2,6,print);
				
		
		return result;
	}
	
	private boolean runTest(int nbFonc, boolean print, int x1 , int y1 , int x2 , int y2 ,boolean boolWanted) {
		boolean boolObtained = true;
		String Fonc = "";
		switch(nbFonc) {
		case 0:
			boolObtained = areAligned(x1,y1,x2,y2) != 0;
			Fonc = "areAligned";
			break;
		case 1:
			boolObtained = isMoveLegit(x1,y1,x2,y2);
			Fonc = "isMoveLegit";
			break;
		case 2:
			boolObtained = makeMove(x1,y1,x2,y2) != null;
			Fonc = "makeMove";
			break;
		default:
			break;
		}
		if(print) {
			System.out.println(boolObtained);
			printTest(nbFonc,x1,y1,x2,y2);
		}
		if(boolWanted != boolObtained) {
			System.out.println(Fonc + "(" + x1 + "," + y1 + "," + x2 + "," + y2 + ") n'a pas eu le bon résultat, recu " + boolObtained + " alors qu'on voulait " + boolWanted );
		}
		return boolWanted == boolObtained;
	}
	
	private boolean runTestNextTile(int wanted[], int way, boolean direction, int x, int y, boolean print) {
		int obtained[] = nextTile(x,y,way,direction);
		if(wanted != null && obtained != null) {
			if(obtained[0] != wanted[0] && obtained[1] != wanted[1]) {
				if(print) {
					System.out.println("attendu x : " + wanted[0] + " y : " + wanted[1] + " ; recu x : " + obtained[0] + " y : " + obtained[1]);
				}
				return false;
			}
			else {
				return true;
			}
		}
		else {
			if(obtained == null && wanted == null) {
				return true;
			}
			else {
				if(wanted == null) {
					if(print) {
						System.out.println("on attendait aucune tuile, or, une tuile a été recu x : " + x + ", y : " + y + ", direction : " + way + " , sens : " + direction);
					}
					return false;
				}
				else {
					if(print) {
						System.out.println("on attendait une tuile, or, aucune tuile n'a été recu x : " + x + ", y : " + y + ", direction : " + way + " , sens : " + direction);
					}
					return false;
				}
			}
		}
	}
	
	private boolean runTestPossibility(int wanted[][],int x, int y, boolean print) {
		int obtained[][] = movePossibility(x,y);
		
		boolean not_end = true;
		boolean equality = true;
		int length = 60;
		for(int i = 0; i < 60 && not_end; i++) {
			if(obtained[i][0] != -1) {
				if(i < wanted.length && obtained[i][0] != wanted[i][0] || obtained[i][1] != wanted[i][1]) {
					if(print) {
						System.out.println("attendu x : " + wanted[i][0] + " y : " + wanted[i][1] + " ; recu x : " + obtained[i][0] + " y : " + obtained[i][1]);
					}
					equality = false;
				}
			}
			else {
				not_end = false;
				length = i;
			}
		}
		equality = equality && wanted.length == length;
		if(!equality) {
			if(print) {
				System.out.println("erreur en (" + x + "," + y +")");
			}
		}
		return equality;
	}
	
	/**
	 * affiche le plateau en fonction du test effectué
	 * @param option détermine quel test est effectué : 1 test alignement, 2 déplacement valide , 3 exécution du déplacement
	 * @param x1 coord x de la tuile 1
	 * @param y1 coord y de la tuile 1
	 * @param x2 coord x de la tuile 2
	 * @param y2 coord x de la tuile 2
	 */
	private void printTest(int option, int x1, int y1 , int x2, int y2) {
		char charac[][][] = {{{'1','1','1'},{'2','2','2'},{'.','.','.'}},{{'V','X','@'},{'V','X','@'},{'O','B','.'}},{{'V','X','@'},{'V','X','@'},{'O','B','.'}}};
		for(int y = 0 ; y < 8 ; y++) {
			if(y % 2 == 0) {
				System.out.print(" ");
			}
			for(int x = 0 ; x < 8 ; x++) {
				if(x == x1 && y == y1) {
					if(tab[y][x] == null) {
						System.out.print(charac[option][0][0]);
					}
					else if(tab[y][x].occupied()) {
						System.out.print(charac[option][0][1]);
					}
					else{
						System.out.print(charac[option][0][2]);
					}
				}
				else if(x == x2 && y == y2) {
					if(tab[y][x] == null) {
						System.out.print(charac[option][1][0]);
					}
					else if(tab[y][x].occupied()) {
						System.out.print(charac[option][1][1]);
					}
					else{
						System.out.print(charac[option][1][2]);
					}
				}
				else {
					if(tab[y][x] == null) {
						System.out.print(charac[option][2][0]);
					}
					else if(tab[y][x].occupied()) {
						System.out.print(charac[option][2][1]);
					}
					else{
						System.out.print(charac[option][2][2]);
					}
				}
				System.out.print(" ");
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
		
		System.out.println("tester la recherche de la prochaine tuile dans une direction et un sens ?(1 ou 0)"); // vérifie la fonction qui cherche la prochaine tuile dans une certaine direction
		if(s.nextInt() == 1){
			System.out.println("afficher les tests ?(1 ou 0)");
			if(s.nextInt() == 1){
				result = test_nextTile(true);
				System.out.println(result);
			}
			else {
				result = test_nextTile(false);
				System.out.println(result);
			}
		}
		
		s.close();
	}
}
