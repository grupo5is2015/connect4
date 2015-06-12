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
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/**
 *
 * @author Grupo #5
 */
public class Game extends Model {

    private User player1;
    private User player2;
    public Board table;
    public final int numRow = 6;
    public final int numCol = 7;
    public List<Pair> movesList;
    public int turnOff = 1; // Empieza el player1 valores (1,-1)

    
    public Game(User player1, User player2) {
        this.movesList = new LinkedList<Pair>();
        this.player1 = player1;  //  jugador que inicia la partida
        this.player2 = player2;
        this.table = new Board(numRow, numCol);
        this.set("finished", false);
        this.set("draw", false);
    }

    
    public void settleGame(User player1, User player2) {
        this.player1 = player1;  //  jugador que inicia la partida
        this.player2 = player2;
        this.table = new Board(numRow, numCol);
        turnOff = 1;
    }

    
    public void settleMovesList(List<Move> moves, BoardControl boardControl) {

        Iterator it = moves.iterator();
        Move move;
        int current = 1;
        int column = 0;
        while (it.hasNext()) {
            move = (Move) it.next();
            column = ((Integer) move.get("numCol")).intValue();
            regMove(current, column);
            boardControl.insertCoin(current, column);
            current *= -1;
        }

        turnOff = current;

    }

    public Game() {
        this.movesList = new LinkedList<Pair>();
    }

    public void settleUser() { // prepara el game para ser guardado 

        this.set("player1", this.player1.get("id"));
        this.set("player2", this.player2.get("id"));

    }

    
    public Object create() {
        return new Game();
    }

    public void regMove(int player, int column) {
        Pair p = new Pair(player, column);   // 1: player1, -1: player2;
        this.movesList.add(p);
    }

    
    public String boardToHtml(boolean turn) {

        String s = "";
        if (turnOff == 1) {
            s = player1.get("email").toString();
        } else {
            s = player2.get("email").toString();
        }
        return "Turno de:  <strong>" + s + "</strong>" + table.toHtml(turn);

    }

    public void saveGame(boolean isNew) {

        this.saveIt();

        if (isNew) {
            /* Al juego ya tiene los dos jugadores seteados */
            this.settleUser();

            Pair p = new Pair();
            Iterator i = movesList.iterator();
            while (i.hasNext()) {
                Move m = new Move();
                p = (Pair) i.next();
                m.set("numCol", p.getColumnSelected());
                this.add(m);

                if (p.getNumPlayer() == 1) {
                    player1.add(m);
                } else {
                    player2.add(m);
                }

            }

        }

    }

}
