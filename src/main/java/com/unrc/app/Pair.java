/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

/**
 *
 * @author slowhand
 */
public class Pair {
    
    private Integer numPlayer;
    private Integer columnSelect;
    
    public Pair(Integer np, Integer cs) {
        numPlayer = np;
        columnSelect = cs;
    }

    @Override
    public String toString() {
        return "Pair{" + "numPlayer=" + numPlayer + ", columnSelect=" + columnSelect + '}';
    }

    public Pair() {
    }

    public void setNumPlayer(Integer numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void setColumnSelect(Integer columnSelect) {
        this.columnSelect = columnSelect;
    }

    public Integer getNumPlayer() {
        return numPlayer;
    }

    public Integer getColumnSelect() {
        return columnSelect;
    }
    
}
