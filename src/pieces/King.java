package pieces;

import peaky_chess.Board;

public class King extends Piece {
	boolean castled;
	public King(Color team, int x, int y) {
		super(team, 10000);
		this.name = team == Color.BLACK ? "bK" : "wK";
		this.x = x;
		this.y = y;
		this.hasMoved = false;
		castled = false;
	}

	public King() {
		super();
	}
	
	public boolean castled() {
		return this.castled;
	}
	
	public void setCastled() {
		this.castled = true;
	}

	@Override
	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				checkMove(i, j, board);
			}
		}

		// castling king side.
		if (!this.hasMoved && !board.hasPiece(this.x + 1, this.y) && !board.hasPiece(this.x + 2, this.y)
				&& board.hasPiece(this.x + 3, this.y) && !board.getPiece(this.x + 3, this.y).hasMoved()) {
			this.possibleMoves.add(new int[] { this.x, this.y, this.x + 2, this.y });
		}

		// castling queen side.
		if (!this.hasMoved && !board.hasPiece(this.x - 1, this.y) && !board.hasPiece(this.x - 2, this.y)
				&& !board.hasPiece(this.x - 3, this.y) && board.hasPiece(this.x - 4, this.y)
				&& !board.getPiece(this.x - 4, this.y).hasMoved()) {
			this.possibleMoves.add(new int[] { this.x, this.y, this.x - 2, this.y });
		}
	}

	private void checkMove(int newX, int newY, Board board) {
		if (board.isValidCoordinate(newX, newY)) {
			if (board.hasPiece(newX, newY) && board.getPiece(newX, newY).getTeam() == this.team)
				return;
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });
		}
	}

}
