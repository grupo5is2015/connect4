
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


    public static String menuweb() {

        String out = "<hr><a href='/play/0'>Juego Nuevo </a>- <a href='/loadgame'>Cargar Juego</a>";
        out += " - <a href='/logout'> Salir</a> - <a href='/login'>Iniciar sesion</a> - <a href='/signin'>Registrarse</a><hr>";
        return out;
    }

    public static void main(String[] args) {

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        Login log = new Login();
        WebManager web = new WebManager();


        get("/savegame", (req, res) -> {
            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
            } catch (Exception e) {
            }

            String output = " <strong>Juego Guardado.</strong><hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
            game.settleUser();
            game.save();
            game.saveGame(true); // guarda movimientos tablero
            Base.close();
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

            game.settleGame(player1, player2);
            boardcontrol = new BoardControl(game.table);
              // Setea los jugadores y crea un tablero nuevo

            List<Move> moves = game.getAll(Move.class);
            game.settleMovesList(moves, boardcontrol);

            Base.close();
            res.redirect("/play/0");
            return null;

        });

        
        get("/play/:columna", (req, res) -> {

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

                Integer column = new Integer(req.params(":columna"));

                // Empezar a jugar, solo para los jugadores de este juego
                if (currentUser == 1 || currentUser == -1) {

                    if (game.turnOff == currentUser && column > 0 && !boardcontrol.fullColumn(column - 1) && game.get("finished").toString().equals("false")) {
                        //  Ademas chequear que no haya ganador ni tablero lleno					      
                        game.regMove(currentUser, column-1);
                        boardcontrol.insertCoin(currentUser, column-1);

                        if (boardcontrol.isTheVictor(currentUser == 1)) {
                            game.set("finished", true);
                            game.set("draw", false);
                            game.set("user_id", req.session().attribute("userId"));
                        }

                        game.turnOff *= -1;
                        res.redirect("/play/0");
                        return null;
                    }
                    if (game.get("finished").toString().equals("false")) {
                        output += web.showGame(req.session().attribute("user"), player1.get("email").toString(), player2.get("email").toString(), game.boardToHtml(game.turnOff == currentUser));
                    } else {
                        String winner = "";
                        // comparo luego de que se cambio el turnoff
                        if (game.turnOff == -1) {
                            winner = player1.get("email").toString();
                        } else {
                            winner = player2.get("email").toString();
                        }
                        output += web.showWinner(req.session().attribute("user"), winner);
                        game = null;
                        player1 = null;
                        player2 = null;
                    }

                }
            }
            Base.close();
            return output;
            
        });

        
        get("/", (req, res) -> {

            String output;
            output = "Sesion Activa de " + req.session().attribute("user") + "<hr><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a><br><br><a href='/logout'>Salir</a>";
            if (req.session().attribute("user") == null) {
                output = "Bienvenido a Cuatro en Linea<hr> <a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
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
            String output = "Hola " + req.session().attribute("user") + "! Has ingresado correctamente. Opciones:<br><br><a href='/play/0'> Iniciar nueva partida </a><br><br><a href='/loadgame'> Cargar partida inconclusa</a>";
            User user = null;
            user = web.loginCheck(req.queryParams("email"), req.queryParams("password"), log);
            if (user == null) {
                req.session().attribute("user", null);
                output = "<strong>Datos Incorrectos!</strong><hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
            } else {
                req.session().attribute("userId", user.get("id"));
            }
            return output;

        });

    }
}
