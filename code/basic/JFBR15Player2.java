package basic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

public class JFBR15Player2 extends Player {
	int player;
	int searchDepth = 2;
	@Override
	Move nextMove(Position p, List<Move> moves) {
		this.player = p.nextPlayer;
		//System.out.println("---------------------------------");
		List<Integer> values = new ArrayList<>();
		for (Move move:moves) {
			p.move(move);
			int value = miniMax(p, searchDepth, true);
			p.undo();
			values.add(value);
		}
		System.out.println(values);
		return moves.get(getIndexOfMax(values));
	}

	public int getIndexOfMax(List<Integer> arr){
		int max = arr.get(0); // take first as MaxVal
		int indexOfMax = 0; //returns -1 if all elements are equal
		for (int i = 0; i < arr.size(); i++) {
			if(max < arr.get(i)) {
				max = arr.get(i);
				indexOfMax = i;
			}
		}
		System.out.println(indexOfMax);
		return indexOfMax;
	}

	private int miniMax(Position node, int depth, boolean maximizingPlayer){
		String player = "Gegner";
		if(maximizingPlayer)
			player = "Player";
		//base case
		if(depth == 0 || node.isWin()) {
			return bewerten(node, maximizingPlayer);
		}
		
		if(maximizingPlayer){
			//MAX
			int bestValue = Integer.MIN_VALUE;
			for (Move child: node.getMoves()) {
				node.move(child);
				int value = miniMax(node, depth - 1, false);
				node.undo();
				bestValue = Math.max(bestValue, value);
			}
			return bestValue;
		} else {
			//MIN
			int bestValue = Integer.MAX_VALUE;
			for (Move child: node.getMoves()) {
				node.move(child);
				int value = miniMax(node, depth - 1, true);
				node.undo();
				bestValue = Math.min(bestValue, value);
			}
			return bestValue;
		}
	}

	private int bewerten(Position node, boolean maximizingPlayer){
		if(node.isWin()){
			if(maximizingPlayer){
				return 1;
			}else {
				return -1;
			}
		}else {
			return 0;
		}
		//Maybe refactor to return node.getWinner() * player
	}
}
