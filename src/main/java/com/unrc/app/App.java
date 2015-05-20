
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import com.unrc.app.User;
import com.unrc.app.Login;
import org.javalite.activejdbc.Base;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 *
 * @author slowhand
 */
public class App {
    
    
    public static void main(String[] args) {
        System.out.println("Bienvenidos a 4 en linea");
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");

        //Login log = new Login();
        //User player1 = log.login
        //User player1;
        //User player2;
        
        try {
            int op = menu();
            while (op == 1 || op == 2 || op == 3 || op == 4 || op == 5) {  // mientras sea opcion 1
                switch (op) {
                    case 1: UserControl.UserRegistration();
                            BufferedReader b1 = new BufferedReader(new InputStreamReader(System.in));
                            b1.readLine();
                            break;
                    case 2: System.out.print("\nEn construccion. Presione cualquier tecla para volver al menu... ");
                            BufferedReader b2 = new BufferedReader(new InputStreamReader(System.in));
                            b2.readLine();
                            break;
                    case 3: Login log = new Login();
                            System.out.print("\nIngrese su e-mail: ");
                            BufferedReader b3 = new BufferedReader(new InputStreamReader(System.in));
                            String email = b3.readLine();
                            System.out.print("\nIngrese su contraseña: ");
                            String pass = b3.readLine();
                            User player = log.login_check(email, pass);
                            if (player == null) {
                                System.out.print("\nE-mail/Contraseña incorrectos. Presione cualquier tecla para volver al menu... ");
                                b3.readLine();
                            }
                            else {
                                System.out.print("\n¡Bienvenido!. Presione cualquier tecla para volver al menu... ");
                                b3.readLine();
                           }
                            break;
                    case 4: Game g = new Game();
                            g.PlayGame();
                            break;
                    case 5: System.out.print("\nEn construccion. Presione cualquier tecla para volver al menu... ");
                            BufferedReader b5 = new BufferedReader(new InputStreamReader(System.in));
                            b5.readLine();
                            break;
                    default: op = menu();
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
            while (! isNumeric(s)) {
                System.out.print("\tDebe ingresar un numero. Seleccione la opcion deseada: ");
                s = b.readLine();
            }
            op = new Integer(s);
        } catch (IOException e) {
            throw new IOException("Excepcion bufferedReader.readLine()");
        }
        return op;
    }
    
}