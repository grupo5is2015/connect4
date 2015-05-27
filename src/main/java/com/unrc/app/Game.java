/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static org.apache.commons.lang.StringUtils.isNumeric;
import org.javalite.activejdbc.Model;

/**
 *
 * @author slowhand
 */
public class Game extends Model {

    private User player1;
    private User player2;
    private Board table;
    final int numRow = 6;
    final int numCol = 7;
    List movesList = new LinkedList<Pair>();

    public Game(User player1, User player2) {
        this.player1 = player1;  //  jugador que inicia la partida
        this.player2 = player2;
        this.table = new Board(numRow, numCol);
        this.set("finished", false);
        this.set("draw", false);

    }

    public void PlayGame() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //final int numRow = 6;
        //final int numCol =7;
        final int numMoves = numRow * numCol;
        //Board b = new Board(numRow, numCol);
        BoardControl tableControl = new BoardControl(table);
        int column = -1;
        int turn = 0;			// jugador #1: turno par, jugador #2: turno impar
        boolean player = false; 	// variable para decir quien jugo, false = jugador #1 , true = jugador #2
        int value = 1;                  // ficha jugador #1 = 1, ficha jugador #2 = -1 
        boolean leaveGame = false;
        boolean wrongColumn;
        boolean fullColumn;
        do {
            System.out.println(table.toString());   // muestra Tablero antes de jugar
            wrongColumn = false;
            fullColumn = false;
            do {    // cicla hasta obtener valor válido
                System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                String s = br.readLine();
                if (s.toLowerCase().equals("g")) {
                    leaveGame = true;
                    this.saveGame(leaveGame);
                } else {
                    while (!isNumeric(s)) {
                        System.out.println("\n\t ¡¡¡ Debe ingresar un numero !!! \n");
                        System.out.print("Ingrese la columna donde desea colocar una ficha: ");
                        s = br.readLine();
                    }
                    column = new Integer(s);
                    wrongColumn = (column > numCol || column < 1);
                    if (wrongColumn) {    // si el usuario elije una columna no valida
                        System.out.println("Posicion no valida. Intente nuevamente...");
                    } else {
                        fullColumn = ((tableControl.getColumnTopValue(column - 1)) >= numRow);
                        if (fullColumn) // si la columna esta llena
                        {
                            System.out.println("\nColumna llena. Intente nuevamente...");
                        }
                    }
                }
            } while (!leaveGame && (wrongColumn || fullColumn));
            if (!leaveGame) {
                column--;
                Pair p = new Pair(value, column);   // 1: player1, -1: player2;
                movesList.add(p);
                tableControl.insertCoin(value, column);
                player = !player;
                value = value * (-1);
                turn++;
            }
        } while (!leaveGame && ((!tableControl.isTheVictor(player)) && (turn < numMoves))); // opcional pero mas costoso: ( (!bc.isTheVictor(player)) || (!bc.fullBoard()) )
        this.saveIt(); //Para garantizar que se creo.

        if (!leaveGame) {
            this.set("finished", true);
            this.set("draw", false);

            System.out.println(table.toString());
            if (turn >= numMoves) {  // empate o ganó el jugador #2 en la última jugada
                if (tableControl.isTheVictor(player)) {
                    System.out.println("*** ¡Ganó el jugador #2! ***");
                    player2.add(this);
                } else {
                    System.out.println("*** Empate ***");
                    this.set("draw", true);
                }
            } else {   // hay un ganador

                if (player) {
                    System.out.println("*** ¡Ganó el jugador #1! ***");
                    player1.add(this);
                } else {
                    System.out.println("*** ¡Ganó el jugador #2! ***");
                    player2.add(this);
                }
            }
            this.saveIt(); //Actualizar
        } // fin leaveGame

    }

    private void saveGame(boolean isNew) {

        this.saveIt();
        /*
         this.add(player1);
         this.add(player2);
         Integer id = (Integer) player1.getId();
         table.set("user_id", id);
         this.add(table);
        
         Pair p = new Pair();
         Iterator i = movesList.iterator();
         while (i.hasNext()) {
         Move m = new Move();
         p = (Pair) i.next();
         m.set("numCol", p.getColumnSelected());
         table.add(m);
            
         if (p.getNumPlayer() == 1) {
         player1.add(m);
         }
         else {
         player2.add(m);
         }

         }
         */
        if (isNew) {
            this.add(player1);
            this.add(player2);
            this.table.save();
            this.add(this.table);
            this.player1.add(this.table);
            Pair p = new Pair();
            Iterator i = movesList.iterator();
            while (i.hasNext()) {
                Move m = new Move();
                p = (Pair) i.next();
                m.set("numCol", p.getColumnSelected());
                table.add(m);

                if (p.getNumPlayer() == 1) {
                    player1.add(m);
                } else {
                    player2.add(m);
                }

            }
        }

    }

}
