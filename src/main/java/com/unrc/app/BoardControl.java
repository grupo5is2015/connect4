package com.unrc.app;

/**
 *
 * @author Grupo #5: Muñoz - Ontivero - Rondeau
 *
 */
public class BoardControl {

    private Board b;
    private int[] columnTop;     // cantidad de fichas que tiene cada columna, 0..7.
    private int[] rowToInsert;   // fila donde insertar ficha, 0..5
    //Jugador 1=  1   Jugador 2= -1  El numero 0 representa la celda vacia. 
    // Nota para el sistema la columna empieza en cero y para el usuario en 1
    // Para esto hay que hacer una simple convercion colum + 1


    public Board getBoard () {
      return this.b;
    }
    
    public boolean fullColumn(int c ) {
    
      return columnTop[c]>=b.getNumRow();
    
    }
    
    
    public BoardControl(Board b) {

        this.b = b;
        columnTop = new int[b.getNumCol()];
        rowToInsert = new int[b.getNumCol()];
        for (int i = 0; i < b.getNumCol(); i++) {
            rowToInsert[i] = b.getNumRow() - 1;
        }

    }

    public int getColumnTopValue(int v) {
        return columnTop[v];
    }

    
    public void insertCoin(int player_value, int column) {
        //Recibe datos de columnas validas: 0..n-1
        b.setGrid(rowToInsert[column], column, player_value); // 1 o -1 
        ++columnTop[column];
        --rowToInsert[column];
    }

    /**
     * Indica si un estado dado es un estado final, es decir, un estado sin
     * sucesores. Esto ocurre cuando el tablero esta lleno o alguno de los
     * jugadores logro alinear cuatro fichas consecutivas, ya sea en forma
     * vertical, horizontal o diagonal.
     *
     * @param state es el estado que se analizara para ver si es un estado
     * final.
     * @return true ssi state es un estado final.
     * @pre. state!=null.
     * @post. true es retornado ssi state es un estado final.
     */
    public boolean isTheVictor(boolean lastPlayer) {
    
        int res;
        if (lastPlayer) {
            res = maxLine(1);
        } else {
            res = maxLine(-1);
        }
        return (res >= 4);
    }

    /**
     * Computa el mayor numero de fichas del mismo tipo alineadas
     * consecutivamente en forma vertical.
     *
     * @param board matriz representante del estado actual.
     * @param x valor entero a buscar dentro de board.
     * @return un valor entero, representando el mayor numero de fichas del
     * mismo tipo alineadas consecutivamente en forma vertical. pre. true. post.
     * Un valor entero, representando el mayor numero de fichas del mismo tipo
     * alineadas consecutivamente en forma vertical, es retornado.
     */
    private int maxLineV(int x) { // x=1 | x=-1
        int maxV = 0;
        int count = 0;
        int col = 0;
        while (col < b.getGrid()[0].length) {
            int row = b.getGrid().length - 1; // board.length = nro de filas
            count = 0;
            while (row >= 0 && (b.getGrid()[row][col] != 0)) {
                if (b.getGrid()[row][col] != x) {
                    maxV = Math.max(maxV, count);
                    count = 0;
                } else {
                    count++;
                }
                row--;
            }
            maxV = Math.max(maxV, count);
            col++;
        }
        return maxV;
    }

    /**
     * Computa el mayor numero de fichas del mismo tipo alineadas
     * consecutivamente en forma horizontal.
     *
     * @param board matriz representante del estado actual.
     * @param x valor entero a buscar dentro de board.
     * @return un valor entero, representando el mayor numero de fichas del
     * mismo tipo alineadas consecutivamente en forma horizontal. pre. true.
     * post. Un valor entero, representando el mayor numero de fichas del mismo
     * tipo alineadas consecutivamente en forma horizontal, es retornado.
     */
    private int maxLineH(int x) {
        int maxH = 0;
        int count = 0;
        int row = b.getGrid().length - 1;
        while (row >= 0) {
            int col = 0;
            count = 0;
            while (col < b.getGrid()[0].length) {
                if (b.getGrid()[row][col] != x) {
                    maxH = Math.max(maxH, count);
                    count = 0;
                } else {
                    count++;
                }
                col++;
            }
            maxH = Math.max(maxH, count);
            row--;
        }
        return maxH;
    }

