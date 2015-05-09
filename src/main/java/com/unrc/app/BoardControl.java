package com.unrc.app;



public class BoardControl {

	int [][] board;       //matriz
	int [] columnTop;     //cantidad de fichas que tiene la columna, como maximo 7.
	static final int m=6; //numero de filas
	static final int n=7; //numero de columnas
	//Jugador 1=  1   Jugador 2= -1  El numero 0 representa la celda vacia. 
	// Nota para el sistema la columna empieza en cero y para el usuario en 1
	// Para esto hay que hacer una simple convercion colum + 1




	
	public  BoardControl() {
		board= new int[m][n];
		columnTop= new int[n];
	}


	public boolean hayLugar() {
		boolean haylugar=false;
		for (int i=0; i<n;i++)
			if (columnTop[i]<n) haylugar=true;
		return haylugar;
	}


	public boolean insertCoin(int player_value, int column){
		if (!hayLugar()) System.out.println("Tablero Lleno");
		boolean result=false;
		//Recibe datos de columnas validas: 0..n-1

		if (columnTop[column] < m) {

			board[columnTop[column]][column]= player_value; //1 o -1 
			++columnTop[column];
			result=true;	
		}
		return result;		
	}
        
        	/**
	 * Computa el mayor numero de fichas del mismo tipo alineadas consecutivamente en forma vertical.
	 * @param m matriz representante del estado actual.
	 * @param x valor entero a buscar dentro de m.
	 * @return un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en forma vertical.
	 * pre. true.
	 * post. Un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en forma vertical, es retornado.
	 */
	private static int maxLineV(int[][] board, int x) { // x=1 | x=-1
		int maxV = 0;
		int count = 0;
		int col = 0;
		while (col < board[0].length) {
			int row = board.length-1; // board.length = nro de filas
			count = 0;
			while (row >= 0 && (board[row][col] != 0)) {
				if (board[row][col] != x) {
					maxV = Math.max(maxV, count);
					count = 0;
				}
				else
					count++;
				row--;
			}
			maxV = Math.max(maxV, count);
			col++;
		}
		return maxV;
	}

	/**
	 * Computa el mayor numero de fichas del mismo tipo alineadas consecutivamente en forma horizontal.
	 * @param m matriz representante del estado actual.
	 * @param x valor entero a buscar dentro de m.
	 * @return un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en forma horizontal.
	 * pre. true.
	 * post. Un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en forma horizontal, es retornado.
	 */
	private static int maxLineH(int[][] board, int x) {
		int maxH = 0;
		int count = 0;
		int row = board.length-1;
		while (row >= 0) {
			int col = 0;
			count = 0;
			while (col < board[0].length) {
				if (board[row][col] != x) {
					maxH = Math.max(maxH, count);
					count = 0;
				}
				else
					count++;
				col++;
			}
			maxH = Math.max(maxH, count);
			row--;
		}
		return maxH;
	}

        // Ascendente y descendente en relación al barrido de columnas
        
	/**
	 * Computa el mayor numero de fichas del mismo tipo alineadas consecutivamente en una 
	 * diagonal descendente.
	 * @param m matriz representante del estado actual.
	 * @param x valor entero a buscar dentro de m.
	 * @return un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en una diagonal descendente de m.
	 * pre. true.
	 * post. Un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en una diagonal descendente, es retornado.
	 */
	private static int maxLineDD(int[][] board, int x) {
		int maxDD = 0;
		int count = 0;
		int row = board.length;
		int col = board[0].length;
		for (int n=0; n<row; n++) { // recorrido de la diagonal principal 
			count = 0;		// y la parte inferior del tablero
			for (int i=n, j=0; i<row && j<col; i++, j++) {
				if (board[i][j] != x) {
					maxDD = Math.max(maxDD, count);
					count = 0;
				}
				else {
					count++;
					maxDD = Math.max(maxDD, count);
				}
			}
		}
		for (int j=1; j<col; j++) { // recorrido de la parte superior del tablero
			count = 0;
			for (int i=0; i+j<6; i++) {
				if (board[i][j+i] != x) {
					maxDD = Math.max(maxDD, count);
					count = 0;
				}
				else {
					count++;
					maxDD = Math.max(maxDD, count);
				}
			}
		}
		return maxDD;
	}

	/**
	 * Computa el mayor numero de fichas del mismo tipo alineadas consecutivamente en una 
	 * diagonal ascendente.
	 * @param m matriz representante del estado actual.
	 * @param x valor entero a buscar dentro de m.
	 * @return un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en una diagonal ascendente de m.
	 * pre. true.
	 * post. Un valor entero, representando el mayor numero de fichas del mismo tipo alineadas 
	 * consecutivamente en una diagonal ascendente, es retornado.
	 */
	private static int maxLineDA(int[][] board, int x) {
		int maxDA = 0;
		int count = 0;
		int row = board.length;
		int col = board[0].length;
		for (int n=0; n<row; n++) { // recorrido de la diagonal principal y 
			count = 0;		// la parte inferior del tablero
			for (int i=n, j=col-1; i<row && j>=0; i++, j--) {
				if (board[i][j] != x) {
					maxDA = Math.max(maxDA, count);
					count = 0;
				}
				else {
					count++;
					maxDA = Math.max(maxDA, count);
				}
			}
		}
		for (int n=col-2; n>=0; n--) { // recorrido de la parte superior del tablero
			count = 0;
			for (int i=0, j=n; i<row && j>=0; i++, j--) {
				if (board[i][j] != x) {
					maxDA = Math.max(maxDA, count);
					count = 0;
				}
				else {
					count++;
					maxDA = Math.max(maxDA, count);
				}
			}
		}
		return maxDA;
	}



	public void showBoard() {
		System.out.println("************ Board *************");
		String line = new String();
		for (int rows=m-1; rows >= 0; rows--) {
		  line = "|";
		  for (int cols=0; cols < n; cols++) { // recorro las columnas
		    if (board[rows][cols]>0){ line+=" X |";}
			else if (board[rows][cols]<0) { line+=" O |";}
			else { line+="   |";}
		  }
		  System.out.println(line);
		} //end for rows




	}
 

 }


