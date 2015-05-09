package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main( String[] args )
    {
        System.out.println( "Bienvenidos a 4 en linea" );
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
	
	//Login log = new Login();
	//log.show_login();

 
	Base.close();
	System.out.println("Fin de 4 en linea");

    }
}
