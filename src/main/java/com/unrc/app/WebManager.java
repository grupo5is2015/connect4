package com.unrc.app;

import java.util.Iterator;
import java.util.List;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Grupo #5: Ontivero - Rondeau - Zabala
 *
 */
public class WebManager {

    
    public WebManager() {
        
    }

    
    public String welcomePage(boolean activeSession, boolean wrongLogin, String email) {
        
        String output; 
        //output = 
        //<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        if (activeSession) {
            output = optionsScreen(false,"Sesion Activa de <strong>" + email + "</strong>", true, true, true, false, false, true);//"Sesion Activa de <strong>" + email + "</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            //"<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Frameset//EN' 'http://www.w3.org/TR/html4/frameset.dtd'>
            output = "<html><head><title>Cuatro en Línea</title><style type=\"text/css\">" +
" #Registrate { " +
"   background-Color:#5F944B;" +
"   color:white;" +
"   font-weight:bold;" +
"   width: 100px;" +
"   height: 25px;" +
"}" +
"</style><script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>" +
		     "<script> $(document).ready(function() { var pageBody = $(document.body); pageBody.css('zoom', '200%'); pageBody.css('background-color', '#dfe3ee'); "+ 
                     "if ("+wrongLogin+") {alert('Datos de acceso incorrectos. Intente nuevamente.'); }}); </script> " +
                     "<script>function validateSubmit() { if ((document.loginForm.email.value == '') || (document.loginForm.password.value.length == 0)) { alert('Debe completar los campos E-mail y Password') } else { var rex=/^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$/; if(!rex.test(document.loginForm.email.value)) { alert('Formato de e-mail no valido') } else { document.loginForm.submit() } } } </script>" +
                     "</head><body>" + //<script>document.bgColor='#dfe3ee';</script> 
                     
		     "<table style='width:100%; background-color:#dfe3ee'>" + 
                     "<tr><td><center><h1>Cuatro en Línea</h1></td><tr><td><center>" +
                     "<form name='loginForm' action='/logincheck' method='post'><table border='20' bordercolor='#8b9dc3' bgcolor='#3b5998'>" +
                     "<tr><td><table><tr><td align='right'><font color='white'><strong>E-mail:</strong></font></TD>" +
                     "<td align='left'><input type='text' name='email' value='' size='25' color='white'></td></tr>" +
                     "<tr><td align='right'><font color='white'><strong>Password:</strong></font></td>" +
                     "<td align='left'><input type='password' name='password' size='25'></td></tr>" +
                     "<tr><td colspan='2' align='right'><input type='button' value='Iniciar Sesión' onclick='validateSubmit()'></td></tr></table></center></td></tr></table>" +
		     "<br>¿No tienes cuenta?   <input id='Registrate' value='Registrate' onClick=\"document.location.href='/signin'\" type='button' />" +
                     //"<script> if ("+wrongLogin+") {alert('Datos de acceso incorrectos. Intente nuevamente.');}</script>" +
                     "</form></center></body></html>"; //<a href='signin' style='color:green'><strong> Crear Cuenta </strong></a>
        }
        return output;

    }

    

