package com.unrc.app;
import com.unrc.app.User;

public class Login {


	User usuario;

	public Login() {
		

	}

	public User loginCheck(String login_email, String login_pass) {
		usuario = User.findFirst("email = ? and password= ?",login_email,login_pass );
		return usuario;

	}
 }
