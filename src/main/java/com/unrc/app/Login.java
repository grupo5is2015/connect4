package com.unrc.app;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class Login {

    public Login() {
    }


    public User loginCheck(String loginEmail, String loginPass) {
        User loggedUser = User.findFirst("email = ? and password = ?", loginEmail, loginPass);
        return loggedUser;
    }

}
