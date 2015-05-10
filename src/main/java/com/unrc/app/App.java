package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Hello world!
 *
 */
public class App
{
    
    int turn = 0;			// jugador1: turno par, jugador2: turno impar
    public static void main( String[] args )
    {
        System.out.println( "Bienvenidos a 4 en linea" );
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
	
	//Login log = new Login();
	//log.show_login();
           try {
		 int op = menu();
		 //while (op == 1) {  // mientras sea opcion 1, 2 o 3
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		      //FourInLineState st = new FourInLineState();	// nuevo objeto estado concreto
		      //FourInLineProblem p = new FourInLineProblem();	// nuevo objeto problema concreto
                      Board b = new Board(6,7);
                      //System.out.println(b.toString());
                      BoardControl bc =new BoardControl(b);
		      //int depth = op * 3 + 1;	// profundidad por nivel: 4(facil), 7(medio) y 10(dificil)
		      //MaxMinABEngine<FourInLineProblem,FourInLineState> engine = new MaxMinABEngine<FourInLineProblem,FourInLineState>(p,depth);
		      //int[] columnA = new int[7];  // arreglo para almacenar la cantidad de elementos de cada columna
		      int column;
		      int turn = 0;			// jugador: turno par, maquina: turno impar
		      boolean player = false; 	// variable para decicir quien jugo
                      int value = 1;
                                           
		      do {

                          
			    //if (turn%2 == 0) {
				  System.out.println(b.toString()); // muestra Tablero antes de jugar
                                  boolean option1;
				  boolean option2 = false;
				  do { // cicla hasta obtener valor válido
					System.out.print("Ingrese la columna donde desea colocar una ficha: ");
					column = new Integer(br.readLine());
					option1 = (column > 7 || column < 1);
					if (option1)	// si el usuario elije una columna no valida
					     System.out.println("Posicion no valida. Intente nuevamente.");
					else {
					     option2 = ((bc.getColumnTopValue(column-1)) >= 6);
					     if (option2)	// si la columna esta llena
						   System.out.println("\nColumna llena. Intente nuevamente");
					}
				  } while (option1 || option2);
				  column--;
                                  //System.out.println(value + " " + rowCorrection + " " + column);
				  bc.insertCoin(value, column);
                                  //st.setState(column);  // modifica el estado segun la columna que elije el usuario
				  // player = true;
                                  player = !player;
                                  value = value * (-1);
                                  turn++;
                                  //rowCorrection = rowCorrection + 2;
			    } while (!bc.end());
                      System.out.println(b.toString());
                              //(!p.end(st));
		      //System.out.println(st.toString()); 	// muestra Tablero final
		     // if (turn>=(7*6) && (p.value(st)==0)) // si ya se ocuparon las 42 pocisiones y la diferencia es 0
                    if (turn>=(6*7)) 
                        System.out.println("*** Empate ***");
		    else {   // hay un ganador (jugador u maquina)
			    if (player) 	// si gano el jugador
				  System.out.println("*** ¡Ganó el jugador #1! ***");
			    else 		// si gano la maquina
				  System.out.println("*** ¡Ganó el jugador #2! ***");
		      }
		      //engine.report(); 	// informa la cantidad de podas realizadas durante el juego
		   //   op = menu(); 		//  vuelve a  pedir la opcion
		 //}
	   }
	   catch (java.io.IOException ioex) {
		 System.out.println("Error I/O");
	   }
        Base.close();
	System.out.println("Fin de 4 en linea");
     }

     /**
      * Muestra por pantalla el menu y lee por teclado la opcion ingresada por el usuario.
      * @throws IOException cuando el metodo readLine() falla.
      * @pre. true.
      * @post. Muestra por pantalla el menu y lee por teclado la opcion ingresada por el usuario.
      */
     private static int menu() throws IOException {
	   int op = 0;
	   BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
	   System.out.println("\n \t -------------------------------");
	   System.out.println("\t|***** Four In A Line Game *****|");
	   System.out.println("\t --------------------------------");
	   System.out.print("\tPresione 1 para comenzar el juego u otro numero para salir: ");
	   try {
		 op = new Integer(b.readLine());
	   }
	   catch (IOException e) {
		 throw new IOException("Excepcion bufferedReader.readLine()");
	   }
	   return op;
     }


}
