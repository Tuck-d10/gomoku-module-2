package learn.gomoku;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class GameRunner {

    private Gomoku game;
    private GameBoard board;
    private Scanner sc;

    private Player p1;
    private Player p2;

    public void run(){
        do{
            setupGame();
            System.out.printf("%s -vs- %s, %s goes first\n",p1,p2,game.getCurrent().getName());

            playGame();
        }while(continuePlaying());

        System.out.println("Thank you for playing Gomoku!\nNow exiting...");
    }

    public void playGame(){
        while(!game.isOver()){
            board.drawBoard(game);
            promptPlacement(game);
        }

        //Display winner and end board state
        board.drawBoard(game);
    }

    private void promptPlacement(Gomoku gomoku){
        Result result;
        Stone stone;

        //Loops to ensure valid placement
        do{
            System.out.printf("It is %s's turn\n",gomoku.getCurrent().getName());

            stone = gomoku.getCurrent().generateMove(gomoku.getStones());
            if(stone == null){
                Coord coord = promptCoordinates();
                //Values decremented to use 0 index
                stone = new Stone(coord.getRow(), coord.getColumn(), gomoku.isBlacksTurn());
            }else{
                System.out.printf("%s is placing stone at %d-%d\n",
                        gomoku.getCurrent().getName(),stone.getRow(),stone.getColumn());
            }

            result = gomoku.place(stone);

            if(result.getMessage() != null){
                System.out.println(result.getMessage());
            }

        }while(!result.isSuccess());

//        return stone;
    }

    private Coord promptCoordinates(){
        Coord coord = new Coord();

        System.out.println("Row: ");
        coord.setRow(sc.nextLine());
        System.out.println("Column: ");
        coord.setColumn(sc.nextLine());

        return coord;
    }

    public boolean continuePlaying(){
        String answer;
        do{
            System.out.println("Play another game? [y/n]");
            answer = sc.nextLine();
        }while(!answer.equalsIgnoreCase("y") && !answer.equalsIgnoreCase("n"));

        return answer.equalsIgnoreCase("y");
    }

    public void setupGame(){
        p1 = createPlayer(1);
        p2 = createPlayer(2);

        game = new Gomoku(p1,p2);
        board = new GameBoard(game);
    }

    private Player createPlayer(int playerNum){
        Player player = null;

        System.out.println("Player " +playerNum+ " is:\n" +
                "1.  Human\n" +
                "2.  Random Player\n" +
                "Select [1-2]");

        Integer selection = Integer.valueOf(sc.nextLine());

        do{
            if (selection.equals(1)) {
                String playerName = promptPlayerName(playerNum);
                player = new HumanPlayer(playerName);

            }else if(selection.equals(2)){
                player = new RandomPlayer();

            }else{
                System.out.println("Invalid entry select 1 or 2");
                selection = Integer.valueOf(sc.nextLine());

            }
        }while(player == null);

        return player;
    }

    private String promptPlayerName(int playerNum){
        String playerName;
        System.out.println("Player "+playerNum+", enter your name:  ");
        playerName = sc.nextLine();

        return playerName;
    }

    public GameRunner() {
        this.sc = new Scanner(System.in);
    }
}

class Coord {
    private int row;
    private int column;

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setRow(String row) {
        if(row == null || row.isEmpty()){
            this.row = -1;
        }else{
            this.row = parseInt(row);
        }
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setColumn(String column) {
        if(column == null || column.isEmpty()){
            this.column = -1;
        }else{
            this.column = parseInt(column);
        }
    }

    public Coord(){
        this.setColumn(-1);
        this.setRow(-1);
    }

    public Coord(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
