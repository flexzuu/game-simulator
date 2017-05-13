package basic;


import javafx.geometry.Pos;
import plotter.Sleep;

import java.util.ArrayList;
import java.util.List;

public class JFBR15PlayerAlpha extends Player {
	Move bestMove = null;
	int player;
	int searchDepth = 3;
	@Override
	Move nextMove(Position p, List<Move> moves) {
		this.player = p.nextPlayer;
		bestMove = moves.get(0);
		max(p, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return bestMove;
	}

	private int max(Position p, int depth, int alpha, int beta){
		if(depth == 0 || p.isWin()) return bewerte(p);

		int maxValue = alpha;
		List<Move> moves = p.getMoves();
		for (Move move:moves) {
			Position position = new Position(p);
			position.move(move);
			position.nextPlayer();
			int value = min(position, depth - 1, maxValue, beta);
			if(value > maxValue){
				maxValue = value;
				if(maxValue >= beta){
					break;
				}
				if(depth == searchDepth){
					bestMove = move;
				}
			}
		}
		return maxValue;
	}

	private int min(Position p, int depth, int alpha, int beta) {
		if(depth == 0 || p.isWin()) bewerte(p);
		int minValue = beta;
		List<Move> moves = p.getMoves();
		for (Move move:moves){
			Position position = new Position(p);
			position.move(move);
			position.nextPlayer();
			int value = max(position, depth - 1 , alpha, minValue);
			if(value < minValue){
				minValue = value;
				if(minValue <= alpha){
					break;
				}
			}
		}
		return minValue;
	}

	private int bewerten(Position p) {
		if (p.isWin())
			if (p.nextPlayer == player) {
				return 1000000000;
			} else {
				return -1000000000;
			}
		return bewerte(p);
	}

	private int bewerte (Position p){
		int points = 0;
		int player = this.player;
		points += bewerteX(p.board, player );
		points += bewerteY(p.board, player);
		points += bewerteDiagOne(p.board, player)/2;
		points += bewerteDiagTwo(p.board, player)/2;
		return points;
	}
	// [ | | | ]
	private int bewerteDiagOne(int[][] board, int player){
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
	private int bewerteDiagTwo(int[][] board, int player){
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
	private int bewerteX(int[][] board, int player){
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
	private int bewerteY(int[][] board, int player){
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
	private int bewerteWindow(int [] w, int p){
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
			return -1000000000;
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
