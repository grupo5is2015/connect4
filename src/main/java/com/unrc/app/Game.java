package com.unrc.app;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class Game extends Model {

    private User player1;
    private User player2;
    public Board table;
    public final int numRow = 6;
    public final int numCol = 7;
    public List<Pair> movesList;
    public int turnOff = 1;     // Empieza el player1 - Valores: 1 | -1
    public boolean player1Aware;
    public String winnerName;
    public int moveNumber;
    public boolean pausedGame;
    
    
    public Game() {
        this.movesList = new LinkedList<Pair>();
    }


    public Game(User player1, User player2) {
        this.movesList = new LinkedList<Pair>();
        this.player1 = player1;
        this.player2 = player2;
        this.table = new Board(numRow, numCol);
        this.set("finished", false);
        this.set("draw", false);
        this.player1Aware = false;
        this.winnerName = "";
        this.moveNumber = 1;
        this.pausedGame = false;
    }


    public void settleGame(User player1, User player2, int moveNumber) {
        
        this.player1 = player1;
        this.player2 = player2;
        this.table = new Board(numRow, numCol);
        this.turnOff = 1;
        this.moveNumber = moveNumber;

    }


    public void settleMovesList(List<Move> moves, BoardControl boardControl) {

        Move move;
        int current = 1;
        int column = 0;
        Iterator it = moves.iterator();
        while (it.hasNext()) {
            move = (Move) it.next();
            column = ((Integer) move.get("numCol")).intValue();
            registerMove(current, column);
            boardControl.insertCoin(current, column);
            current *= -1;
        }
        this.turnOff = current;

    }


    public void settleUser() { // prepara el game para ser guardado 

        this.set("player1", this.player1.get("id"));
        this.set("player2", this.player2.get("id"));

    }


    public void registerMove(int player, int column) {
        
        Pair p = new Pair(player, column);   // 1: player1, -1: player2;
        this.movesList.add(p);
        
    }

    
    public String boardToHtml(boolean turn) {

        String s;
        if (turnOff == 1) {
            s = player1.get("email").toString();
        }
        else {
            s = player2.get("email").toString();
        }
        return "Turno de:  <strong>" + s + "</strong>" + table.toHtml(turn);

    }


    public void saveGame() {

        Pair p = new Pair();
        long movesPreviouslySaved = Move.count("game_id = ?", this.getId().toString());
        int x = 0;
        Iterator i = movesList.iterator();

        while (i.hasNext() && x < movesPreviouslySaved) {   // SALTEO MOVIMIENTOS YA ALMACENADOS POR UN "GUARDAR" ANTERIOR A ESTE
            i.next();
            x++;
        }

        while (i.hasNext()) {   // ALMACENO LOS NUEVOS MOVIMIENTOS
            Move m = new Move();
            p = (Pair) i.next();
            m.set("numCol", p.getColumnSelected());
            this.add(m);

            if (p.getNumPlayer() == 1) {
                player1.add(m);
            }
            else {
                player2.add(m);
            }
        }

    }


}
