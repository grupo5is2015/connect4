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
    
        public static String UserRegistration(String email, String pass, String nickName) throws IOException {
            try {Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");}
            catch(Exception e) {}
            String output = null;
            User u = User.findFirst("email = ?", email);
            if (u != null) {
               output = "El e-mail " + email + " ya se encuentra registrado.<hr><a href='/login'> Iniciar sesion</a><br><br><a href='/signin'> Registrarse</a>";
            }
            else {
                User nu = new User();
                nu.set("email", email);
                //nu.set("first_name", " ");
                //nu.set("last_name", null);
                nu.set("nickname", nickName);
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
                output = "Felicitaciones! Registracion exitosa.<hr><a href='/login'> Iniciar sesion</a>";
            }
            Base.close();
            return output;
        }
    
}
