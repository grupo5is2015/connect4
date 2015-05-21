package com.unrc.app;

import com.unrc.app.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login {

    public Login() {

    }

    public User logInMe() throws IOException {
        System.out.print("\nIngrese su e-mail: ");
        BufferedReader b3 = new BufferedReader(new InputStreamReader(System.in));
        String email = b3.readLine();
        System.out.print("\nIngrese su contraseña: ");
        String pass = b3.readLine();
        return loginCheck(email, pass);
    }

    private static User loginCheck(String loginEmail, String loginPass) {
        User loggedUser = User.findFirst("email = ? and password = ?", loginEmail, loginPass);
        return loggedUser;
    }

}
