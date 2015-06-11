/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.javalite.activejdbc.Base;

/**
 *
 * @author slowhand
 */
public class UserControl {
    
        public static String UserRegistration(String email, String pass,String nickName) throws IOException {
            try {Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");}
		  catch(Exception e) {}
        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        //System.out.print("\nIngrese su e-mail: ");
        //String inputReaded = br.readLine();
            String output="";
            User u = User.findFirst("email = ?", email);
        if (u != null) {
           output="\nEl e-mail " + email + " ya se encuentra registrado. Presione cualquier tecla para volver al menu... ";
            
        }
        else {
            User nu = new User();
            nu.set("email", email);
            //System.out.print("\nIngrese su nombre: ");
            //inputReaded = br.readLine();
            //nu.set("first_name", "f");
            //System.out.print("\nIngrese su apellido: ");
            //inputReaded = br.readLine();
            //nu.set("last_name", inputReaded);
            //System.out.print("\nIngrese el apodo que desea usar: ");
            //inputReaded = br.readLine();
            nu.set("nickname", nickName);
            //System.out.print("\nIngrese una contraseña: ");
            //inputReaded = br.readLine();
            nu.set("password", pass);
            nu.saveIt();
            Ranking rnu = new Ranking();
            nu.add(rnu);
            rnu.set("rank", Base.count("users").intValue());
            rnu.set("points", 0);
            rnu.set("won", 0);
            rnu.set("tie", 0);
            rnu.set("lost", 0);
            rnu.saveIt();
           output="\nRegistracion exitosa. Presione cualquier tecla para volver al menu... ";
           
        }
        Base.close();
        return output;
    }
    
}
