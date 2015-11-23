package com.unrc.app.models;

import com.unrc.app.User;
import java.util.Iterator;
import java.util.List;
import org.javalite.activejdbc.Model;

/**
 *
 * @author Grupo #5: Ontivero - Rondeau - Zabala
 *
 */
public class Ranking extends Model {

    public static Integer pos = 0;

    static {
        validatePresenceOf("id", "rank", "points", "won", "tie", "lost", "user_id", "year");
        validatePresenceOf("ranking");
    }

    public String id() {
        return String.valueOf(this.getInteger("id"));
    }

    public String points() {
        return String.valueOf(this.getInteger("points"));
    }

    public String won() {
        return String.valueOf(this.getInteger("won"));
    }

    public String tie() {
        return String.valueOf(this.getInteger("tie"));
    }

    public String lost() {
        return String.valueOf(this.getInteger("lost"));
    }

    public String user_id() {
        return this.getString("user_id");
    }

    public String year() {
        return String.valueOf(this.getInteger("year"));
    }

    public Integer rank() {
        pos = pos + 1;
        return pos;

    }

    public String email() {
        User u = User.findById(this.get("user_id"));
        return u.get("email").toString();
    }

}
