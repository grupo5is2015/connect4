package com.unrc.app;

import java.util.Iterator;
import org.javalite.activejdbc.Base;
import java.util.List;
import spark.Spark;
import static spark.Spark.*;

/**
 *
 * @author Grupo #5: Ontivero - Rondeau - Zabala
 *
 */
public class App {

    public static User player1 = null;
    public static User player2 = null;
    public static Game game;
    public static BoardControl boardCtrl;


    public static void main(String[] args) {

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        Login log = new Login();
        WebManager web = new WebManager();
        Spark.staticFileLocation("/public");


        get("/", (req, res)
                
                -> web.welcomePage(req.session().attribute("user") != null, false,req.session().attribute("user"))
               //-> WebManager.optionsScreen("hola", true, true, false, true, true, false)
        );


/*
        get("/login", (req, res)
                
                -> web.showLoginForm()
                
        );
*/


        post("/logincheck", (req, res) -> {

            req.session(true);  // create and return session
            boolean succesfulLogin;
            User user = log.loginCheck(req.queryParams("email"), req.queryParams("password"));
            if (user == null) {
                req.session().attribute("user", null);
                succesfulLogin = false;
            }
            else {
                req.session().attribute("user", req.queryParams("email"));
                req.session().attribute("userId", user.get("id"));
                succesfulLogin = true;
           }
            /*
           if (succesfulLogin) {
                return web.optionsScreen("Hola <strong>" + req.queryParams("email") + "</strong>, has ingresado correctamente!", true, true, true, false, false, true);
           }
           else {
                return web.welcomePage(false, true, "");
           }*/
           //return WebManager.buttonsOptions ("Hola",false,true,true,true,true,true); 
           return web.loginReport(succesfulLogin, req.queryParams("email"));
           //return web.newLoginReport(succesfulLogin, req.queryParams("email"));

        });




        get("/signin", (req, res)

            -> web.showRegistrationForm()

        );



        post("/registration", (req, res) -> {

            boolean succesfulRegistration = UserControl.userRegistration(req.queryParams("email"), req.queryParams("password"), req.queryParams("nickname"));
            return web.registrationReport(succesfulRegistration, req.queryParams("email"));

        });
        
        
        
        get("/showrankings", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            }
            catch (Exception e) {
            }

            List<Ranking> ranksList = Ranking.findAll().orderBy("points desc");
            String output = web.showPlayersRankings(ranksList);

            Base.close();
            return output;

        });



        get("/loadgame/:game", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            }
            catch (Exception e) {
            }

            game = Game.findById(req.params(":game"));
            player1 = User.findById(game.get("player1"));
            player2 = User.findById(game.get("player2"));

            game.settleGame(player1, player2);
            boardCtrl = new BoardControl(game.table);

            List<Move> moves = game.getAll(Move.class);
            game.settleMovesList(moves, boardCtrl);

            Base.close();
            res.redirect("/play/0");
            return null;

        });

                

        get("/loadgame", (req, res) -> {

            String currentUserId = req.session().attribute("userId").toString();
            List<Game> pausedGames = Game.where("finished = ? and (player1 = ? or player2 = ?)", false, currentUserId, currentUserId);
            String output = web.showPausedGames(pausedGames, currentUserId);
            return output;

        });



        get("/logout", (req, res) -> {

            String email = req.session().attribute("user").toString();
            req.session(true);
            req.session().attribute("user", null);
            player1 = null;
            player2 = null;
            return web.goodbyeMessage(email);

        });



        get("/savegame", (req, res) -> {

            if (!game.pausedGame) {

                try {
                    Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
                }
                catch (Exception e) {
                }

                game.settleUser();
                game.saveIt();
                game.saveGame(); // guarda los movimientos del tablero
                game.pausedGame = true;
                
                Base.close();
                return web.savedGameReport(!game.pausedGame);
                
            }   // para player1
            else {
                return web.savedGameReport(game.pausedGame);
            }

        });
         
        
        get("/ajaxtest", (req, res) -> {
         String output="";
         output="<!DOCTYPE html>\n" +
            "<html>" +
            "<head>" +
            "" +
            "<script src=\'https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\'>\n" +
            "</script>\n" +
            "<script>\n" +
            "setTimeout(checkTurn,1000);\n" +
            "function checkTurn () {\n" +
            "    alert('Call ajax');\n" +
            "    $.get( \"/ajaxturncheck\", function( data ) {\n" +
            "         $( \"#a\" ).html( data );\n" +
            "          if (data=='yes'){\n"
                 + "                 }"
                 + "    else{\n"
                 + "        alert('espera wachin!!!');\n"
                      
                 +"         //setTimeout(checkTurn,5000);\n" 
                 + "} \n              "+   
            "    });\n" +
                 " $.get( \"/play/2\", function( data) {"+
               " $( \"#b\" ).html( data ) });\n" +
            " }\n" +
               
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id=\"a\">\n" +
            "<p>Is my turn?</p>\n" +
            "</div>\n" +
            "<div id=\"b\">\n" +
            "</div>\n" +
            "<div id=\"c\">\n" +
            "</div>\n" +
            "</body>\n" +
            "\n" +
            "</html>\n ";
         
         return output;
         
         });
        
        get("/ajaxreadchannel", (req, res) -> {
            // String output="";
            req.session(true);
            int currentUser = 0;
            if (player1 != null && player2 != null) {
                if (player1.get("email").toString().equals(req.session().attribute("user"))) {
                    currentUser = 1;
                }
            
                if (player2.get("email").toString().equals(req.session().attribute("user"))) {
                     currentUser = -1;
                }                    
            }
             Iterator it = game.movesList.iterator();
             String mv="";
             while (it.hasNext() ){
                 Pair p = (Pair) it.next();
                 mv+= p.getNumPlayer().toString() +","+ p.getColumnSelected()+";"; 
             }
             return mv;
        });
        
        get("/ajaxturncheck", (req, res) -> {
            String output = "denegado";
            req.session(true);
            int currentUser = 0;
            if (player1 != null && player2 != null) {
                if (player1.get("email").toString().equals(req.session().attribute("user"))) {
                    currentUser = 1;
                }
            
                if (player2.get("email").toString().equals(req.session().attribute("user"))) {
                     currentUser = -1;
                }
            
                if (game.turnOff == currentUser)
                     output = "yes";
                }
            
            return output;
        });
        


        get("/play/:column", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            }
            catch (Exception e) {
            }

            String output = "";
            req.session(true);

            if (player1 == null) {
                player1 = User.findFirst("id = ?", new Integer(req.session().attribute("userId").toString()));
                req.session().attribute("player", 1);
                output = web.waitingAdversary(2, req.session().attribute("user").toString());

            }
            else if (player2 == null) {   // player1 != null & player2 == null
                    if (!player1.get("id").toString().equals(req.session().attribute("userId").toString())) {
                        player2 = User.findFirst("id = ?", new Integer(req.session().attribute("userId").toString()));
                        req.session().attribute("player", 2);
                        output = web.waitingAdversary(1, req.session().attribute("user").toString());
                    }
                    else {  // 
                        output = web.waitingAdversary(2, req.session().attribute("user").toString());
                    }
                }
            else {    // player1 !=null & player2 != null
                    if (game == null) {
                        game = new Game(player1, player2);
                        boardCtrl = new BoardControl(game.table);
                    }
                
                    // column==0 --> mostrar tablero
                    int currentUser = 0;
                    if (player1.get("email").toString().equals(req.session().attribute("user"))) {
                        currentUser = 1;
                    }
                    if (player2.get("email").toString().equals(req.session().attribute("user"))) {
                        currentUser = -1;
                    }

                    Integer column = new Integer(req.params(":column"));
                    if (currentUser * currentUser != 1) {
                        output = web.busyGame();
                    }
                
                    // SOLO SE PERMITE JUGAR A LOS JUGADORES PERTENECIENTES A ESTE JUEGO
                    if (currentUser == 1 || currentUser == -1) {

                        if (game.turnOff == currentUser  &&  column > 0  &&  !boardCtrl.fullColumn(column-1)  &&  game.get("finished").toString().equals("false")) {
                            // PUEDE JUGAR SI ES SU TURNO, LA COLUMNA ES VALIDA, LA COLUMNA NO ESTA LLENA Y EL JUEGO NO ESTA FINALIZADO
                            game.registerMove(currentUser, column-1);
                            boardCtrl.insertCoin(currentUser, column-1);

                        if (game.movesList.size() == game.numCol*game.numRow) { // ULTIMA JUGADA: EMPATE O TRIUMFO DE PLAYER #2
                            if (!game.player1Aware) { // NINGUN JUGADOR FUE NOTIFICADO
                                game.set("finished", true);
                                if (boardCtrl.isTheVictor(false)) { // GANO PLAYER #2
                                    game.set("draw", false);
                                    game.set("user_id", req.session().attribute("userId"));
                                }
                                else { // EMPATE
                                    game.set("draw", true);
                                    // ACTUALIZACION DEL RANKING DE PLAYER #1
                                    Ranking updRnkP1 = Ranking.findFirst("user_id = ?", player1.getId());
                                    int newPoint1 = ((Integer) updRnkP1.get("points")).intValue() + 1;
                                    updRnkP1.set("points", newPoint1);
                                    updRnkP1.saveIt();
                                    // ACTUALIZACION DEL RANKING DE PLAYER #2
                                    Ranking updRnkP2 = Ranking.findFirst("user_id = ?", player2.getId());
                                    int newPoint2 = ((Integer) updRnkP2.get("points")).intValue() + 1;
                                    updRnkP2.set("points", newPoint2);
                                    updRnkP2.saveIt();
                                }
                                game.player1Aware = true;   // PLAYER #1 VA A SER NOTIFICADO, FALTA NOTIFICAR A PLAYER #2
                            }
                            else {  // PLAYER #1 YA NOTIFICADO
                                player1 = null;
                                player2 = null;
                            }
                            if (game.get("draw").toString().equals("true")) {   // REDIRECCION A INFORME DE EMPATE
                                res.redirect("/gameover/"+req.session().attribute("user")+"/withoutwinner/draw");
                                return null;
                            }
                            else {  // REDIRECCION A INFORME DE PLAYER #2 GANADADOR
                                res.redirect("/gameover/"+req.session().attribute("user")+"/"+game.winnerName+"/thereiswinner");
                                return null;
                            }
                        }
                        else { // NO ES LA ULTIMA JUGADA (< #42)
                            if (boardCtrl.isTheVictor(currentUser == 1)) {  // PLAYER #1: TRUE / PLAYER #2: FALSE
                                game.set("finished", true);
                                game.set("draw", false);
                                game.set("user_id", req.session().attribute("userId"));
                            }
                        }

                        game.turnOff *= -1;
                        res.redirect("/play/0");
                        return null;
                    }

                    if (game.get("finished").toString().equals("false")) {  // EL JUEGO NO FINALIZO
			if (!game.pausedGame) {
                            if (game.movesList.size() < 2 ){ //Primera vez muestra matriz
				
                                //output = web.showGame(req.session().attribute("user"), player1.get("email").toString(), player2.get("email").toString(), game.boardToHtml(game.turnOff == currentUser));
                                output = web.showGame(req.session().attribute("user"), player1.get("email").toString(), player2.get("email").toString(), game.turnOff == currentUser, boardCtrl.getBoard(),column+1);//, boardCtrl.rowToInsert[column]);
                                //output= boardCtrl.getBoard().showGame(req.session().attribute("user"), player1.get("email").toString(), player2.get("email").toString(), game.turnOff == currentUser, game.turnOff);
                                }
                            else{
                                output= "Solo se muestra la primera vez"+game.movesList.size() ;
                               }
                            }
                        if (game.pausedGame) {
                            res.redirect("/savegame");
                            return null;
                        }				
                    }
                    else {  // JUEGO FINALIZADO
                        // se compara luego de que se cambio turnOff
                        if (!game.player1Aware) {   // NINGUN JUGADOR FUE NOTIFICADO
                            if (game.turnOff == -1) {
                                game.winnerName = player1.get("email").toString();
                                Ranking updrnk1 = Ranking.findFirst("user_id = ?", player1.getId());
                                int newScore = Integer.valueOf(updrnk1.get("points").toString()) + 3;
                                updrnk1.set("points", newScore);
                                updrnk1.saveIt();
                            }
                            else {
                                game.winnerName = player2.get("email").toString();
                                Ranking updrnk2 = Ranking.findFirst("user_id = ?", player2.getId());
                                int newScore = Integer.valueOf(updrnk2.get("points").toString()) + 3;				 
                                updrnk2.set("points", newScore);
                                updrnk2.saveIt();
                            }
                            game.player1Aware = true;   // PLAYER #1 VA A SER NOTIFICADO, FALTA NOTIFICAR A PLAYER #2
                        }
                        else {  // PLAYER #1 YA NOTIFICADO
                            player1 = null;
                            player2 = null;                      
                        }

                        if (game.get("draw").toString().equals("true")) {
                            System.out.println(game.get("draw").toString());
                            res.redirect("/gameover/"+req.session().attribute("user")+"/withoutwinner/draw");
                            return null;
                        }
                        else {
                            res.redirect("/gameover/"+req.session().attribute("user")+"/"+game.winnerName+"/thereiswinner");
                            return null;
                        }
                    }

                }
            }
            Base.close();
            return output;

        });



        get("/gameover/:user/:winner/:draw", (req, res) -> {
            
            String output;
            String user = req.params(":user");
            String winner = req.params(":winner");
            String draw = req.params(":draw");
            
            if (draw.equals("draw")) {
                output = web.showTieMatch(user);
            }
            else {
                output = web.showWinner(user, winner);
            }

            if (player1 == null) {

		try {
		  Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
		}
                catch (Exception e) {
                }

		game.saveIt();
		Base.exec("delete from moves where game_id=?", game.get("id").toString());
        	game.delete();
		game = null;

		Base.close();
            
            }

            return output;
            
        });
        
        
        get("/style.css", (req, res) -> { 
                return web.getPageStyle(); 
        });

    }
    
}
