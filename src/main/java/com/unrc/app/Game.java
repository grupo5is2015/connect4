/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static org.apache.commons.lang.StringUtils.isNumeric;
import org.javalite.activejdbc.Model;

/**
 *
 * @author slowhand
 */
public class Game extends Model {
    
           public void PlayGame() throws IOException {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                final int numRow = 6;
                final int numCol =7;
                final int numMoves = numRow * numCol;
                Board b = new Board(numRow, numCol);
                BoardControl bc = new BoardControl(b);
                int column;
                int turn = 0;			// jugador #1: turno par, jugador #2: turno impar
                boolean player = false; 	// variable para decir quien jugo, false = jugador #1 , true = jugador #2
                int value = 1;                  // ficha jugador #1 = 1, ficha jugador #2 = -1 

                boolean wrongColumn;
                boolean fullColumn;
                do {
                    System.out.println(b.toString());   // muestra Tablero antes de jugar
                    wrongColumn = false;
                    fullColumn = false;
                    do {    // cicla hasta obtener valor válido
                        System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                        String s = br.readLine();
                        while (! isNumeric(s)) {
                            System.out.println("\n\t ¡¡¡ Debe ingresar un numero !!! \n");
                            System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                            s = br.readLine();
                        }
                        column = new Integer(s);
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

    }
}
