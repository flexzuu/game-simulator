package basic;

import java.util.List;

public class PlayerJonas extends Player {
	@Override
	Move nextMove(Position p, List<Move> moves) {
		for (Move move:moves) {
			p.move(move);
			if(p.isWin())
				return move;
			for (Move gegnerMoves:p.getMoves()) {
				p.move(gegnerMoves);
				if(!p.isWin())
					return move;
				p.undo();
			}
			p.undo();
		}
		return moves.get(0);
	}

//	private int bewerten(Position node){
//		int winner = node.getWinner();
//		if(winner == player){
//			return 1;
//		}else if(winner == -player){
//			return -1;
//		}else {
//			return 0;
//		}
//		//Maybe refactor to return node.getWinner() * player
//	}
}
