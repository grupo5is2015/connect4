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
public class App {

    public static void main(String[] args) {
        System.out.println("Bienvenidos a 4 en linea");
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");

        try {
            int op = menu();
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

                do {
                    System.out.println(b.toString());   // muestra Tablero antes de jugar
                    boolean option1;
                    boolean option2 = false;
                    do {    // cicla hasta obtener valor válido
                        System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                        column = new Integer(br.readLine());
                        option1 = (column > numCol || column < 1);
                        if (option1) {    // si el usuario elije una columna no valida
                            System.out.println("Posicion no valida. Intente nuevamente...");
                        }
                        else {
                            option2 = ((bc.getColumnTopValue(column - 1)) >= numRow);
                            if (option2) // si la columna esta llena
                            {
                                System.out.println("\nColumna llena. Intente nuevamente...");
                            }
                        }
                    } while (option1 || option2);
                    column--;
                    bc.insertCoin(value, column);
                    player = !player;
                    value = value * (-1);
                    turn++;
                } while ( (!bc.isTheVictor(player)) && (turn < numMoves) ); // opcional pero mas costoso: ( (!bc.isTheVictor(player)) || (!bc.fullBoard()) )
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