/*
    public String showLoginForm() {

        return "Ingrese sus datos de acceso:<br><br>"
                + "<form action='/logincheck' method='post'>"
                + "E-mail: <input type='text' name='email' value=''/><br><br>"
                + "Password: <input type='password' name='password'/><br><br>"
                + "<input type='submit' value='Acceder'>"
                + "</form>";

    }
*/    
    
    
    public String showRegistrationForm() {
        
        return       "<html><head><title>Cuatro en Línea</title><script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>" +
		    	 			"<script> $(document).ready(function() { var pageBody = $(document.body) ; pageBody.css('zoom', '200%'); pageBody.css('background-color', '#dfe3ee'); }) </script> " +
							"<script>function validateSubmit() { if ((document.registrationForm.email.value == '') || (document.registrationForm.password.value.length == 0)) { alert('Debe completar los campos E-mail y Password') } else { var rex=/^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$/; if(!rex.test(document.registrationForm.email.value)) { alert('Formato de e-mail no valido') } else { document.registrationForm.submit() } } } </script>" +
                     "</head><body>" + //<script>document.bgColor='#dfe3ee'</script>" +
		     "<table style='width:100%; background-color:#dfe3ee'>" + 
                     "<tr><td><center><h1>Cuatro en Línea</h1><hr><font color='#3b5998'><small><strong>Complete los siguientes campos para registrarse:</strong></small></font><hr></td><tr><td><center>" +
                     "<form name='registrationForm' action='/registration' method='post'><table border='20' bordercolor='#8b9dc3' bgcolor='#3b5998'>" +
                     "<tr><td><table><tr><td align='right'><font color='white'><strong>E-mail:</strong></font></td>" +
                     "<td align='left'><input type='text' name='email' value='' size='25' color='white'></td></tr>" +
                     "<tr><td align='right'><font color='white'><strong>Password:</strong></font></td>" +
                     "<td align='left'><input type='password' name='password' size='25'></td></tr><tr><td align='right'><font color='white'><strong>Nickname:</strong></font></td>" +
                     "<td align='left'><input type='text' name='nickname' size='25'></td></tr>" +
                     "<tr><td colspan='2' align='right'><input type='button' value='Crear Usuario' onclick='validateSubmit()'></td></tr></table></center></td></tr></table>" +
		     "</form></center></body></html>";

    }
    


    public String loginReport(boolean logOK, String email) {
        
        String output;
        if (logOK) {
            output = optionsScreen(false,"Hola <strong>" + email + "</strong>, has ingresado correctamente!", true, true, true, false, false, true);//<hr><a href='/play/0'> Iniciar nueva partida</a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            //output = "<strong>Datos de acceso incorrectos!</strong><hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a><br><br>";
            output = welcomePage(false, true, ""); // 2do parametro = wrongLogin
        }
        return output;

    }
   
/*    
    public String newLoginReport(boolean logOK, String email) {
        
        String output;
        if (logOK) {
            output = "<!DOCTYPE html><html><head><title>Cuatro en Línea</title><meta name='viewport' content='width=device-width, initial-scale=1'>" +
		     "<link rel='stylesheet' href='http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css'>" +
		     "<script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>" +
		     "<script src='code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js'></script>" +
		     "<script> $(document).ready(function() { var pageBody = $(document.body) ; pageBody.css('zoom', '200%'); pageBody.css('background-color', '#dfe3ee'); }) </script></head>" +
                     "<body>" + //<script>document.bgColor='#dfe3ee'</script>" +
		     //"<table style='width:100%; background-color:#dfe3ee'>" + 
                     //"<tr><td> 
                     "<center><h1>Cuatro en Línea</h1><hr><font color='#3b5998'><small>Hola <strong>" + email + "</strong>, has ingresado correctamente!</small></font><hr>"+ //</td><tr><td>
                     "<center>" +

  "<div data-role='main' class='ui-content'>"+
    "<a href='#' class='ui-btn ui-icon-arrow-r ui-btn-icon-right'>Iniciar nueva partida</a>"+
    "<a href='#' class='ui-btn ui-icon-back ui-btn-icon-right'>Reanudar partida inconclusa</a>"+
    "<a href='#' class='ui-btn ui-icon-info ui-btn-icon-right'>Ver Rankings</a>"+
    "<a href='#' class='ui-btn ui-icon-delete ui-btn-icon-right'>Salir</a>"+
  "</div>"+


		     "</center></body></html>";
        }
        else {
            output = welcomePage(false, true, ""); // 2do parametro = wrongLogin
        }
        return output;

    }
*/

    public String registrationReport(boolean regOK, String email) {
        
        String output;
        if (regOK) {
            output = optionsScreen(false,"Felicitaciones <strong>" + email + "</strong>! Registracion exitosa.", false, false, false, true, true, false);//"Felicitaciones! Registracion exitosa.";
        }
        else {
            output = optionsScreen(false,"El e-mail <strong>" + email + "</strong> ya se encuentra registrado.", false, false, false, true, true, false);//"El e-mail <strong>" + email + "</strong> ya se encuentra registrado.";
        }
        //output += "<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
        return output;

    }



