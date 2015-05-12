package com.unrc.app;

import org.javalite.activejdbc.Model;

public class User extends Model {
   String nickname ;
   String mail;
   String pass;
    
   
   /*Constructor de la clase User
   */
   public User (){    
       nickname ="";
       mail="";
       pass="";
   
   }
   /* Set de la clase User
   
   */
   public void setUser(String n, String m, String p){
       nickname=n;
       mail=m;
       pass= p;
   
       
    /* Gets de la clase User
    */
   
   }
   public String getUserNick (){
       return nickname;
   
   }
   public String getUserMail(){
       return mail;
   }
    
   public String getUserPass(){
       return pass;
   } 
   public String getUserInfo(){
       return nickname +" "+mail+" "+pass;
   }
    
    static {
    validatePresenceOf("first_name");
    
  }

}
