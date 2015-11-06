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

    public static String showBoard(boolean turn, Board b) {
         
        String ds = "";
        if (!turn) {
            ds = "disabled";
        }
        String color;
       
      //String whosPlay;
      //String color="white";
      //if(currentTurn==1){
	//whosPlay= player1;
      //}
      //else {
	//}whosPlay=player2;      
      //}
      //String output = "";
      //output += 
        return /*"<!DOCTYPE html>" +
                "<html>" +
                    "<head>" +
                        "<script>" +
                            "$(document).ready(function() {" +
                                "var a = $('#fstColButton); a.clic(function(){" +
                                    "$('#71).css('background-color', 'red')" +
                                "]);" +
              "</script>" + 
"<style type='text/css'>"+
"    .Table"+
"    {"+
"        display: table;"+
"    }"+
"    .Title"+
"    {"+
"        display: table-caption;"+
"        text-align: center;"+
"        font-weight: bold;"+
"        font-size: larger;"+
"    }"+
"    .Heading"+
"    {"+
"        display: table-row;"+
"        font-weight: bold;"+
"        text-align: center;"+
"    }"+
"    .Row"+
"    {"+
"        display: table-row;"+
"    }"+
"    .Cell"+
"    {"+
"        display: table-cell;"+
"        border: solid;"+
"        border-width: thin;"+
"        padding-left: 5px;"+
"        padding-right: 5px;"+
"    }"+
"</style>"+

"</head>"+
"<body>"+
//"<div> Jugador 1: "+player1+" VS Jugador 2: "+player2+". Turno de: "+whosPlay+"   </div>"+

//"</div>"+
"<div class='Table'>"+
//"    <div class='Title'>"+
//"        <p>Cuatro en Línea</p>"+
//"    </div>"+*/
"<div  class='Heading'>" +
"   <div id='01' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fstColBut' value='#1' onClick=\"document.location.href='/play/1'\">" + //onClick= //'playAndRedirect(1)'> </p>" + 
"   </div>" +
"   <div id='02' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sndColBut' value='#2' onClick=\"document.location.href='/play/2'\">" +
"   </div>" +
"   <div id='03' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='thrColBut' value='#3' onClick=\"document.location.href='/play/3'\">" +
"   </div>" +
"   <div id='04' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fourColBut' value='#4' onClick=\"document.location.href='/play/4'\">" +
"   </div>" +
"   <div id='05' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='fiveColBut' value='#5' onClick=\"document.location.href='/play/5'\">" +
"   </div>" +
"   <div id='06' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sixColBut' value='#6' onClick=\"document.location.href='/play/6'\">" +
"   </div>" +
"   <div id='07' class='ButtonCell'>" +
"       <input type='button' " + ds + " id='sthColBut' value='#7' onClick=\"document.location.href='/play/7'\">" +
"   </div>" +
"</div>" +
"<div id='1' class='Row'>"+
"   <div id='11' class='Cell'>"+
//"            <p>" + user + "</p>"+
"   </div>"+
"   <div id='12' class='Cell'>"+
//"            <p>" + player1 + "</p>"+
"   </div>"+
"        <div id='13' class='Cell'>"+
//"            <p>" + player2 + "</p>"+
"        </div>"+
"        <div id='14' class='Cell'>"+
//"            <p>" + turn + "</p>"+
"        </div>"+
"        <div id='15' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='16' class='Cell'>"+ //style='background-color:red'
"            <p></p>"+
"        </div>"+
"        <div id='17' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"    </div>"+
"    <div id='2' class='Row'>"+
"        <div id='21' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='22' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='23' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='24' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='25' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='26' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='27' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"    </div>"+

"    <div id='3' class='Row'>"+
"        <div id='31' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='32' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='33' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='34' class='Cell'>"+
"           <p></p>"+
"        </div>"+
"        <div id='35' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='36' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='37' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"    </div>"+

"    <div id='4' class='Row'>"+
"        <div id='41' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='42' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='43' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='44' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='45' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='46' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='47' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"    </div>"+

"    <div id='5' class='Row'>"+
"        <div id='51' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='52' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='53' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='54' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='55' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='56' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='57' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"    </div>"+

"    <div id='6' class='Row'>"+
"        <div id='61' class='Cell'>"+
//"            <p></p>"+
"        </div>"+
"        <div id='62' class='Cell'>"+
"           <p></p>"+
"        </div>"+
"        <div id='63' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='64' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='65' class='Cell'>"+
"            <p></p>"+
"        </div>"+
"        <div id='66' class='Cell'>"+
"           <p></p>"+
"        </div>"+
"        <div id='67' class='Cell'>"+
"           <p></p>"+
"        </div>"+
"        </div>";
//"</body>"+

//"</html>";

  //return output;
    }
    
    
    
/*
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
    }*/

}
