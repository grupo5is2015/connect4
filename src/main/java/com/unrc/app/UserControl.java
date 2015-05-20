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
    
        public static void UserRegistration() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("\nIngrese su e-mail: ");
        String inputReaded = br.readLine();
        User u = User.findFirst("email = ?", inputReaded);
        if (u != null) {
            System.out.print("\nEl e-mail " + inputReaded + " ya se encuentra registrado. Presione cualquier tecla para volver al menu... ");
        }
        else {
            User nu = new User();
            nu.set("email", inputReaded);
            System.out.print("\nIngrese su nombre: ");
            inputReaded = br.readLine();
            nu.set("first_name", inputReaded);
            System.out.print("\nIngrese su apellido: ");
            inputReaded = br.readLine();
            nu.set("last_name", inputReaded);
            System.out.print("\nIngrese el apodo que desea usar: ");
            inputReaded = br.readLine();
            nu.set("nickname", inputReaded);
            System.out.print("\nIngrese una contraseña: ");
            inputReaded = br.readLine();
            nu.set("password", inputReaded);
            nu.saveIt();
            Ranking rnu = new Ranking();
            nu.add(rnu);
            rnu.set("rank", Base.count("users").intValue());
            rnu.set("won", 0);
            rnu.set("tie", 0);
            rnu.set("lost", 0);
            rnu.saveIt();
            System.out.print("\nRegistracion exitosa. Presione cualquier tecla para volver al menu... ");
        }
    }
    
}
