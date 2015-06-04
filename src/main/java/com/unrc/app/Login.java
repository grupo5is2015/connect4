package com.unrc.app;

import com.unrc.app.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Login {

    public Login() {

    }

    public User logMeIn() throws IOException {
        System.out.print("\nIngrese su e-mail: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String email = br.readLine();
        System.out.print("\nIngrese su contraseña: ");
        String pass = br.readLine();
        return loginCheck(email, pass);
    }

    public User loginCheck(String loginEmail, String loginPass) {
        User loggedUser = User.findFirst("email = ? and password = ?", loginEmail, loginPass);
        return loggedUser;
    }

}
