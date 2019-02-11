package pieces;

import java.util.ArrayList;

import peaky_chess.Board;

public class Pawn extends Piece {
	public Pawn(Color team, int x, int y) {
		super(team, 10);
		this.name = team == Color.BLACK ? "bP" : "wP";
		this.hasMoved = false;
		this.x = x;
		this.y = y;
		this.dir = team == Color.WHITE ? -1 : 1;
	}

	public Pawn() {
		super();
	}

	@Override
	public boolean move(int[] coords, Board board) {
		for (int i = 0; i < this.possibleMoves.size(); i++) {
			int[] newCoords = this.possibleMoves.get(i);
			if (newCoords[2] == coords[2] && newCoords[3] == coords[3]) {
				board.finalizeMove(newCoords);
				this.hasMoved = true;
				this.x = newCoords[2];
				this.y = newCoords[3];
				return true;
			}
		}
		return false;
	}

	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();
		// adding moving two spaces forward.
		if (!this.hasMoved) {
			if (!board.hasPiece(x, y + dir) && !board.hasPiece(x, y + 2 * dir)) {
				int newY = y + 2 * (dir);
				if (board.isValidCoordinate(x, newY)) {
					int[] move = { x, y, x, newY };
					this.possibleMoves.add(move);
				}
			}
		}

		// adding move one space forward.
		if (board.isValidCoordinate(x, y + dir))
			if (!board.hasPiece(x, y + dir))
				this.possibleMoves.add(new int[] { x, y, x, y + dir });

		// adding top right attack.
		int newX = x + 1;
		int newY = y + dir;
		if (board.isValidCoordinate(newX, newY))
			if (board.hasPiece(newX, newY) && board.getPiece(newX, newY).getTeam() != this.team)
				this.possibleMoves.add(new int[] { x, y, newX, newY });

		newX = x - 1;
		if (board.isValidCoordinate(newX, newY))
			if (board.hasPiece(newX, newY) && board.getPiece(newX, newY).getTeam() != this.team)
				this.possibleMoves.add(new int[] { x, y, newX, newY });
	}
}
