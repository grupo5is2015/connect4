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
        
        return "<html><head><title>Cuatro en Línea</title><script>" +
                     "function validateSubmit() { if ((document.loginForm.email.value == '') || (document.loginForm.password.value.length == 0)) { alert('Debe completar los campos E-mail y Password') } else { document.loginForm.submit() } } </script>" +
                     "</head><body><script>document.bgColor='#dfe3ee'</script>" +
		     "<table style='width:100%; background-color:#dfe3ee'>" + 
                     "<tr><td><center><h1>Cuatro en Línea</h1><hr><font color='#3b5998'><small><strong>Complete los siguientes datos para registrarse</strong></small></font></td><tr><td><center>" +
                     "<form name='loginForm' action='/registration' method='post'><table border='20' bordercolor='#8b9dc3' bgcolor='#3b5998'>" +
                     "<tr><td><table><tr><td align='right'><font color='white'><strong>E-mail:</strong></font></TD>" +
                     "<td align='left'><input type='text' name='email' value='' size='25' color='white'></td></tr>" +
                     "<tr><td align='right'><font color='white'><strong>Password:</strong></font></td>" +
                     "<td align='left'><input type='password' name='password' size='25'></td></tr><tr><td align='right'><font color='white'><strong>Nickname:</strong></font></td>" +
                     "<td align='left'><input type='nickname' name='nickname' size='25'></td></tr>" +
                     "<tr><td colspan='2' align='right'><input type='button' value='Crear Usuario' onclick='validateSubmit()'></td></tr></table></center></td></tr></table>" +
		     "</form></center></body></html>";

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
        //output = 
        //<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        if (activeSession) {
            output = "Sesion Activa de <strong>" + email + "</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            //"<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Frameset//EN' 'http://www.w3.org/TR/html4/frameset.dtd'>
            output = "<html><head><title>Cuatro en Línea</title><script>" +
                     "function validateSubmit() { if ((document.loginForm.email.value == '') || (document.loginForm.password.value.length == 0)) { alert('Debe completar los campos E-mail y Password') } else { var rex=/^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$/; if(!rex.test(document.loginForm.email.value)) { alert('Formato de e-mail no valido') } else { document.loginForm.submit() } } } </script>" +
                     "</head><body><script>document.bgColor='#dfe3ee'</script>" +
		     "<table style='width:100%; background-color:#dfe3ee'>" + 
                     "<tr><td><center><h1>Cuatro en Línea</h1></td><tr><td><center>" +
                     "<form name='loginForm' action='/logincheck' method='post'><table border='20' bordercolor='#8b9dc3' bgcolor='#3b5998'>" +
                     "<tr><td><table><tr><td align='right'><font color='white'><strong>E-mail:</strong></font></TD>" +
                     "<td align='left'><input type='text' name='email' value='' size='25' color='white'></td></tr>" +
                     "<tr><td align='right'><font color='white'><strong>Password:</strong></font></td>" +
                     "<td align='left'><input type='password' name='password' size='25'></td></tr>" +
                     "<tr><td colspan='2' align='right'><input type='button' value='Iniciar Sesión' onclick='validateSubmit()'></td></tr></table></center></td></tr></table>" +
		     "<br>¿No estás registrado?<a href='signin' style='color:green'><strong> Crear Cuenta </strong></a></form></center></body></html>";
        }
        return output;

    }



    public String showPlayersRankings(List<Ranking> ranksList) {

        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        }
        catch (Exception e) {
        }

        String output = "<html><head>"+
                "<link type=\"text/css\" href=\"/style.css\" rel=\"stylesheet\">"+
                //<table><tr><td><table width=\"450\" align='center' border=1>
                "</head><body><h1>Ranking de Jugadores</h1><table><tr><td><table width=\"450\" align='center' border=1>";
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
            output += "<td>" + u.get("nickname") + "</td><td>" + r.get("points") + "</td>";
        }
        output += "</table></td></tr><tr><td>";
        output += "<ul><li><a href='/play/0'> Iniciar nueva partida</a></li> <li><a href='/loadgame'> Cargar partida inconclusa</a></li><li><a href='/logout'>Salir</a></li></ul>";
        output +="</td></tr></table>";
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

        String output = "<html><head>"
                + "<link type=\"text/css\" href=\"/style.css\" rel=\"stylesheet\"></head>"
                + "<body><h1>Juegos Pausados</h1><hr>Seleccione el juego que desea retomar:<br><br><table><tr><td><table width=\"450\" align='center' border=1>";
        output += "<tr><th> Juego </th><th> Adversario </th><th> E-mail </th></tr>";
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
            } else {
                adversary = "player1";
            }
            u = User.findById(g.get(adversary));
            output += "<td>" + u.get("nickName") + "</td><td>" + u.get("email") + "</td></tr>";
        }
        output += "</table></td></tr><tr><td>";
        output += "<hr><ul><li><a href='/play/0'> Iniciar nueva partida </a></li><li><a href='/showrankings'> Listar Rankings</a></li><li><a href='/logout'>Salir</a></li></ul>";
        output += "</td></tr></table></body></html>";

        Base.close();
        return output;

    }
    


    public String savedGameReport(boolean savedGame) {
        
        String output;
        if (savedGame) {
            output = "<strong>Tu adversario ha pausado la partida.</strong><br><small>La misma puede ser retormada mas adelante.</small><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            output = "<strong>Juego guardado exitosamente.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        return output;
        
    }



    public String waitingAdversary(int player, String user) { // Valores 1 ó 2

        String output = "<html><head><meta http-equiv='refresh' content='3' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong>Esperando por el player #" + player + " ...</strong></td></tr>"
                + "<table></html><hr><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        return output;

    }

    
    public String showGame(String user, String player1, String player2, String board) {

        String output = "";

        output = "<html><head><meta http-equiv='refresh' content='4' ><title>4 en Linea</title>"
                +"<link type=\"text/css\" href=\"/style.css\" rel=\"stylesheet\">"
                +"</head><body><center>"
                + "<h1 align='center'>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td class=\".panelvs\"> Partida: <strong><font color='green'>" + player1 + "</font></strong> VS <strong><font color='red'> " + player2 + "</font></strong></td></tr>"
                + "<tr><td>" + board + "</td></tr>"
                + "</table></center></body></html>";

        return output;

    }



    public String showWinner(String user, String ganador) {

        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1><font color='red'>Ganador de la partida: " + ganador + " !!</font></h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;
        
    }

        public String showTieMatch(String user) {
        
        String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1><font color='red'>Partida empatada !!</font></h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;
        
    }



    public String busyGame() {
        
        String output = "<strong>Partida ocupada. Intente nuevamente mas adelante.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        return output;
        
    }
    
    
    public String getPageStyle() {
        String output = "";
        output += "html {font-family: sans-serif;}"
                + "table {\n"
                + "   border: 1px solid #999;\n"
                + "   text-align: center;\n"
                + "   border-collapse: collapse;\n"
                + "   margin: 0 0 1em 0;\n"
                + "   caption-side: top;\n"
                + "}\n"
                + "th, td {\n"
                + "   border-bottom: 1px solid #999;\n"
                + "   width: 60px;\n"
                + "}\n"
                + "td.menuitem {\n"
                + "   font-weight: bold;\n"
                + "   font-style: italic;\n"
                + "};"
                + "td.panelvs {\n"
                + "   font-size: 13px;\n"
                + "};";

        return output;
    }


}
