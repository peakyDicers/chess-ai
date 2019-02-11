package pieces;

import java.util.ArrayList;

import peaky_chess.Board;

public class Bishop extends Piece {
	public Bishop(Color team, int x, int y) {
		super(team, 30);
		this.name = team == Color.BLACK ? "bB" : "wB";
		this.x = x;
		this.y = y;
	}
	
	public Bishop() {
		super();
	}
	
	public ArrayList<int[]> getMovesForQueen(Board board) {
		this.generateAllPossibleMoves(board);
		return this.possibleMoves;
	}
	
	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();

		// checks top right. ---------------------------------------------
		int newX = this.x + 1;
		int newY = this.y - 1;

		boolean positionValid = board.isValidCoordinate(newX, newY);
		boolean pieceInWay = false;
		if (positionValid)
			pieceInWay = board.hasPiece(newX, newY);
		

		while (!pieceInWay && positionValid) {
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

			// prepares to check next piece.
			newX++;
			newY--;
			positionValid = board.isValidCoordinate(newX, newY);
			if (positionValid)
				pieceInWay = board.hasPiece(newX, newY);

		}
		if (positionValid && board.getPiece(newX, newY).getTeam() != this.team)
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

		// checks bottom right. ---------------------------------------------
		newX = this.x + 1;
		newY = this.y + 1;

		positionValid = board.isValidCoordinate(newX, newY);
		if (positionValid)
			pieceInWay = board.hasPiece(newX, newY);

		while (!pieceInWay && positionValid) {
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

			// prepares to check next piece.
			newX++;
			newY++;
			positionValid = board.isValidCoordinate(newX, newY);
			if (positionValid)
				pieceInWay = board.hasPiece(newX, newY);

		}
		if (positionValid && board.getPiece(newX, newY).getTeam() != this.team)
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

		// checks top left. ---------------------------------------------
		newX = this.x - 1;
		newY = this.y - 1;

		positionValid = board.isValidCoordinate(newX, newY);
		if (positionValid)
			pieceInWay = board.hasPiece(newX, newY);

		while (!pieceInWay && positionValid) {
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

			// prepares to check next piece.
			newX--;
			newY--;
			positionValid = board.isValidCoordinate(newX, newY);
			if (positionValid)
				pieceInWay = board.hasPiece(newX, newY);

		}
		if (positionValid && board.getPiece(newX, newY).getTeam() != this.team)
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

		// checks bottom left. ---------------------------------------------
		newX = this.x - 1;
		newY = this.y + 1;

		positionValid = board.isValidCoordinate(newX, newY);
		if (positionValid)
			pieceInWay = board.hasPiece(newX, newY);

		while (!pieceInWay && positionValid) {
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

			// prepares to check next piece.
			newX--;
			newY++;
			positionValid = board.isValidCoordinate(newX, newY);
			if (positionValid)
				pieceInWay = board.hasPiece(newX, newY);

		}
		if (positionValid && board.getPiece(newX, newY).getTeam() != this.team)
			this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });

	}
}
