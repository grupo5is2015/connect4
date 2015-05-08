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

public class Login {


	User usuario;

	public void Login() {
		

	}


	public User login_check(String login_email, String login_pass) {
	  	Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
		usuario = User.findFirst("email = ? and password= ?",login_email,login_pass );
		Base.close();
		return usuario;

	}



	public void show_login() {
  	

	JFrame v = new JFrame ("Iniciar Sesion");
	v.getContentPane().setLayout(new FlowLayout()); //agrega un container layout

	JButton b = new JButton("Entrar");
	JTextField loguser= new JTextField(20);
	JTextField logpass= new JTextField(20);

	v.getContentPane().add(loguser);
	v.getContentPane().add(logpass);
	v.getContentPane().add(b);

	v.pack(); //optimiza al tamaño minimo requerido
	//v.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE); //permite cerrar el programa

     	v.setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
	v.setVisible(true); //visualiza la ventana



	b.addActionListener (new ActionListener ()
	{
 	  public void actionPerformed (ActionEvent e)
	   {
    
	      String user_email = loguser.getText();
	      String user_pass = logpass.getText();
	      User salida = login_check(user_email,user_pass);
	      if (salida!=null) {
			System.out.println(salida.get("first_name"));
	      		v.dispose();
	      }

	
	   } 
	});



	}

 }
