package com.unrc.app;

import org.javalite.activejdbc.Base;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class Login {

    public Login() {
    }


    public User loginCheck(String loginEmail, String loginPass) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");
        User loggedUser = User.findFirst("email = ? and password = ?", loginEmail, loginPass);
        Base.close();
        return loggedUser;
    }

}
