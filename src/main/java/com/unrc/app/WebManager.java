package com.unrc.app;

import java.util.Iterator;
import java.util.List;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class WebManager {

    
    public WebManager() {
        
    }

    
    public String showLoginForm() {

        return "Ingrese sus datos de acceso:<br><br>"
                + "<form action='/logincheck' method='post'>"
                + "E-mail: <input type='text' name='email' value=''/><br><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "<input type='submit' value='Acceder'>"
                + "</form>";

    }
    
    
    
    public String showRegistrationForm() {
        
        return "Complete los siguientes campos para registrarse:<br><br>"
                + "<form action='/registration' method='post'>"
                + "E-mail: <input type='text' name='email' value=''/><br><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "Nickname: <input type='text' name='nickname'/><br><br>"
                + "<input type='submit' value='Crear Usuario'>"
                + "</form>";

    }
    
    
    public User loginCheck(String email, String pass, Login log) {

        User u = null;
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        u = log.loginCheck(email, pass);
        Base.close();
        return u;

    }    


    public String loginReport(boolean logOK, String email) {
        
        String output; 
        if (logOK) {
            output = "Hola <strong>" + email + "</strong>, has ingresado correctamente!<hr><a href='/play/0'> Iniciar nueva partida</a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            output = "<strong>Datos de acceso incorrectos!</strong><hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a><br><br>";
        }
        return output;

    }



    public String registrationReport(boolean regOK, String email) {
        
        String output; 
        if (regOK) {
            output = "Felicitaciones! Registracion exitosa.";
        }
        else {
            output = "El e-mail <strong>" + email + "</strong> ya se encuentra registrado.";
        }
        output += "<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
        return output;

    }



    public String welcomePage(boolean activeSession, String email) {
        
        String output; 
        if (activeSession) {
            output = "Sesion Activa de <strong>" + email + "</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            output = "<h1>Bienvenido a Cuatro en Linea</h1><hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
        }
        return output;

    }



    public String showPlayersRankings(List<Ranking> ranksList) {

        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        }
        catch (Exception e) {
        }

        String output = "<html><body><h1>Ranking de Jugadores</h1><table align='left' border=1>";
        output += "<tr><th> Posicion </th><th> Jugador </th><th> Puntos </th></tr>";
	User u;
        Ranking r;
        String gameId, adversary;

        Iterator it = ranksList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            r = (Ranking) it.next();
            output += "<tr><td>" + i + "</td>";
            u = User.findById(r.get("user_id"));
            output += "<td>" + u.get("nickname") + "</td><td>" + r.get("points") + "</tr>";
        }
        output += "</table><br><br><br><br>";
        for (int j=1; j<=i; j++) {
            output += "<br>";
        }
        output += "<hr><a href='/play/0'> Iniciar nueva partida</a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
        output += "</body></html>";

	Base.close();        
	return output;
        
    }



    public String goodbyeMessage(String email) {
        
        String output = "Hasta luego <strong>" + email + "</strong>. Sesion finalizada.<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
        return output;
    }



    public String showPausedGames(List<Game> pausedGames, String requesterUserId) {
        
        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        }
        catch (Exception e) {
        }

        String output = "<html><body><h1>Juegos Pausados</h1><hr>Seleccione el juego que desea retomar:<br><br><table align='left' border=1>";
        output += "<tr><th> Juego </th><th> Adversario </th><th> E-mail </th></tr>"; 
        Game g;
	User u;
        String gameId, adversary;
        Iterator it = pausedGames.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
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
            output += "<td>" + u.get("nickName") + "</td><td>" + u.get("email") + "</td></tr>";
        }
        output += "</table><br><br><br><br>";
        for (int j=1; j<=i; j++) {
            output += "<br>";
        }
        output += "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        output += "</body></html>";

	Base.close();        
	return output;

    }
    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String waitForPlayer(int player, String user) { // Valores 1 ó 2
        
        String output = "<html><head><meta http-equiv='refresh' content='3' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Esperando por el player " + player + "</strong></td></tr>"
                + "<table></html><hr><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        return output;
        
    }

    
    public String showGame(String user, String player1, String player2, String board) {

        String output = "";

        output = "<html><head><meta http-equiv='refresh' content='4' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como <strong>" + user + " </strong></td></tr>"
                + "<tr><td bgcolor='yellow'> Partida: <strong>" + player1 + "</strong> VS <strong> " + player2 + "</strong></td></tr>"
                + "<tr><td>" + board + "</td></tr>"
                + "</table></body></html>";

        return output;

    }
    
    



    

    
    public String showWinner(String user, String ganador) { // Valores 1 0 2
        
        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Ganador de la partida: " + ganador + "</strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;
        
    }

        public String showTieMatch(String user) { // Valores 1 0 2
        
        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong> Partida Empatada !!</strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;
        
    }


}
