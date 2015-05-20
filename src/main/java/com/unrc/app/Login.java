package com.unrc.app;
import com.unrc.app.User;

public class Login {


	User loggedUser;

	public Login() {
		

	}


	public User login_check(String loginEmail, String loginPass) {
	  	loggedUser = User.findFirst("email = ? and password = ?", loginEmail, loginPass);
		return loggedUser;
        }

	}