public String showPlayersRankings(List<Ranking> ranksList) {

        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        }
        catch (Exception e) {
        };

        String output =  

                    "<!DOCTYPE html>"
                    +"<html><head>"

                    +"<style type='text/css'>"


                    +"#header {"
                    +"      background-color:#3b5998;"
                    +"      color:white;"
                    +"      text-align:center;"
                    +"      padding:10px;"
                    +"}"

                    +" #buttongraphic { " 
                    +"   background-Color:#8b9dc3;" 
                    +"   color:#ffffff;" 
                    +"   font-weight:bold;" 
                    +"   width: 300px;" 
                    +"   height: 25px;"
                    +"   border-radius: 24px 24px 24px 24px;"
                    +"   -moz-border-radius: 24px 24px 24px 24px;"
                    +"   -webkit-border-radius: 24px 24px 24px 24px;"
                    +"}"

                    +"#buttonfield {"
                    +"    line-height:25px;"
                    +"    background-color:#dfe3ee;"
                    +"    text-align:center;"
                    +"    padding:5px;"       
                    +"}"

                    +"#ranking {"
                    +"      background-color:#dfe3ee;"
                    +"      color:#3b5998;"
                    +"      text-align:center;"
                    +"      padding:8px;"
                    +"}"


                    +"#table {"
                    +"      color:#3b5998;"
                    +"      text-align:center;"
                    +"}"


                    +"</style>"
                    +"<script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>"
                    +"<script> $(document).ready(function() { var pageBody = $(document.body) ; pageBody.css('zoom', '150%'); pageBody.css('background-color', '#dfe3ee'); }); </script> "
                    +"</head>"


                    +"<body>"

                    +"<div id='header'>"
                    +"<h1>Cuatro en Línea</h1>"
                    +"</div>"

                    +"<div id='ranking'>"
                    +"<hr><h2>Ranking de Jugado"
                + "res  </h2></hr>"
                    +"<hr> </hr>"
                    +"</div>"

                    +"<center><div id='buttonfield'>"
                    +"<input type='button' id='buttongraphic' value='VOLVER' onClick=\"document.location.href='/'\">"
                    +"</div></center>"

                    +"<div id= 'table'>"
                    +"<br><center><table><tr><td><table width=\"450\" border=1></br>"
                    +"<tr><th> Posicion </th><th> Jugador </th><th> Puntos </th></tr>";

                    

                    

        User u;
        Ranking r;
        String gameId, adversary;

        Iterator it = ranksList.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            r = (Ranking) it.next(); 
            output += "<tr><td><center>" + i + "</center></td>";
            u = User.findById(r.get("user_id"));
            output += "<td><center>" + u.get("email") + "</center></td><td><center>" + r.get("points") + "</center></td>";

        };

        
            output += "</table></center></td></tr><tr><td>"
            +"</body>"
            +"</html>";


    Base.close();
    return output;
        
    }



    public String goodbyeMessage(String email) {
        
        String output = optionsScreen(false,"Hasta luego <b>" + email + "</b>, has salido correctamente!", false, false, false, true, true, false);//"Hasta luego <strong>" + email + "</strong>. Sesion finalizada.<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
        return output;
    }



    public String showPausedGames(List<Game> pausedGames, String requesterUserId) {

        try {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        } 
        catch (Exception e) {
        }


         String output =  

                    "<!DOCTYPE html>"
                    +"<html><head>"

                    +"<style type='text/css'>"


                    +"#header {"
                    +"      background-color:#3b5998;"
                    +"      color:white;"
                    +"      text-align:center;"
                    +"      padding:10px;"
                    +"}"

                    +" #buttongraphic { " 
                    +"   background-Color:#8b9dc3;" 
                    +"   color:#ffffff;" 
                    +"   font-weight:bold;" 
                    +"   width: 300px;" 
                    +"   height: 25px;"
                    +"   border-radius: 24px 24px 24px 24px;"
                    +"   -moz-border-radius: 24px 24px 24px 24px;"
                    +"   -webkit-border-radius: 24px 24px 24px 24px;"
                    +"}"
                   
                    +"#buttonfield {"
                    +"    line-height:25px;"
                    +"    background-color:#dfe3ee;"
                    +"    text-align:center;"
                    +"    padding:5px;"       
                    +"}"

                    +"#pausedgame {"
                    +"      background-color:#dfe3ee;"
                    +"      color:#3b5998;"
                    +"      text-align:center;"
                    +"      padding:8px;"
                    +"}"


                    +"#table {"
                    +"      color:#3b5998;"
                    +"      text-align:center;"
                    +"}"


                    +"</style>"
                    +"<script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>"
                    +"<script> $(document).ready(function() { var pageBody = $(document.body) ; pageBody.css('zoom', '150%'); pageBody.css('background-color', '#dfe3ee'); }); </script> "
                    +"</head>"


                    +"<body>"

                    +"<div id='header'>"
                    +"<h1>Cuatro en Línea</h1>"
                    +"</div>"

                    +"<div id='pausedgame'>"
                    +"<hr><h2>Juegos Pausados</h2></hr>"
                    +"<hr> </hr>"
                    +"<hr>Seleccione el juego que desea retomar:</hr>"
                    +"<hr> </hr>"
                    +"</div>"

                    +"<center><div id='buttonfield'>"
                    +"<input type='button' id='buttongraphic' value='VOLVER' onClick=\"document.location.href='/'\">"
                    +"</div></center>"
           

                    +"<div id= 'table'>"
                    +"<br><center><table><tr><td><table width=\"450\" border=1></br>"
                    +"<tr><th> Juego </th><th> Adversario </th><th> E-mail </th></tr>";


        Game g;
        User u;
        String gameId, adversary;
        Iterator it = pausedGames.iterator();
        while (it.hasNext()) {
            g = (Game) it.next();
            gameId = g.getId().toString();
            output += "<tr><td><center><a href='/loadgame/" + gameId + "'> #" + gameId + "</a></center></td>";
            if (requesterUserId.equals(g.get("player1").toString())) {
                adversary = "player2";
            } else {
                adversary = "player1";
            }
            u = User.findById(g.get(adversary));
            output += "<td><center>" + u.get("nickName") + "</center></td><td><center>" + u.get("email") + "</center></td></tr>";
        }
        
         output += "</table></center></td></tr><tr><td>"
                +"</body>"
                +"</html>";

        Base.close();
        return output;

    }
    


    public String savedGameReport(boolean savedGame) {
        
        String output;
        if (savedGame) {
            output = optionsScreen(false,"Tu adversario ha pausado la partida", true, true, true, false, false, true);//"<strong>Tu adversario ha pausado la partida.</strong><br><small>La misma puede ser retomada mas adelante.</small><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        else {
            output = optionsScreen(false,"Juego guardado exitosamente", true, true, true, false, false, true);//"<strong>Juego guardado exitosamente.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        }
        return output;
        
    }


/*
    public String waitingAdversary(int player, String user) { // Valores 1 ó 2

        String output = "<html><head><meta http-equiv='refresh' content='3' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong>Esperando por el player #" + player + " ...</strong></td></tr>"
                + "<table></html><hr><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        return output;

    }
*/
    
    
    public String waitingAdversary(int player, String user) { // Valores 1 ó 2

  /*     String output = "<html><head><meta http-equiv='refresh' content='3' ><title>4 en Linea</title></head><body>"
                + "<h1>4 en Linea</h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<tr><td><strong>Esperando por el player #" + player + " ...</strong></td></tr>"
                + "<table></html><hr><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        return output;
  */
        return optionsScreen (true, "<strong>" + user + "</strong>, espera por tu adversario...",false,true,true,false,false,true);
    }

/*    
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
    */
    
    
//        public String showGame(String user, String player1, String player2, boolean turn, int row,int col,boolean noPlays) {
        public String showGame(String user, String player1, String player2, boolean turn) {
            
          String couldSave1;
          String couldSave2;
          if ((turn)&&(user.equals(player1))) {
              couldSave1 = "";
              couldSave2 = "disabled";
          }
          else if ((turn)&&(user.equals(player2))) {
              couldSave1 = "disabled";
              couldSave2 = "";
          }
          else {
              couldSave1 = "disabled";
              couldSave2 = "disabled";
          }
          
          String color;
          if (turn) {
              color = "green";
          }
          else {
              color = "red";
          }
              

          /*
	  if (user.equals(player1)) {
	    couldSave = "";
	  }
          if (user.equals(player2)) {
	    couldSave = "";
	  }*/
      /*    String paint = new String();
          int [][] grid = b.getGrid();
        paint = "[";
        for (int f = 5; f >= 0; f--) {
            for (int c = 0 ; c < 7; c++) {
                if (!((f==0) && (c==6))) {
                    paint += ((f+1) * grid[f][c]) + "" + (c+1) * Math.abs(grid[f][c]) + ", ";    
                }
                //llamar funcion
            }
        }
        paint += grid[0][0] + "]";
      */   
        //System.out.println("*************" +row+""+col+ " "+noPlays+"***************");
      
          
          return  "<!DOCTYPE html>"
+"<html>"
+"<head>"
//+"<meta http-equiv='refresh' content='60'>"
//+"<script>playAndRedirect(i) { \"document.location.href='/play/" + i + "'\" ; } </script>"
+"<style type='text/css'>"
//+ "    .Table"
//+"    {"
//+"        display: table;"
//+"    }"
//"    .Title"+
//"    {"+
//"        display: table-caption;"+
//"        text-align: center;"+
//"        font-weight: bold;"+
//"        font-size: larger;"+
//"    }"+
+"    .Heading"
+"    {"
+"        display: table-row;"
+"        font-weight: bold;"
+"        text-align: center;"
+"    }"

+"    .Row"
+"    {"
+"        display: table-row;"
+"    }"

+"    .Cell"
+"    {"
+"        display: table-cell;"
+"        border: solid;"
+"        border-width: thin;"
+"        width:32px;"
+"        height:32px;"
+"        padding-left: 1px;"
+"        padding-right: 1px;"
+"    }"

+"    .ButtonCell"
+"    {"
+"        display: table-cell;"
+"        border: solid;"
+"        border-width: thin;"
+"        background-color: black;"
//+"        width:32px;"
//+"        height:32px;"
//+"        padding-left: 0px;"
//+"        padding-right: 0px;"
+"    }"

+"#header {"
+"    background-color:#3b5998;"
+"   color:white;"
+"  text-align:center;"
+" padding:10px;"
+"}"

+"#userlog {"
+"    background-color:#dfe3ee;"
+"   color:#3b5998;"
+"  text-align:right;"
                    //+"    height:30px;"
                  //  +"    width:100%;"
+" padding:2px;"
+"}"
//+"#gamepanel {"
//+" width: 100%;"
//                +"}"
+"#leftpanel {"
+"    line-height:20px;"
+"    background-color:#dfe3ee;"
                +"color:green;"
+"text-align:center;"
+"    height:230px;"
+"    width:25%;"//200px;"
+"    float:left;"
+"    padding:5px;"	      
+"}"
                
+"#section {"
+"    width:50%;"//400px;"
                +"    background-color:#dfe3ee;"
+"    height:230px;"
+"    float:center;"
+"    padding:1px;"	 	 
+"}"
                
+"#rightpanel {"
+"    line-height:20px;"
+"    background-color:#dfe3ee;"
                +"color:red;"
+                "text-align:center;"
+"    height:230px;"
+"    width:25%;"//200px;"
+"    float:right;"
+"    padding:5px;"	      
+"}"
                
+"#footer {"
+"    background-color:black;"
+"    color:white;"
+"    clear:both;"
+"    text-align:center;"
+"   padding:5px;"	 	 
+"}"
                
+"</style>"
                
                
                

+"<script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>"
+"<script> $(document).ready(function() { var c ; var x='green' ; var pageBody = $(document.body); "
+ "pageBody.css('zoom', '150%'); pageBody.css('background-color', '#dfe3ee');  "

/*     "setTimeout(checkTurn,1000);\n" +
     "function checkTurn () {\n" +
 //    "    alert('Call ajax');\n" +
     "    $.get( \"/ajaxturncheck\", function( data ) {if (data=='yes') {$( '#"+row+""+col+"' ).css('background-color', 'blue');     " +           
     "                 }"+
     "    else{\n"+
 //    "        alert('daleeee wachin!!!');\n"  +
 //    "        $( \"#35\" ).css('background-color', 'red');     " +  
                  
    "        setTimeout(checkTurn,3000);\n" +
     "} \n              "+   
     "    });\n" +
     "} \n              "           
                
*/                
    +"$('#fstColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/1', function(data){$('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1') } ).done(function() {  setTimeout(checkTurn,1000) ;  function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color', x) ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1');x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    +"$('#sndColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/2', function(data){$('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1') } ).done( function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1');x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }})   }  ; })}) ;"                 
    +"$('#thrColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/3', function(data){$('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1') } ).done(function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1'); x= invertColor(" + color + ");} else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    +"$('#fourColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/4', function(data) { $('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1') } ).done(function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1');x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    +"$('#fiveColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/5', function(data){$('#' + data).css('background-color','" + color + "'); $(':button').attr('disabled','-1')  }   ).done(function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1');x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    +"$('#sixColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/6', function(data){$('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1')  }   ).done(function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1');x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    +"$('#sthColBut').click(function (){jQuery.ajaxSetup({async:false}); $.get('/play/7', function(data){$('#' + data).css('background-color','" + color + "') ; $(':button').attr('disabled','-1')  }   ).done(function() { setTimeout(checkTurn,1000) ; function checkTurn () { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1'); x= invertColor(" + color + ");} else { setTimeout(checkTurn, 2000); }})   }  ; })  }) ;"
    //+"if ("+ turn +") { c = invertTurn(" + turn + ") } ; "              
    +" setTimeout(checkTurn,1000) ;  function checkTurn () { if (!" + turn + ") { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color',x) } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1'); x= invertColor(" + color + "); } else { setTimeout(checkTurn, 2000); }}) }  };"
    
    //+" setTimeout(checkTurn1,1000) ; function checkTurn1 () { if (" + turn + ") { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {$('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton2').attr('disabled','-1'); } else { setTimeout(checkTurn, 2000); }}) }  };"
    //+ "else { if ("+ turn +") { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) { $('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color + "') ; } );  ; $(':button').removeAttr('disabled'); $('#saveButton2').attr('disabled','-1'); }  else {  setTimeout(checkTurn, 2000); } } ) }  } ; "
    //+ "};"
    //+"setTimeout(checkTurn,1000) ; function checkTurn () { if (" + turn + ") { $.get( '/ajaxturncheck', function( data ) { if (data=='yes') { jQuery.ajaxSetup({async:false}); $.get('/ajaxreadchannel', function( arr ) {  $('#' + arr[arr.length - 2] + arr[arr.length - 1]).css('background-color','" + color1 + "') ; } ) ; $(':button').removeAttr('disabled'); $('#saveButton1').attr('disabled','-1'); } else { setTimeout(checkTurn, 2000); }}) }};"    
                  //if (" + color + ".equals('green')) { " + color + " = 'red';} else { " + color + " = 'green'; } ;
//if (" + color + "=="+ color + ") { alert('tdtdtd') } ;
                  
                  /*+"$.each(grid, function(i, n) {" 
+"  var color;  "
+ " if (n>0){"
+"      color='green' ;"
                 + "    $('#' + n).css('background-color', color);"
+"  }"
+ " if (n<0){"
+"      color='red' ;"
                + "    $('#' + Math.abs(n)).css('background-color', color);"
+"  }"
+" });"*/
+ "});" 
+"</script>"
 // +"<script type='text/javascript' > function invertTurn ( ) { var c= " + turn + "; alert (c) ;}  </script>"                
+"<script type='text/javascript' > function invertTurn ( t ) { var c =!t ; return c }  </script>" 
                  
+"<script> function invertColor ( p ) { var pintar = p ; if (pintar == 'green') { pintar= 'red' } else { pintar = 'green' } ; return pintar }  </script>"
//+"<script> function invertColor ( ) { if ( " + color + "== 'green') { "+ color + " = 'red' } else { "+ color + " = 'green' }  }  </script>"                    
                  
//+"<script> $(document).ready(function() { $('#fstColBut').click(function(evento) { $('#11').css('background-color', 'red'); }); </script> " //\"document.location.href='/play/1'\" ;
//$('#fstColBut').click(function(evento) { $('#11').css('background-color', 'red'); });
                +"</head>"
+"<body>"

+"<div id='header'>"
+"<h1>Cuatro en Línea</h1>"
+"</div>"

                
+"<div id='userlog'>"
+"<hr><i>Usuario:</i> <b>" + user + "</b><hr>"
+"</div>"
                
+"<div id='rightpanel'>"
+"<i>Jugador #2:</i><br>"
                +"<b>" + player2 + "</b><br><hr>"
                +"<input type='button' " + couldSave2 + " id='saveButton2' value='Guardar' onClick=\"document.location.href='/savegame'\">"
+"<hr></div>"
//+"<div id='gamepanel'>"
+"<div id='leftpanel'>"
+"<i>Jugador #1:</i><br>"
                +"<b>" + player1 + "</b><br><hr>"
                +"<input type='button' " + couldSave1 + " id='saveButton1' value='Guardar' onClick=\"document.location.href='/savegame'\">"
+"<hr></div>"

+"<center><div id='section'>" + Board.showBoard(turn) + "</div></center>"


//+"</div>"

+"<div id='footer'>"
+"Copyright ©. Mariano Ontivero - Matías Rondeau - Carolina Zabala"
+"</div>"

+"</body>"
+"</html>";


}



    public String showWinner(String user, String winner) {

        /*String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1><font color='red'>Ganador de la partida: " + ganador + " !!</font></h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;*/
        return optionsScreen(false,"Ganador de la partida: <strong>" + winner + "</strong>", true, true, true, false, false, true);
        
    }

        public String showTieMatch(String user) {
        
        /*String output = "<html><head><title>4 en Linea</title></head><body>"
                + "<h1><font color='red'>Partida empatada !!</font></h1><hr><table><tr><td>Estas logueado como: <strong>" + user + " </strong></td></tr>"
                + "<table></html>"
                + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        
        return output;*/
            return optionsScreen(false,"Partida empatada!", true, true, true, false, false, true);
            
    }



    public String busyGame() {
        
        //String output = "<strong>Partida ocupada. Intente nuevamente mas adelante.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
        //return output;
        return optionsScreen(false,"Partida ocupada, intente nuevamente mas adelante", true, true, true, false, false, true);
        
    }
    
    
    /*public String getPageStyle() {
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
    }*/

    //public static 
    public String optionsScreen (Boolean refresh, String msg, boolean opt1, boolean opt2, boolean opt3, boolean opt4, boolean opt5, boolean opt6) {

        String refpage;
        String dsb1;
        String dsb2;
        String dsb3;
        String dsb4;
        String dsb5;
        String dsb6;

        if (refresh) {
            refpage="<meta http-equiv='refresh' content='2' >";
            
        }
        else {
            refpage="";
        }
        
        if (opt1) {
            dsb1 = "'ActiveButton'";
        }
        else {
            dsb1 = "'InactiveButton' disabled";
        }

        if (opt2) {
            dsb2 = "'ActiveButton'";
        }
        else {
            dsb2 = "'InactiveButton' disabled";
        }

        if (opt3) {
            dsb3 = "'ActiveButton'";
        }
        else {
            dsb3 = "'InactiveButton' disabled";
        }

        if (opt4) {
            dsb4 = "'ActiveButton'";
        }
        else {
            dsb4 = "'InactiveButton' disabled";
        }

        if (opt5) {
            dsb5 = "'ActiveButton'";
        }
        else {
            dsb5 = "'InactiveButton' disabled";
        }
        
        if (opt6) {
            dsb6 = "'ActiveButton'";
        }
        else {
            dsb6 = "'InactiveButton' disabled";
        }
        
        return  "<!DOCTYPE html>"
                    +"<html>"
                    +"<head>"+ refpage + "<style type='text/css'>" 
+" #ActiveButton { " 
+"   background-Color:#8b9dc3;"//006666;" 
+"   color:#ffffff;" 
//+"   font-family:'Courier New\', Courier, monospace"
+"   font-weight:bold;" 
+"   width: 450px;" 
+"   height: 30px;"
+"                border-radius: 24px 24px 24px 24px;"
+"            -moz-border-radius: 24px 24px 24px 24px;"
+"            -webkit-border-radius: 24px 24px 24px 24px;"
+"}"
              
+" #InactiveButton { " 
+"   background-Color:light-gray;" 
+"   color:gray;" 
//+"   font-family:'Courier New\', Courier, monospace"
+"   font-weight:bold;" 
+"   width: 450px;" 
+"   height: 30px;" 
+"                border-radius: 24px 24px 24px 24px;"
+"            -moz-border-radius: 24px 24px 24px 24px;"
+"            -webkit-border-radius: 24px 24px 24px 24px;"
+"}"
                    +"#header {"
                    +"      background-color:#3b5998;"
                    +"      color:white;"
                    +"      text-align:center;"
                    +"      padding:10px;"
                    +"}"

                    +"#infofield {"
                    +"      background-color:#dfe3ee;"
                    +"      color:#3b5998;"
                    +"      text-align:center;"
                    //+"    height:30px;"
                    //  +"    width:100%;"
                    +"      padding:5px;"
                    +"}"

                    +"#buttonfield {"
                    +"    line-height:25px;"
                    +"    background-color:#dfe3ee;"
                    //+"color:green;"
                    +"text-align:center;"
                    //+"    height:450px;"
                    //+"    width:200px;"
                    //+"    float:left;"
                    +"    padding:5px;"       
                    +"}"
/*
+"#footer {"
+"    background-color:black;"
+"    color:white;"
+"    clear:both;"
+"    text-align:center;"
+"   padding:5px;"	 	 
+"}"
*/

                    +"</style>"
                    +"<script src='http://localhost:4567/js/jquery-1.11.3.min.js' type='text/javascript'></script>"
                    +"<script> $(document).ready(function() { var pageBody = $(document.body) ; pageBody.css('zoom', '150%'); pageBody.css('background-color', '#dfe3ee'); }); </script> "
                    +"</head>"

                    +"<body>"

                    +"<div id='header'>"
                    +"<h1>Cuatro en Línea</h1>"
                    +"</div>"
                    
                    +"<div id='infofield'>"
                    +"<hr>" + msg + "<hr>"
                    +"</div>"

                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb1+ " value='INICIAR NUEVA PARTIDA' onClick=\"document.location.href='/play/0'\" type='button' />"
                    +"</div>"

                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb2+ " value='REANUDAR PARTIDA PAUSADA' onClick=\"document.location.href='/loadgame'\" type='button' />"
                    +"</div>"

                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb3+ " value='LISTAR RANKINGS' onClick=\"document.location.href='/showrankings'\" type='button' />"
                    +"</div>"

                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb4+ " value='REGISTRARSE' onClick=\"document.location.href='/signin'\" type='button' />"
                    +"</div>"
                
                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb5+ " value='INICIAR SESION' onClick=\"document.location.href='/'\" type='button' />"
                    +"</div>"

                    +"<div id='buttonfield'>"
                    +"<input id=" +dsb6+ " value='CERRAR SESION' onClick=\"document.location.href='/logout'\" type='button' />"
                    +"</div>";

/*                    +"<div id='footer'>"
                    +"Copyright © Ver que escribir o si lo sacamos"
                    +"</div>";
*/    }

}
