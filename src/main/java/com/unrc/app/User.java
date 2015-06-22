package com.unrc.app;

import org.javalite.activejdbc.Model;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class User extends Model {

  static {
    validatePresenceOf("email");    
  }

}
