/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;
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

		return "Ingrese sus datos<br>"
		+"<form action='/logincheck' method='post'>"
		+"email: <input type='text' name='email' value='' /><br>"
		+"Password: <input type='password' name='password'/><br>"		
		+"<input type='submit' value='Enviar' >"
		+"</form>"; 

    }

    public String showGame(String user,String player1, String player2,String board) {
    
      String salida="";
      
      salida="<html><head><meta http-equiv='refresh' content='8' ><title>4 en Linea</title></head><body>"
      + "<h1>4 en linea</h1> <table><tr><td>"+user+" Logueado.</td></tr>"
      + "<tr><td bgcolor='yellow'>"+player1+" vs " + player2+ "</td></tr>"
      + "<tr><td>"+board+"</td></tr>"
      + "</table></body></html>";
      
      
      
      
      
      return salida;
    
    }

    public User logincheck(String email,String pass,Login log) {
		User u=null;
	        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");

 		u = log.loginCheck(email,pass);
		Base.close();
		return u;

    }
   
}
