package com.unrc.app;

import com.unrc.app.User;
import com.unrc.app.Login;
import com.unrc.app.Ranking;
import org.javalite.activejdbc.Base;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

 /**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Bienvenidos a 4 en linea");
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");

	 	Login log = new Login();
		User player1 = log.loginCheck("martin@gmail.com","martin");
		User player2 = log.loginCheck("pepe@gmail.com","pepe");
		Ranking rank1;
		Ranking rank2;
		List <Ranking> ranks;

		if (player1==null) { // Si no Existe el Player1 Lo creo
			 player1= User.create("first_name","martin","email","martin@gmail.com","password","martin");
			 player1.save();
			 rank1 = new Ranking();
			 rank1.set("won",0,"tie",0,"lost",0);
			 player1.add(rank1);

		}
		if (player2==null) {
			 player2= User.create("first_name","pepe","email","pepe@gmail.com","password","pepe");
			 player2.save();
 			 rank2 = new Ranking();
			 rank2.set("won",0,"tie",0,"lost",0);
			 player2.add(rank2);
		}

		ranks =  player1.getAll(Ranking.class);
		rank1=ranks.get(0);
		ranks =  player2.getAll(Ranking.class);
		rank2=ranks.get(0); //Aqui obtengo el ranking de cada Jugador.



	
			






	int op=0;	

       try {

	 op = menu();

            while (op == 1) {  // mientras sea opcion 1
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                int numRow = 6;
                int numCol =7;
                int numMoves = numRow * numCol;
                Board b = new Board(numRow, numCol);
                BoardControl bc = new BoardControl(b);
                int column;
                int turn = 0;			// jugador #1: turno par, jugador #2: turno impar
                boolean player = false; 	// variable para decir quien jugo, false = jugador #1 , true = jugador #2
                int value = 1;                  // ficha jugador #1 = 1, ficha jugador #2 = -1 
		
		boolean wrongColumn;
                boolean fullColumn=false;
                do {
                    System.out.println(b.toString());   // muestra Tablero antes de jugar
                    
                    do {    // cicla hasta obtener valor válido
                        System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                        column = new Integer(br.readLine());
                        wrongColumn = (column > numCol || column < 1);
                        if (wrongColumn) {    // si el usuario elije una columna no valida
                            System.out.println("Posicion no valida. Intente nuevamente...");
                        }
                        else {
                            fullColumn = ((bc.getColumnTopValue(column - 1)) >= numRow);
                            if (fullColumn) // si la columna esta llena
                            {
                                System.out.println("\nColumna llena. Intente nuevamente...");
                            }
                        }
                    } while (wrongColumn || fullColumn);

                    column--;
                    bc.insertCoin(value, column);
                    player = !player; //alterna jugador
                    value *= (-1);
                    turn++;
                } while ( (!bc.isTheVictor(player)) && (turn < numMoves) ); 
		// opcional pero mas costoso: ( (!bc.isTheVictor(player)) || (!bc.fullBoard()) )
                System.out.println(b.toString());

                if (turn >= numMoves) {  // empate o ganó el jugador #2 en la última jugada
                    if (bc.isTheVictor(player)) {
                        System.out.println("*** ¡Ganó el jugador #2! ***");
                    } 
                    else {
                        System.out.println("*** Empate ***");
                    }
                } else {   // hay un ganador
                    if (player) {
                        System.out.println("*** ¡Ganó el jugador #1! ***");
                    }
                    else {
                        System.out.println("*** ¡Ganó el jugador #2! ***");
                    }
                }
                op = menu(); 	//  vuelve a solicitar la opcion
            }
        } catch (java.io.IOException ioex) {
            System.out.println("Error I/O");
        }
        Base.close();
        System.out.println("Fin de 4 en linea");
    }

    /**
     * Muestra por pantalla el menu y lee por teclado la opcion ingresada por el
     * usuario.
     *
     * @throws IOException cuando el metodo readLine() falla.
     * @pre. true.
     * @post. Muestra por pantalla el menu y lee por teclado la opcion ingresada
     * por el usuario.
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
        } catch (IOException e) {
            throw new IOException("Excepcion bufferedReader.readLine()");
        }
        return op;
    }

}

