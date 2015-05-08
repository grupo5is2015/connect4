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


