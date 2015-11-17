package com.unrc.app;

/**
 *
 * @author Grupo #5: Ontivero - Rondeau - Zabala
 *
 */
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
        grid[r][c] = v;
    }

    public String toHtml(boolean turn) {

        String s = "<table align='center' border=1 width=\"450\"><tr>";
        for (int c = 1; c <= n; c++) {
            if (turn) {
                s += "<td class='menuitem'><a href='/play/" + c + "'> #" + c + "</a></td>";
            } else {
                s += "<td class='menuitem'>#" + c + "</td>";
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
        
        if (turn) {
            s += "<tr><td colspan=7 class='menuitem'> <a href='/savegame'> Guardar</a></td></tr>";
        }
        s+="</table>";

        return s;

    }

    public static String showBoard(boolean turn) {
         
        String ds = "";
        if (!turn) {
            ds = "disabled";
        }
        String color;
        
        return "<div class='Heading'>" +
"   <div id='h1' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fstColBut' value='#1'>"+                
"   </div>" +
"   <div id='h2' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sndColBut' value='#2'>"+
"   </div>" +
"   <div id='h3' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='thrColBut' value='#3'>"+
"   </div>" +
"   <div id='h4' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fourColBut' value='#4'>"+
"   </div>" +
"   <div id='h5' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fiveColBut' value='#5'>"+
"   </div>" +
"   <div id='h6' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sixColBut' value='#6'>"+ 
"   </div>" +
"   <div id='h7' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sthColBut' value='#7'>"+ 
"   </div>" +
"</div>" +
"<div id='0' class='Row'>"+
"   <div id='00' class='Cell'>"+
"   </div>"+
"   <div id='01' class='Cell'>"+
"   </div>"+
"        <div id='02' class='Cell'>"+
"        </div>"+
"        <div id='03' class='Cell'>"+
"        </div>"+
"        <div id='04' class='Cell'>"+
"        </div>"+
"        <div id='05' class='Cell'>"+ 
"        </div>"+
"        <div id='06' class='Cell'>"+
"        </div>"+
"    </div>"+
"    <div id='1' class='Row'>"+
"        <div id='10' class='Cell'>"+
"        </div>"+
"        <div id='11' class='Cell'>"+
"        </div>"+
"        <div id='12' class='Cell'>"+
"        </div>"+
"        <div id='13' class='Cell'>"+
"        </div>"+
"        <div id='14' class='Cell'>"+
"        </div>"+
"        <div id='15' class='Cell'>"+
"        </div>"+
"        <div id='16' class='Cell'>"+
"        </div>"+
"    </div>"+

"    <div id='2' class='Row'>"+
"        <div id='20' class='Cell'>"+
"        </div>"+
"        <div id='21' class='Cell'>"+
"        </div>"+
"        <div id='22' class='Cell'>"+
"        </div>"+
"        <div id='23' class='Cell'>"+
"        </div>"+
"        <div id='24' class='Cell'>"+
"        </div>"+
"        <div id='25' class='Cell'>"+
"        </div>"+
"        <div id='26' class='Cell'>"+
"        </div>"+
"    </div>"+

"    <div id='3' class='Row'>"+
"        <div id='30' class='Cell'>"+
"        </div>"+
"        <div id='31' class='Cell'>"+
"        </div>"+
"        <div id='32' class='Cell'>"+
"        </div>"+
"        <div id='33' class='Cell'>"+
"        </div>"+
"        <div id='34' class='Cell'>"+
"        </div>"+
"        <div id='35' class='Cell'>"+
"        </div>"+
"        <div id='36' class='Cell'>"+
"        </div>"+
"    </div>"+

"    <div id='4' class='Row'>"+
"        <div id='40' class='Cell'>"+
"        </div>"+
"        <div id='41' class='Cell'>"+
"        </div>"+
"        <div id='42' class='Cell'>"+
"        </div>"+
"        <div id='43' class='Cell'>"+
"        </div>"+
"        <div id='44' class='Cell'>"+
"        </div>"+
"        <div id='45' class='Cell'>"+
"        </div>"+
"        <div id='46' class='Cell'>"+
"        </div>"+
"    </div>"+

"    <div id='5' class='Row'>"+
"        <div id='50' class='Cell'>"+
"        </div>"+
"        <div id='51' class='Cell'>"+
"        </div>"+
"        <div id='52' class='Cell'>"+
"        </div>"+
"        <div id='53' class='Cell'>"+
"        </div>"+
"        <div id='54' class='Cell'>"+
"        </div>"+
"        <div id='55' class='Cell'>"+
"        </div>"+
"        <div id='56' class='Cell'>"+
"        </div>"+
"        </div>";
    }
    

}