    // La posición (0,0) de la matríz es el extremo superior izquierdo 
     /**
      * Computa el mayor numero de fichas del mismo tipo alineadas
      * consecutivamente en una diagonal descendente.
      *
      * @param board matriz representante del estado actual.
      * @param x valor entero a buscar dentro de board.
      * @return un valor entero, representando el mayor numero de fichas del
      * mismo tipo alineadas consecutivamente en una diagonal descendente de
      * board. pre. true. post. Un valor entero, representando el mayor numero de
      * fichas del mismo tipo alineadas consecutivamente en una diagonal
      * descendente, es retornado.
      */
    private int maxLineDD(int x) {
        int maxDD = 0;
        int count = 0;
        int row = b.getGrid().length;
        int col = b.getGrid()[0].length;
        for (int n = 0; n < row; n++) { // recorrido de la diagonal principal 
            count = 0;		// y la parte inferior del tablero
            for (int i = n, j = 0; i < row && j < col; i++, j++) {
                if (b.getGrid()[i][j] != x) {
                    maxDD = Math.max(maxDD, count);
                    count = 0;
                } else {
                    count++;
                    maxDD = Math.max(maxDD, count);
                }
            }
        }
        for (int j = 1; j < col; j++) { // recorrido de la parte superior del b.getGrid()ro
            count = 0;
            for (int i = 0; i + j < 6; i++) {
                if (b.getGrid()[i][j + i] != x) {
                    maxDD = Math.max(maxDD, count);
                    count = 0;
                } else {
                    count++;
                    maxDD = Math.max(maxDD, count);
                }
            }
        }
        return maxDD;
    }

    /**
     * Computa el mayor numero de fichas del mismo tipo alineadas
     * consecutivamente en una diagonal ascendente.
     *
     * @param board matriz representante del estado actual.
     * @param x valor entero a buscar dentro de board.
     * @return un valor entero, representando el mayor numero de fichas del
     * mismo tipo alineadas consecutivamente en una diagonal ascendente de
     * board. pre. true. post. Un valor entero, representando el mayor numero de
     * fichas del mismo tipo alineadas consecutivamente en una diagonal
     * ascendente, es retornado.
     */
    private int maxLineDA(int x) {
        int maxDA = 0;
        int count = 0;
        int row = b.getGrid().length;
        int col = b.getGrid()[0].length;
        for (int n = 0; n < row; n++) { // recorrido de la diagonal principal y 
            count = 0;		// la parte inferior del tablero
            for (int i = n, j = col - 1; i < row && j >= 0; i++, j--) {
                if (b.getGrid()[i][j] != x) {
                    maxDA = Math.max(maxDA, count);
                    count = 0;
                } else {
                    count++;
                    maxDA = Math.max(maxDA, count);
                }
            }
        }
        for (int n = col - 2; n >= 0; n--) { // recorrido de la parte superior del tablero
            count = 0;
            for (int i = 0, j = n; i < row && j >= 0; i++, j--) {
                if (b.getGrid()[i][j] != x) {
                    maxDA = Math.max(maxDA, count);
                    count = 0;
                } else {
                    count++;
                    maxDA = Math.max(maxDA, count);
                }
            }
        }
        return maxDA;
    }

    /**
     * Computa el mayor nro de fichas alineadas del tipo 'n' (ya sea en forma
     * vertical, horizontal o diagonal) en la matriz 'board'.
     *
     * @param board es una matriz de 6x7 que representa el tablero del juego.
     * @param n es un valor entero que representa el tipo de la ficha a buscar
     * en 'board'. pre. true. post. El mayor nro de fichas alineadas del tipo
     * 'n' (ya sea en forma vertical, horizontal o diagonal) en la matriz
     * 'board' es retornado.
     */
    private int maxLine(int n) {
        int v, h, da, dd;
        v = maxLineV(n);
        h = maxLineH(n);
        da = maxLineDA(n);
        dd = maxLineDD(n);
        return (Math.max(Math.max(v, h), Math.max(da, dd)));
    }
   

    /**
     * Indica si un tablero esta completo, es decir, si no existen lugares
     * libres en el mismo donde colocar una ficha. En este caso, no es posible
     * realizar una nueva jugada.
     *
     * @param m es la matriz representante del tablero.
     * @return true ssi m esta completo.
     * @pre. true.
     * @post. true es retornado ssi m esta completo.
     */
    public boolean fullBoard() {
        boolean res = true;
        int col = 0;
        int numCol = b.getGrid()[0].length;
        while (res && col < numCol) {
            res = res && (b.getGrid()[0][col] != 0);
            col++;
        }
        return res;
    }

}
