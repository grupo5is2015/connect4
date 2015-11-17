package com.unrc.app;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class Tern {
    
    private Integer numPlayer;
    private Integer rawSelected;
    private Integer columnSelected;
    
    public Tern(Integer np, Integer rs, Integer cs) {
        numPlayer = np;
        rawSelected = rs;
        columnSelected = cs;
    }

    @Override
    public String toString() {
        return "Tern{" + "numPlayer=" + numPlayer + ", rawSelected=" + rawSelected + ", columnSelected=" + columnSelected + '}';
    }

    public Tern() {
    }

    public void setNumPlayer(Integer numPlayer) {
        this.numPlayer = numPlayer;
    }

    public void setRawSelected(Integer columnSelected) {
        this.rawSelected = rawSelected;
    }
    
    public void setColumnSelected(Integer columnSelected) {
        this.columnSelected = columnSelected;
    }

    public Integer getNumPlayer() {
        return numPlayer;
    }

    public Integer getRawSelected() {
        return rawSelected;
    }
    
    public Integer getColumnSelected() {
        return columnSelected;
    }
    
}
