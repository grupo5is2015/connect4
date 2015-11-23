package com.unrc.app;

import com.unrc.app.models.Ranking;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class UserControl {

        public static boolean userRegistration(String email, String pass, String nickName) {

            try {
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_development", "franco", "franco");}
            catch (Exception e) {
            }

            boolean regOK = false;
            User u = User.findFirst("email = ?", email);
            if (u == null) {
                User nu = new User();
                nu.set("email", email);
                nu.set("nickname", nickName);
                nu.set("password", pass);
                nu.saveIt();
                Ranking rnu = new Ranking();
                nu.add(rnu);
                rnu.set("rank", Base.count("users").intValue());
                rnu.set("points", 0);
                rnu.set("won", 0);
                rnu.set("tie", 0);
                rnu.set("lost", 0);
                rnu.saveIt();
                regOK = true;
            }
            Base.close();
            return regOK;
            
        }
    
}
