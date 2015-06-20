
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import com.unrc.app.*;
import org.javalite.activejdbc.Base;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.isNumeric;
import static spark.Spark.*;

/**
 *
 * @author Grupo #5
 */
public class App {

    public static User player1 = null;
    public static User player2 = null;
    public static Game game;
    public static BoardControl boardcontrol;
    //public static int moveNumber = 1;

    public static void main(String[] args) {

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        Login log = new Login();
        WebManager web = new WebManager();

        get("/savegame", (req, res) -> {
	    String output="";
	    
	    
	    if (!game.gamePaused) {
	    
	      try {
		  Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
	      } catch (Exception e) {
	      }

	      output = " <strong>Juego Guardado.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
	      game.settleUser();
	      game.save();
	      game.saveGame(true, game.moveNumber); // guarda movimientos tablero
	      Base.close();
              game.gamePaused=true;
           } //end for player1
           else {
            output = " <strong>Juego Guardado.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>";
           
           }
            
            
          
            
            return output;

        });

        get("/loadgame/:game", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            } catch (Exception e) {
            }

            game = Game.findById(req.params(":game"));
            player1 = User.findById(game.get("player1"));
            player2 = User.findById(game.get("player2"));

            int moveNumber = Move.count("game_id = ?", game.get("id")).intValue();
System.out.println("************* Guardo--> " + moveNumber);
            game.settleGame(player1, player2, moveNumber);
           System.out.println("************* Recupero--> " + game.moveNumber);
            boardcontrol = new BoardControl(game.table);
            // Setea los jugadores y crea un tablero nuevo

            List<Move> moves = game.getAll(Move.class);
            game.settleMovesList(moves, boardcontrol);

            Base.close();
            res.redirect("/play/0");
            return null;

        });

        get("/play/:column", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            } catch (Exception e) {
            }

            String output = "";
            req.session(true);

            if (player1 == null) {
                player1 = User.findFirst("id = ?", new Integer(req.session().attribute("userId").toString()));
                req.session().attribute("player", 1);
                output = web.waitForPlayer(2, req.session().attribute("user").toString());

            } else if (player2 == null) {
                if (!player1.get("id").toString().equals(req.session().attribute("userId").toString())) {
                    player2 = User.findFirst("id = ?", new Integer(req.session().attribute("userId").toString()));
                    req.session().attribute("player", 2);
                    output += web.waitForPlayer(1, req.session().attribute("user").toString());
                } else {
                    output += web.waitForPlayer(2, req.session().attribute("user").toString());
                }

            } else {    // player1 !=null & player2 != null
                if (game == null) {
                    game = new Game(player1, player2);
                    boardcontrol = new BoardControl(game.table);
                }

                //column==0 Solo mostrar tablero
                int currentUser = 0;

                if (player1.get("email").toString().equals(req.session().attribute("user"))) {
                    currentUser = 1;
                }
                if (player2.get("email").toString().equals(req.session().attribute("user"))) {
                    currentUser = -1;
                }

                Integer column = new Integer(req.params(":column"));
                if (currentUser * currentUser != 1) {
                    output = "Partida ocupada. Intente nuevamente mas adelante.<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a><br><br><a href='/logout'>Salir</a>\"";
                }
                // Empezar a jugar, solo para los jugadores de este juego
                if (currentUser == 1 || currentUser == -1) {

                    if (game.turnOff == currentUser && column > 0 && !boardcontrol.fullColumn(column - 1) && game.get("finished").toString().equals("false")) {
                        //  Ademas chequear que no haya ganador ni tablero lleno					      
                        game.regMove(currentUser, column - 1);
                        System.out.println("*************--> move: " + game.moveNumber);
                        boardcontrol.insertCoin(currentUser, column - 1);
                       
                        if (game.moveNumber == game.numCol*game.numRow) { // empate o gano player2
                            
                            System.out.println("*************--> " + game.get("draw").toString());
                            if (!game.bothNotified) { // ningun jugador notificado
                                                       System.out.println("*************--> jugador 1");
                                if (boardcontrol.isTheVictor(false)) { // gano player2
                                    game.set("finished", true);
                                    game.set("draw", false);
                                    game.set("user_id", req.session().attribute("userId"));
                                }
                                else { // empate
                                    game.set("finished", true);
                                    game.set("draw", true);
                                    Ranking updRnkP1 = Ranking.findFirst("user_id = ?", player1.getId());
                                    int newPoint1 = ((Integer) updRnkP1.get("points")).intValue();
                                    updRnkP1.set("points", newPoint1 + 1);
                                    updRnkP1.saveIt();
                                    Ranking updRnkP2 = Ranking.findFirst("user_id = ?", player2.getId());
                                    int newPoint2 = ((Integer) updRnkP2.get("points")).intValue();
                                    updRnkP2.set("points", newPoint2 + 1);
                                    updRnkP2.saveIt();
                                }
                                game.bothNotified = true;
                            }
                            else {  // jugador1 ya notificado
                                System.out.println("*************--> jugador2 ");
                                player1 = null;
                                player2 = null;                      
                                game.moveNumber=1;
                            }
                            if (game.get("draw").toString().equals("true")) {
                            System.out.println("222222222222222222222*************--> " + game.get("draw").toString());
                            //output += web.showTieMatch(req.session().attribute("user"));
                                res.redirect("/gamefinished/"+req.session().attribute("user")+"/garbage/draw");
                                return null;
                            }
                            else {
                            //output += web.showWinner(req.session().attribute("user"), winner);
                                res.redirect("/gamefinished/"+req.session().attribute("user")+"/"+game.namewinner+"/thereiswinner");
                                return null;
                            }
                        }
                        else { //no era la ultima jugada
                            if (boardcontrol.isTheVictor(currentUser == 1)) {
                                game.set("finished", true);
                                game.set("draw", false);
                                game.set("user_id", req.session().attribute("userId"));
                            } else {
                                game.moveNumber = game.moveNumber + 1;
                            }
                        }

                        game.turnOff *= -1;
                        res.redirect("/play/0");
                        return null;
                    }
                    
                    
                    if (game.get("finished").toString().equals("false")) {
			if (!game.gamePaused) 
				output += web.showGame(req.session().attribute("user"), player1.get("email").toString(), player2.get("email").toString(), game.boardToHtml(game.turnOff == currentUser));
			if (game.gamePaused) {
				      res.redirect("/savegame");
				      return null;
                        }
				
                  } else {
                        // comparo luego de que se cambio el turnoff
                        if (!game.bothNotified) {
                            if (game.turnOff == -1) {
                                game.namewinner = player1.get("email").toString();
                                Ranking updrnk1 = Ranking.findFirst("user_id = ?", player1.getId());
                                int newPoint = Integer.valueOf(updrnk1.get("points").toString());
                                updrnk1.set("points", newPoint + 3);
                                updrnk1.saveIt();
                            } else {
                                game.namewinner = player2.get("email").toString();
                                Ranking updrnk2 = Ranking.findFirst("user_id = ?", player2.getId());
                                int newPoint = Integer.valueOf(updrnk2.get("points").toString());				 
                                updrnk2.set("points", newPoint + 3);
                                updrnk2.saveIt();
                            }
                            game.bothNotified=true;
                        } else {
                            //game = null;
                            player1 = null;
                            player2 = null;
                            game.moveNumber = 1;
                        
                        
                        }
                        
                        

                        if (game.get("draw").toString().equals("true")) {
                            System.out.println(game.get("draw").toString());
                            //output += web.showTieMatch(req.session().attribute("user"));
                                res.redirect("/gamefinished/"+req.session().attribute("user")+"/garbage/draw");
                                return null;
                        } else {
                            //output += web.showWinner(req.session().attribute("user"), winner);
                            res.redirect("/gamefinished/"+req.session().attribute("user")+"/"+game.namewinner+"/thereiswinner");
                            return null;
                        }

                        
                    }

                }
            }
            Base.close();
            return output;

        });

        get("/gamefinished/:user/:winner/:draw", (req, res) -> {
            String output = "";
            String user = req.params(":user");
            String winner = req.params(":winner");
            String draw = req.params(":draw");
            if (draw.equals("draw")) {
                output += web.showTieMatch(user);
            } else {
                output += web.showWinner(user, winner);
            }
            
            if (player1==null) { 
		try {
		  Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
		} catch (Exception e) {}

		game.save();
		
		Base.exec("delete from moves where game_id=?",game.get("id").toString());
		
		game.delete();
		game=null;
		
	
		Base.close();
            
            }

            return output;
        });

        get("/", (req, res) -> {

            String output;
            output = "Sesion Activa de " + req.session().attribute("user") + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
            if (req.session().attribute("user") == null) {
                output = "<h1>Bienvenido a Cuatro en Linea</h1><hr> <a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
            }
            return output;

        });

        get("/signin", (req, res) -> {

            return web.showRegistrationForm();

        });

        post("/loginreceiver", (req, res) -> {

            return UserControl.UserRegistration(req.queryParams("email"), req.queryParams("password"), req.queryParams("nickname"));

        });

        get("/logout", (req, res) -> {

            String output = "Sesion finalizada.<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
            req.session(true);
            req.session().attribute("user", null);
            player1 = null;
            player2 = null;
            return output;

        });

        get("/login", (req, res)
                -> web.showLoginForm()
        );

        get("/loadgame", (req, res) -> {

            String currentUserId = req.session().attribute("userId").toString();
            List<Game> pausedGames = Game.where("finished = ? and (player1 = ? or player2 = ?)", false, currentUserId, currentUserId);
            String output = web.showPausedGames(pausedGames, currentUserId);
            return output;

        });

        post("/logincheck", (req, res) -> {

            req.session(true);                           // create and return session
            req.session().attribute("user", req.queryParams("email"));
            String output = "Hola " + req.session().attribute("user") + ", has ingresado correctamente! Opciones:<br><br><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/showrankings'> Listar Rankings</a>";
            User user = null;
            user = web.loginCheck(req.queryParams("email"), req.queryParams("password"), log);
            if (user == null) {
                req.session().attribute("user", null);
                output = "<strong>Datos Incorrectos!</strong><hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a><br><br><a href='/showrankings'> Listar Rankings</a>";
            } else {
                req.session().attribute("userId", user.get("id"));
            }
            return output;

        });

        get("/showrankings", (req, res) -> {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            } catch (Exception e) {
            }

            List<Ranking> ranksList = Ranking.findAll().orderBy("points desc");
            String output = web.showPlayersRankings(ranksList);

            Base.close();
            return output;

        });

    }
}
