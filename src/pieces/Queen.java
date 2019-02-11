package pieces;

import peaky_chess.Board;

public class Queen extends Piece {
	public Queen(Color team, int x, int y) {
		super(team, 90);
		this.name = team == Color.BLACK ? "bQ" : "wQ";
		this.x = x;
		this.y = y;
	}
	
	public Queen() {
		super();
	}

	@Override
	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();
		Rook rook = new Rook(this.team, this.x, this.y);
		Bishop bishop = new Bishop(this.team, this.x, this.y);
		
		this.possibleMoves.addAll(rook.getMovesForQueen(board));
		this.possibleMoves.addAll(bishop.getMovesForQueen(board));
	}
}
