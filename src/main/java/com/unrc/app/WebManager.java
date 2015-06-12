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

    
    public String showRegistrationForm() {
        
        return "Complete los siguientes campos para registrarse:<br><br>"
                + "<form action='/loginreceiver' method='post'>"
                + "E-mail: <input type='text' name='email' value='' /><br><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "Nickname: <input type='text' name='nickname'/><br><br>"
                + "<input type='submit' value='Crear Usuario' >"
                + "</form>";
    }
    
    
    public String showLoginForm() {

        return "Ingrese sus datos de acceso.<br><br>"
                + "<form action='/logincheck' method='post'>"
                + "E-mail: <input type='text' name='email' value='' /><br><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "<input type='submit' value='Acceder' >"
                + "</form>";

    }

    public String waitForPlayer(int player, String user) { // Valores 1 ó 2
        
        String output = "<html><head><meta http-equiv='refresh' content='5' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1> <table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Esperando por el player " + player + "</strong></td></tr>"
                + "<table></html><hr><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
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
    
    
    public String showPausedGames(List<Game> pausedGames, String requesterUserId) {
        
        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        } catch (Exception e) {
        }

        String output = "<html><body><h1>Juegos Pausados</h1><hr>Seleccione el juego que desea retomar:<br><br><table align='left' border=1>";
        output += "<tr><th> Juego </th><th> Adversario </th></tr>"; 
        Game g;
	User u;
        String gameId, adversary;
        Iterator it = pausedGames.iterator();
        while (it.hasNext()) {
            g = (Game) it.next();
            gameId = g.getId().toString();
            output += "<tr><td><a href='/loadgame/" + gameId + "'> #" + gameId + "</a></td>";
            if (requesterUserId.equals(g.get("player1").toString())) {
                adversary = "player2";
            }
            else {
                adversary = "player1";
            }
            u = User.findById(g.get(adversary));
            output += "<td>" + u.get("email") + "</td></tr>";
        }
        output += "</table></body></html><br><br><br><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/logout'>Salir</a>";
        
	Base.close();        
	return output;
        
    }


    public String showWinner(String user, String ganador) { // Valores 1 0 2
        
        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1>4 en linea</h1> <table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Ganador de la partida: " + ganador + "</strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
        
        return output;
        
    }

    
    public User loginCheck(String email, String pass, Login log) {
        
        User u = null;
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        u = log.loginCheck(email, pass);
        Base.close();
        return u;

    }

}
