
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
  import org.apache.commons.lang.StringUtils;
  import static org.apache.commons.lang.StringUtils.isNumeric;
  import static spark.Spark.*;


  /**
  *
  * @author slowhand
  */
  public class App {
	  public static User player1 = null;
	  public static User player2 = null;
	  public static Game game;
	  public static BoardControl boardcontrol;
	  


  public static synchronized User getPlayer1(){
	  return player1;
  }


      public static void main(String[] args) {
	  //System.out.println("Bienvenidos a 4 en linea");
	  Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
	  Login log = new Login();


	  WebManager web= new WebManager();

	  
	  get("/savegame", (req, res) -> { 
	    try {Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");}
	    catch(Exception e) {}
		
	      String output=" Juego Guardado.";
	      game.settleUser(); 
	      game.save();
	      game.saveGame(true); // guarda movimientos tablero
	      Base.close();
	      return output;
	  
	  });
	  
	  
	  get("/play/:columna", (req, res) -> { 
	  
		  try {Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");}
		  catch(Exception e) {}
		  
		  String output="";
		  req.session(true);
		  
		  if (player1==null) {
			  player1=User.findFirst("id = ?",new Integer(req.session().attribute("userId").toString()));
			  req.session().attribute("player",1);
			  output = web.waitForPlayer(2,req.session().attribute("user").toString());

		  } else if (player2==null) {
			  if (! player1.get("id").toString().equals(req.session().attribute("userId").toString())) {
				player2=User.findFirst("id = ?",new Integer(req.session().attribute("userId").toString()));
				req.session().attribute("player",2);
				output =  web.waitForPlayer(1,req.session().attribute("user").toString());
			  } else
			  {  output =  web.waitForPlayer(2,req.session().attribute("user").toString());}
			  
		  }else {
			if (game==null) {
			    game= new Game(player1,player2);
			    boardcontrol = new BoardControl(game.table);
			    
			    }
			    
		    
			//column==0 Solo mostrar tablero
			
			int currentUser=0;
			
			if (player1.get("email").toString().equals(req.session().attribute("user")))
			      currentUser=1;
			if (player2.get("email").toString().equals(req.session().attribute("user")))
			      currentUser=-1;
			      
			Integer column = new Integer(req.params(":columna"));
		    
			
			// Empezar a jugar, solo para los jugadores de este juego
			if (currentUser==1 || currentUser==-1 ){
		  
			  if (game.turnOff == currentUser && column>0 && !boardcontrol.fullColumn(column-1) && game.get("finished").toString()=="false"){ 
									//  Ademas chequear que no haya ganador ni tablero lleno					      
				game.regMove(currentUser, column-1);					
				boardcontrol.insertCoin(currentUser,column-1);
				
				if (boardcontrol.isTheVictor(currentUser==1)) {
				      game.set("finished", true);  
				      game.set("draw", false);
				      game.set("user_id",req.session().attribute("userId"));
				      
				      
				}
				
				game.turnOff*= -1;
				res.redirect("/play/0");
				return null;
			  }
			  if (game.get("finished").toString()=="false") {
			    output=web.showGame(req.session().attribute("user"),player1.get("email").toString(),player2.get("email").toString(),game.boardToHtml(game.turnOff == currentUser));
			  } else {
			      String winner="";
			      // comparo luego de que se cambio el turnoff
			    if (game.turnOff == -1) { winner = player1.get("email").toString();}
			    else {winner= player2.get("email").toString();}
			    output = web.showWin(req.session().attribute("user"),winner);
			    game=null;
			    player1=null;
			    player2=null;
			  }
			  
			  
			}
		  }
		  Base.close();
		  
		  return output;
	  });

	  //port(1080);
	  //for default 4567
	  get("/", (req, res) -> { 
		  String output;
		  output="Session Iniciada por "+req.session().attribute("user")+" - <a href='/logout'>Salir</a>";
		  if (req.session().attribute("user")==null)
			  output="Welcome to Four in a Line<hr> <a href='/login'> Ingresar</a>";

		  
		  return output;
		  

	  });


	  get("/logout", (req, res) -> { 
		  String output="Session finalizada";
		  req.session(true);
		  req.session().attribute("user",null);
		  player1=null;
		  player2=null;

		  return output;
		  
	  });

	  get("/login", (req, res) -> 
		  web.ShowLogin()
	  );
	  
	  post("/logincheck", (req, res) ->{
		  req.session(true);                           // create and return session
		  req.session().attribute("user", req.queryParams("email"));

		  String output="Hola! "+req.session().attribute("user")+" - <a href='/play/0'> Jugar </a>";
		  User user =null;
		  user = web.logincheck(req.queryParams("email"),req.queryParams("password"),log);
		  if (user==null) {
		    req.session().attribute("user", null);
		    output="<strong>Datos Incorrectos!</strong><hr> Intente nuevamente <a href='/login'>Aqui</a>";
		  } else {
			  
		  req.session().attribute("userId",user.get("id"));
		  }


		  

		  return output;
		  
	  });


	  }

	  /*

	  //Login log = new Login();
	  //User player1 = log.login
	
	  try {
	      int op = menu();
	      while (op == 1 || op == 2 || op == 3 || op == 4 || op == 5) {  // mientras sea opcion 1
		  switch (op) {
		      case 1:
			  UserControl.UserRegistration();
			  BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
			  b1.readLine();
			  break;
		      case 2:
			  System.out.print("\nEn construccion. Presione cualquier tecla para volver al menu... ");
			  BufferedReader b2 = new BufferedReader(new InputStreamReader(System.in));
			  b2.readLine();
			  break;
		      case 3:
			  Login log = new Login();
			  player1 = log.logMeIn();
			  while (player1 == null) {
			      System.out.println("\nE-mail/Contraseña incorrectos. Intente nuevamente...");
			      player1 = log.logMeIn();
			  }
			  System.out.println("\n¡Bienvenido " + player1.get("nickname") + "!");
			  player2 = log.logMeIn();
			  while (player2 == null) {
			      System.out.println("\nE-mail/Contraseña incorrectos.  Intente nuevamente...");
			      player2 = log.logMeIn();
			  }
			  //System.out.println(player1.getId().toString() + player2.getId().toString());
			  //System.out.println(player1.getId() == player2.getId());
			  while (player1.getId().equals(player2.getId())) {
			      System.out.println("\nEl usuario " + player1.get("nickname") + "ya se encuentra logueado.");
			      player2 = log.logMeIn();
			  }
			  System.out.print("\n¡Bienvenido " + player2.get("nickname") + "! Presione cualquier tecla para volver al menu... ");
			  BufferedReader b3 = new BufferedReader(new InputStreamReader(System.in));
			  b3.readLine();
			  break;
		      case 4:
			  if (player1 == null || player2 == null) {
			      System.out.print("\nDebe haber dos usuarios logueados para poder jugar. Presione cualquier tecla para volver al menu... ");
			      BufferedReader b4 = new BufferedReader(new InputStreamReader(System.in));
			      b4.readLine();
			  } else {
			      Game g = new Game(player1, player2);
			      g.PlayGame();

			  }

			  break;
		      case 5:
			  System.out.print("\nEn construccion. Presione cualquier tecla para volver al menu... ");
			  BufferedReader b5 = new BufferedReader(new InputStreamReader(System.in));
			  b5.readLine();
			  break;
		      default:
			  op = menu();
		  }
		  op = menu(); 	//  vuelve a solicitar la opcion
	      }
	  } catch (java.io.IOException ioex) {
	      System.out.println("Error I/O");
	  }
	  Base.close();
	  System.out.println("Fin de 4 en linea");
      }

      /**
      * Muestra por pantalla el menu y lee por teclado la opcion ingresada por el
      * usuario.
      *
      * @throws IOException cuando el metodo readLine() falla.
      * @pre. true.
      * @post. Muestra por pantalla el menu y lee por teclado la opcion ingresada
      * por el usuario.
      */
      private static int menu() throws IOException {
	  int op = 0;
	  BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
	  System.out.println("\n \t -------------------------------");
	  System.out.println("\t|***** Four In A Line Game *****|");
	  System.out.println("\t --------------------------------");
	  System.out.println("\t| Opciones:                     |");
	  System.out.println("\t| - 1: Registrarse              |");
	  System.out.println("\t| - 2: Darse de Baja            |");
	  System.out.println("\t| - 3: Ingresar                 |");
	  System.out.println("\t| - 4: Jugar                    |");
	  System.out.println("\t| - 5: Ver Rankings             |");
	  System.out.println("\t| - Otro para Salir             |");
	  System.out.println("\t --------------------------------");
	  System.out.print("\tSeleccione la opcion deseada: ");
	  try {
	      String s = b.readLine();
	      while (!isNumeric(s)) {
		  System.out.print("\tDebe ingresar un numero. Seleccione la opcion deseada: ");
		  s = b.readLine();
	      }
	      op = new Integer(s);
	  } catch (IOException e) {
	      throw new IOException("Excepcion bufferedReader.readLine()");
	  }
	  return op;
      } // end function menu()

  }
