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

    public String logincheck(String email,String pass,Login log) {
		User u=null;
		String resultado="<strong>Datos Incorrectos!</strong><hr> Intenente nuevamente <a href='/login'>Aqui</a>";
	        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
 		u = log.loginCheck(email,pass);
		if (u!=null) {
			resultado=" Bienvenido: "+email;

		}

		return resultado;

    }
   
}
