/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import java.util.Iterator;
import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;

/**
 *
 * @author slowhand
 */
public class GameManagement {

    public static List<Game> listPausedGames(String playerId) {
        //String s = "";
        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        } catch (Exception e) {
        }

        List<Game> pausedGames = Game.where("finished = ? and (player1 = ? or player2 = ?)", false, playerId, playerId);
        /*
        Iterator it = pausedGames.iterator();
        while (it.hasNext()) {
            int id = ((Integer) ((Game) it.next()).getId()).intValue();
            s = s + "->" + id + "<- ";
        }
        */
        Base.close();
        //return s;
        return pausedGames;
    }
    
    public static void loadGame(String gameId) {
        
    }

}
