package com.unrc.app;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.unrc.app.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.javalite.test.jspec.JSpec.the;


public class GameTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/connect4_test", "franco", "franco");
        System.out.println("GameTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("GameTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }


    @Test
    public void shouldValidateGame(){
      Game game = new Game();

      game.set("player1", 10);
      game.set("player2",10);
      
      the(game).shouldBe("valid");
      //assertEquals(game.get("player1").toString(),game.get("player2").toString()); falla si no son iguales
      assertFalse(game.get("player1").toString().equals(game.get("player2").toString()));
      
    }
}
