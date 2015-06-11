/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import java.util.Iterator;
import java.util.List;
import org.javalite.activejdbc.Base;

/**
 *
 * @author slowhand
 */
public class WebManager {

    public WebManager() {
    }

    public String toString() {
        return "";
    }

    public String ShowLogin() {

        return "Ingrese sus datos.<br><br>"
                + "<form action='/logincheck' method='post'>"
                + "E-mail: <input type='text' name='email' value='' /><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "<input type='submit' value='Enviar' >"
                + "</form>";

    }

    public String waitForPlayer(int player, String user) { // Valores 1 0 2
        String output = "<html><head><meta http-equiv='refresh' content='5' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1> <table><tr><td>Estas logueado como <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Esperando por el player " + player + "</strong></td></tr>"
                + "<table></html>";
        return output;
    }

    public String showGame(String user, String player1, String player2, String board) {

        String output = "";

        output = "<html><head><meta http-equiv='refresh' content='8' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en linea</h1> <table><tr><td>Estas logueado como <strong>" + user + " </strong></td></tr>"
                + "<tr><td bgcolor='yellow'>" + player1 + " vs " + player2 + "</td></tr>"
                + "<tr><td>" + board + "</td></tr>"
                + "</table></body></html>";

        return output;

    }
    
    public String showPausedGames(List<Game> pausedGames, boolean player) {
		        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        } catch (Exception e) {
        }

        String output = "<html><body><h1>Juegos Pausados</h1><table align='left' border=1><tr>";
        Game g;
	User u;
        String i, adversary;
        Iterator it = pausedGames.iterator();
        while (it.hasNext()) {
            g = (Game) it.next();
            i = g.getId().toString();
            output += "<td><a href='/loadgame/" + i + "'> #" + i + "</a></td>";
            if (player) {
                adversary = "player2";
            }
            else {
                adversary = "player1";
            }
            u = User.findById(g.get(adversary));
            output += "<td>" + u.get("email") + "</td></tr>";
        }
        output += "</table></body></html>";
		Base.close();        
		return output;
    }


    public String showWinner(String user, String ganador) { // Valores 1 0 2
        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1>4 en linea</h1> <table><tr><td>Estas logueado como <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Ganador de la partida " + ganador + "</strong></td></tr>"
                + "<tr><td><strong> <a href='/play/0'> Jugar de nuevo </a></strong></td></tr>"
                + "<table></html>";
        return output;
    }

    public User logincheck(String email, String pass, Login log) {
        User u = null;
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        u = log.loginCheck(email, pass);
        Base.close();
        return u;

    }

}
