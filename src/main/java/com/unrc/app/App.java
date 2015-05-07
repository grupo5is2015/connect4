package com.unrc.app;

import com.unrc.app.User;
import org.javalite.activejdbc.Base;
import java.util.List;
import java.util.Scanner;

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


    public static void add_new_user() 
	{
		
	User u = new User();
	//u.set("first_name","pepito");
	u.set("last_name","cibrian");
	u.save();

	JFrame v = new JFrame ("Bienvenidos a 4 en linea!!!");
	v.getContentPane().setLayout(new FlowLayout()); //agrega un container layout
	JButton b = new JButton("Púlsame");
	JTextField t= new JTextField(20);
	v.getContentPane().add(b);
	v.getContentPane().add(t);

	v.pack(); //optimiza al tamaño minimo requerido
	v.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE); //permite cerrar el programa
	v.setVisible(true); //visualiza la ventana



b.addActionListener (new ActionListener ()
{
   public void actionPerformed (ActionEvent e)
   {
      t.setText ("Hola mundo");
   } 
});


	
/* Esta instruccion permite leer una linea desde la ventana de comandos 
	Scanner sc = new Scanner(System.in);
	String cadena = sc.nextLine();
*/


        }




    public static void main( String[] args )
    {
        System.out.println( "Bienvenidos a 4 en linea" );
	System.out.println("*********************************************************");
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "usuario", "usuario");
	add_new_user();


// cidigo que permite pasar por encima del maping
//@Table("Operator")
//persona extends Model;

/*
	User u = new User();
	u.set("first_name","pepito");
	u.set("last_name","cibrian");
	u.save();

 	User p2 = new User();
	p2.set("first_name","mariano");
	p2.set("last_name","ontivero");
	p2.save();

  Employee e = Employee.findFirst("first_name = ?", "John");

*/

	//User mariano = User.findById(4);
	//User pepito = User.findById(3);
/*
      	Ranking rank1 = Ranking.create("rank","5");
  	Ranking rank2 = Ranking.create("rank","100");

	mariano.add(rank1);
	pepito.add(rank2);
	
*/





       /*Bloque crea un usuario lo guarda y le asigna un ranking 
 
	Rank rank = new Rank();
	rank.set("rank",10);
	u.add(rank);  // Como la relacion un usuario puede tener muchos ranks, automaticamente guarda la asociacion.
       


	User nano = User.findById(2);
	Game game = Game.create();
	if (nano==null)  {
		nano=User.create("first_name","fernando","last_name","muñoz");
		nano.save();
	}
	nano.add(game);


	

	/* Asiendo consultas para levantar algun usuario creado 
	List<User> people = User.where("first_name='fernando'");
	User nano = people.get(0);
	*/



	Base.close();
    }
}
