package learn.gomoku;

import learn.gomoku.game.Gomoku;
import learn.gomoku.game.Result;
import learn.gomoku.game.Stone;
import learn.gomoku.players.HumanPlayer;
import learn.gomoku.players.Player;
import learn.gomoku.players.RandomPlayer;

import java.util.Arrays;
import java.util.Scanner;

public class App {
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("Hello and welcome to Gomoku!");

        Player player1 = selectPlayer(1);
        Player player2 = selectPlayer(2);
        Gomoku gomoku = new Gomoku(player1, player2);
        System.out.printf("%s goes first!\n", gomoku.getCurrent().getName());

        char[][] gameBoard = boardInit();
        while(!gomoku.isOver()){
            //draw board
            drawBoard(gameBoard);
            Stone stone = promptPlacement(gomoku);
            gameBoard[stone.getRow()][stone.getColumn()] = stone.isBlack() ? 'X' : 'O';
        }

    }

    private static Stone promptPlacement(Gomoku gomoku){
        Result result;
        Stone stone;

        //Loops to ensure valid placement
        do{
            System.out.printf("It is %s's turn\n",gomoku.getCurrent().getName());

            stone = gomoku.getCurrent().generateMove(gomoku.getStones());
            if(stone == null){
                int[] coords = promptCoordinates();
                //Values decremented to use 0 index
                stone = new Stone(coords[0]-1, coords[1]-1, gomoku.isBlacksTurn());
            }

            result = gomoku.place(stone);

            if(result.getMessage() != null){
                System.out.println(result.getMessage());
            }

        }while(!result.isSuccess());

        return stone;
    }

    private static int[] promptCoordinates(){
        int[] coords = new int[2];

        System.out.println("Row: ");
        coords[0] = Integer.parseInt(sc.nextLine());
        System.out.println("Column: ");
        coords[1] = Integer.parseInt(sc.nextLine());

        return coords;
    }

    private static void drawBoard(char[][] board){

        //Horizontal coordinates
        for(int i = 0; i <= board[0].length; i++){
            System.out.printf("%-3d",i);
        }
        //next row
        System.out.println("");

        for(int row = 1; row <= board.length; row++){
            //Vertical coordinates
            System.out.printf("%-3d",row);

            //Draw rest of game board
            for(char square : board[row-1]) {
                System.out.printf("%-3c", square);
            }
            //go to the next row
            System.out.println("");
        }
    }

    private static char[][] boardInit(){
        char[][] newBoard = new char[Gomoku.WIDTH][Gomoku.WIDTH];
        for (char[] chars : newBoard) {
            Arrays.fill(chars, '_');
        }

        return newBoard;
    }


    private static Player selectPlayer(int playerNum){
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

    private static String promptPlayerName(int playerNum){
        String playerName;
        System.out.println("Player "+playerNum+", enter your name:  ");
        playerName = sc.nextLine();

        return playerName;
    }
}
