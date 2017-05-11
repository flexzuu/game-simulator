package basic;

import java.util.List;

public class JFBR15Player extends Player {
	int player;
	Move bestMove = null;
	int searchDepth = 7;
	@Override
	Move nextMove(Position p, List<Move> moves) {
		this.player = -p.nextPlayer;
		bestMove = moves.get(0);
		miniMax(p, searchDepth, true);
		//System.out.println("---------------------------------");

		return bestMove;
	}

	private int miniMax(Position node, int depth, boolean maximizingPlayer){
		String player = "Gegner";
		if(maximizingPlayer)
			player = "Player";
		//base case
		if(depth == 0 || node.isWin()) {
			return bewerten(node);
		}
		
		if(maximizingPlayer){
			//MAX
			int bestValue = Integer.MIN_VALUE;
			for (Move child: node.getMoves()) {
				Position childPosition = new Position(node);
				childPosition.move(child);
				int value = miniMax(childPosition, depth - 1, false);
				if (value > bestValue) {
					bestValue = value;
					if(searchDepth == depth)
						bestMove = child;
				}
			}
			return bestValue;
		} else {
			//MIN
			int bestValue = Integer.MAX_VALUE;
			for (Move child: node.getMoves()) {
				Position childPosition = new Position(node);
				childPosition.move(child);
				int value = miniMax(childPosition, depth - 1, true);
				bestValue = Math.min(bestValue, value);
			}
			return bestValue;
		}
	}

	private int bewerten(Position node){
		int winner = node.getWinner();
		if(winner == player){
			return 1;
		}else if(winner == -player){
			return -1;
		}else {
			return 0;
		}
		//Maybe refactor to return node.getWinner() * player
	}
}
