package learn.gomoku;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Stone;

import java.util.List;

public class GameBoard {
    private String formatString;
    private String blackSymbol;
    private String whiteSymbol;
    private String emptySymbol;

    public void drawBoard(Gomoku game){
        drawCoordinatesRow();

        for(int row = 0; row < Gomoku.WIDTH; row++){
            drawRow(row,game);
        }
    }

    private void drawCoordinatesRow(){
        //Top-left of board at intersection of coordinates
        System.out.printf(formatString,"#");

        //Horizontal coordinates
        for(int i = 0; i < Gomoku.WIDTH; i++){
            drawSquare(Integer.toString(i));
        }

        System.out.println("");
    }

    private void drawRow(int row, Gomoku game){
        //Vertical coordinate
        drawSquare(Integer.toString(row));

        for(int col = 0; col < Gomoku.WIDTH; col++){
            drawSquare(game.getStones(),row,col);
        }

        System.out.println("");
    }

    private void drawSquare(String contents){
        System.out.printf(formatString,contents);
    }

    private void drawSquare(List<Stone> stones, int row, int col){
        for(Stone stone : stones){
            if(doesStoneOccupy(stone,row,col)){
                drawSquare(stone);
                return;
            }
        }
        //only reached if square is empty
        drawEmptySquare();
    }

    public boolean doesStoneOccupy(Stone stone, int row, int col){
        return stone.getColumn() == col && stone.getRow() == row;
    }

    private void drawEmptySquare(){
        drawSquare(emptySymbol);
    }

    private void drawSquare(Stone stone){
        drawSquare(stone.isBlack() ? blackSymbol : whiteSymbol);
    }

    public void setBlackSymbol(String blackSymbol) {
        this.blackSymbol = blackSymbol;
    }

    public void setWhiteSymbol(String whiteSymbol) {
        this.whiteSymbol = whiteSymbol;
    }

    public void setEmptySymbol(String emptySymbol) {
        this.emptySymbol = emptySymbol;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }

    public GameBoard(String formatString, String emptySymbol, String blackSymbol, String whiteSymbol){
                                            //Defaults
        this.formatString = formatString;   //"%-3s"
        this.blackSymbol = blackSymbol;     //X
        this.whiteSymbol = whiteSymbol;     //O
        this.emptySymbol = emptySymbol;     //-
    }

    public GameBoard(Gomoku game){
        this("%-3s", "-", "X", "O");
    }
}
