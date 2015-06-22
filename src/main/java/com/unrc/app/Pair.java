package com.unrc.app;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class Pair {
    
    private Integer numPlayer;
    private Integer columnSelected;
    
    public Pair(Integer np, Integer cs) {
        numPlayer = np;
        columnSelected = cs;
    }

    @Override
    public String toString() {
        return "Pair{" + "numPlayer=" + numPlayer + ", columnSelected=" + columnSelected + '}';
    }

    public Pair() {
    }

    public void setNumPlayer(Integer numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void setColumnSelected(Integer columnSelected) {
        this.columnSelected = columnSelected;
    }

    public Integer getNumPlayer() {
        return numPlayer;
    }

    public Integer getColumnSelected() {
        return columnSelected;
    }
    
}
