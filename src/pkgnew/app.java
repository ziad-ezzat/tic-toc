package pkgnew;

import java.util.Scanner;

public class app {

    private static final int Place = 9;

    private static final String AI = "X";
    private static final String NIL = " ";
    private static final String USER = "O";
    private static final String XNAME = "AI";
    private static final String ONAME = "USER";

    private static Scanner S = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        String[] matrix = new String[Place];
        for(int i=1; i<=Place; i++){
            matrix[i-1] = NIL;
        }
        
        StartGame();
        PrintMatrix(matrix);
        while(true){
            UserMove(matrix);
            AIMove(matrix);
            PrintMatrix(matrix);
        }
    }

    private static int MiniMax(String[] matrix, String player){
        if(CheckWin(matrix, AI)){
            return 1;
        }
        else if(CheckWin(matrix, USER)){
            return -1;
        }
        else if(CheckTie(matrix)){
            return 0;
        }

        if(player == AI){
            int GlobalVal = Integer.MIN_VALUE;

            for(int i=1; i<=Place; i++){
                int val = Integer.MIN_VALUE;
                if(matrix[i-1] == NIL){
                    matrix[i-1] = AI;
                    val = MiniMax(matrix, USER);
                    matrix[i-1] = NIL;
                }
                GlobalVal = Math.max(val, GlobalVal);
            }
            return GlobalVal;
        }
        else{
            int GlobalVal = Integer.MAX_VALUE;
            for(int i=1; i<=Place; i++){
                int val = Integer.MAX_VALUE;
                if(matrix[i-1] == NIL){
                    matrix[i-1] = USER;
                    val = MiniMax(matrix, AI);
                    matrix[i-1] = NIL;
                }
                GlobalVal = Math.min(val, GlobalVal);
            }
            return GlobalVal;
        }
    }

    private static void AIMove(String[] matrix){
        int GlobalVal = Integer.MIN_VALUE;
        int position = -1;

        for(int i=1; i<=Place; i++){
            int val = Integer.MIN_VALUE;
            if(matrix[i-1] == NIL){
                matrix[i-1] = AI;
                val = MiniMax(matrix, USER);//max
                matrix[i-1] = NIL;
            }
            if(val > GlobalVal){
                GlobalVal = val;
                position = i;
            }
        }

        InsertMove(matrix, position, AI);
    }

    private static void UserMove(String[] matrix){
        System.out.print("\n> ");
        int position = S.nextInt();
        InsertMove(matrix, position, USER);
    }

    private static void GameTie(String[] matrix){
        // System.out.println("debug-2");
        PrintMatrix(matrix);
        System.out.println("\nGame tied!");
        System.exit(0);
    }

    private static void GameEnd(String[] matrix, String player){
        // System.out.println("debug-3");
        PrintMatrix(matrix);
        if(player == AI){
            System.out.println("\n"+XNAME+" wins!");
        } else {System.out.println("\n"+ONAME+" wins!");}
        System.exit(0);
    }

    private static boolean CheckWin(String[] matrix, String player){
        if((matrix[0] == player) && (matrix[1] == player) && (matrix[2] == player) ||
           (matrix[3] == player) && (matrix[4] == player) && (matrix[5] == player) ||
           (matrix[6] == player) && (matrix[7] == player) && (matrix[8] == player) ||

           (matrix[0] == player) && (matrix[3] == player) && (matrix[6] == player) ||
           (matrix[1] == player) && (matrix[4] == player) && (matrix[7] == player) ||
           (matrix[2] == player) && (matrix[5] == player) && (matrix[8] == player) ||

           (matrix[0] == player) && (matrix[4] == player) && (matrix[8] == player) ||
           (matrix[2] == player) && (matrix[4] == player) && (matrix[6] == player)){
                return true;
        }
        return false;
    }

    private static boolean CheckTie(String[] matrix){
        for(int i=1; i<=Place; i++){
            if(matrix[i-1] == NIL){
                return false;
            }
        }
        return true;
    }

    private static void InsertMove(String[] matrix, int position, String player){
        if((position >= 1) && ((position <= Place))){
            if(PositionEmpty(matrix, position)){
                matrix[position-1] = player;
                if(CheckWin(matrix, player)){
                    GameEnd(matrix, player);
                }
                else if(CheckTie(matrix)){
                    GameTie(matrix);
                }
            }
            else{
                System.out.print("Error! Not a valid position\n\n> ");
                int newposition = S.nextInt();
                InsertMove(matrix, newposition, player);
                return;
            }
        } 
        else{
            System.out.print("Error! Not a valid position\n\n> ");
            int newposition = S.nextInt();
            InsertMove(matrix, newposition, player);
            return;
        }
    }

    private static boolean PositionEmpty(String[] matrix, int position){
        if(matrix[position-1] == NIL){
            return true;
        }
        return false;
    }

    private static void PrintMatrix(String[] matrix){
        System.out.println();
        for(int i=1; i<=Place; i++){
            System.out.print(matrix[i-1]);
            if(((i%3) == 0) && (i != Place)){
                System.out.println("\n--+---+---");
            }
            else if(i != Place){
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private static void StartGame(){
       
        System.out.println("\nRules:\n> The User starts the game and plays first");
        System.out.println("> User is assigned 'O', AI plays as 'X'");
        System.out.println("> To make your move, place [1-9] at any matrix box");
        System.out.println("> Grid boxes being in a horizantal + downward order fashion");
        System.out.print("\nready to play ? [y/n]\n> ");
        Character var = S.next().charAt(0);
        var = Character.toLowerCase(var);
        
        if(var == 'y'){
            return;
        }
        else{
            System.exit(0);
        }
    }
}