package pieces;

import java.util.ArrayList;

import peaky_chess.Board;

public class Rook extends Piece {
	public Rook(Color team, int x, int y) {
		super(team, 50);
		this.x = x;
		this.y = y;
		this.name = team == Color.BLACK ? "bR" : "wR";
	}
	
	public Rook() {
		super();
	}
	
	public ArrayList<int[]> getMovesForQueen(Board board) {
		this.generateAllPossibleMoves(board);
		return this.possibleMoves;
	}

	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();
		int newX, newY;

		// moving left.
		for (int i = x - 1; i >= 0; i--) {
			newX = i;
			newY = y;
			boolean result = addMove(newX, newY, board);
			if (!result)
				break;
		}

		// moving right
		for (int i = x + 1; i < board.boardSize; i++) {
			newX = i;
			newY = y;
			boolean result = addMove(newX, newY, board);
			if (!result)
				break;
		}

		// moving up
		for (int i = y - 1; i >= 0; i--) {
			newX = x;
			newY = i;
			boolean result = addMove(newX, newY, board);
			if (!result)
				break;
		}

		// moving down
		for (int i = y + 1; i < board.boardSize; i++) {
			newX = x;
			newY = i;
			boolean result = addMove(newX, newY, board);
			if (!result)
				break;
		}
	}

	private boolean addMove(int newX, int newY, Board board) {
		if (board.hasPiece(newX, newY) && board.getPiece(newX, newY).getTeam() == this.team) {
			return false;
		} else if (board.hasPiece(newX, newY) && board.getPiece(newX, newY).getTeam() != this.team) {
			this.possibleMoves.add(new int[] { x, y, newX, newY });
			return false;
		} else {
			this.possibleMoves.add(new int[] { x, y, newX, newY });
			return true;
		}
	}
}
