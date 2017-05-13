package basic;

import javafx.geometry.Pos;

import java.util.Arrays;

/**
 * Created by jonas on 13.05.17.
 */
public class Testing {
    int player = 1;
    public static void main(String [] args)
    {
        int X = 8;
        int P = 1;
        int G = -1;
        int[][] board = {
                {X,X,X,X,X,X,X,X,X},
                {X,0,0,0,0,0,0,0,X},
                {X,0,0,0,0,0,0,0,X},
                {X,0,0,0,0,0,0,0,X},
                {X,0,0,0,0,0,0,0,X},
                {X,0,0,G,0,0,0,0,X},
                {X,0,0,G,0,P,0,0,X},
                {X,0,0,G,0,P,0,0,X},
                {X,X,X,X,X,X,X,X,X},
        };

        Position p = new Position();
        p.board = board;
        int points = bewerte(p);
        System.out.println("Points: " + points);
    }
    private static int bewerte (Position p){
        int points = 0;
        int player = 1;
        points += bewerteX(p.board, player );
        points += bewerteY(p.board, player);
        points += bewerteDiagOne(p.board, player);
        points += bewerteDiagTwo(p.board, player);
        return points;
    }
    // [ | | | ]
    private static int bewerteDiagOne(int[][] board, int player){
        int points = 0;
        for (int x = 1; x <= 4 ; x++) {
            for (int y = 1; y <= 4 ; y++) {
                int[] w = {
                        board[x][y],
                        board[x+1][y+1],
                        board[x+2][y+2],
                        board[x+3][y+3]
                };
                //System.out.println(Arrays.toString(w));
                points += bewerteWindow(w, player);
            }
        }
        return points;
    }
    // [ | | | ]
    private static int bewerteDiagTwo(int[][] board, int player){
        int points = 0;
        for (int x = 1; x <= 4 ; x++) {
            for (int y = 1; y <= 4 ; y++) {
                int[] w = {
                        board[x][y+3],
                        board[x+1][y+2],
                        board[x+2][y+1],
                        board[x+3][y]
                };
                //System.out.println(Arrays.toString(w));
                points += bewerteWindow(w, player);
            }
        }
        return points;
    }
    // [ | | | ]
    private static int bewerteX(int[][] board, int player){
        int points = 0;
        for (int x = 1; x <= 4 ; x++) {
            for (int y = 1; y <= 7 ; y++) {
                int[] w = {
                        board[x][y],
                        board[x+1][y],
                        board[x+2][y],
                        board[x+3][y]
                };
                //System.out.println(Arrays.toString(w));
                points += bewerteWindow(w, player);
            }
        }
        return points;
    }
    // [ | | | ]
    private static int bewerteY(int[][] board, int player){
        int points = 0;
        for (int x = 1; x <= 7 ; x++) {
            for (int y = 1; y <= 4 ; y++) {
                int[] w = {
                        board[x][y],
                        board[x][y+1],
                        board[x][y+2],
                        board[x][y+3]
                };
                points += bewerteWindow(w, player);
            }
        }
        return points;
    }
    private static int bewerteWindow(int [] w, int p){
        int countPlayer = 0;
        int countAntiPlayer = 0;
        for (int i = 0; i < w.length; i++) {
            if (w[i]==p){
                countPlayer++;
            } else if(w[i]==-p) {
                countAntiPlayer++;
            }
        }
        if(countPlayer > 0 && countAntiPlayer > 0)
            return 0;
        if(countPlayer == 0 && countAntiPlayer == 0)
            return 0;
        if(countPlayer == 4)
            return 100000000;
        if(countAntiPlayer == 4)
            return -100000000;
        if(countPlayer == 3 && countAntiPlayer == 0)
            return 10000;
        if(countAntiPlayer == 3 && countPlayer == 0)
            return -10000;
        if(countPlayer == 2 && countAntiPlayer == 0)
            return 100;
        if(countAntiPlayer == 2 && countPlayer == 0)
            return -100;
        if(countPlayer == 1 && countAntiPlayer == 0)
            return 10;
        if(countAntiPlayer == 1 && countPlayer == 0)
            return -10;
        return 0;
    }
}
