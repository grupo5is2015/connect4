package com.unrc.app;

import org.javalite.activejdbc.Model;

public class Board {

    private int[][] grid;       //matriz
    private int m; //numero de filas
    private int n; //numero de columnas

    public Board(int m, int n) {
        this.m = m;
        this.n = n;
        grid = new int[m][n];
    }

    public int[][] getGrid() {
        return grid;
    }

    public int getNumRow() {
        return m;
    }

    public int getNumCol() {
        return n;
    }

    public void setGrid(int r, int c, int v) { // v = 1 | v = -1
        System.out.println(r + " " + c + " " + v);
        grid[r][c] = v;
    }

    public String toHtml(boolean turn) {

        String s = "<table align='center' border=1><tr>";
        if (turn) {
            s += "<tr><td> <a href='/savegame'> Guardar</a></td></tr>";
        }
        for (int c = 1; c <= n; c++) {
            if (turn) {
                s += "<td><a href='/play/" + c + "'> #" + c + "</a></td>";
            } else {
                s += "<td>#" + c + "</td>";
            }

        }

        s += "</tr>";

        for (int f = 0; f < m; f++) {

            s += "<tr>";
            for (int c = 0; c < n; c++) {
                if (grid[f][c] == 1) {
                    s = s + "<td width=50 bgcolor='green'></td>";
                } else {
                    if (grid[f][c] == -1) {
                        s = s + "<td width=50 bgcolor='red'>&nbsp; </td>";
                    } else {
                        s = s + "<td width=50 bgcolor='white'>&nbsp; </td>";
                    }
                }
            }
            s = s + "</tr>";
        }

        return s;

    }

    public String toString() {
        String s = "\n\t  --------------------------- \n\t";
        for (int f = 0; f < 6; f++) {
            for (int c = 0; c < 7; c++) {
                if (grid[f][c] == 1) {
                    s = s + " | X";
                } else {
                    if (grid[f][c] == -1) {
                        s = s + " | O";
                    } else {
                        s = s + " | -";
                    }
                }
            }
            s = s + " | \n\t  --------------------------- \n\t";
        }
        s = s + "   1   2   3   4   5   6   7\n";
        return s;
    }

    static {
        //validatePresenceOf("first_name");	
    }

}
