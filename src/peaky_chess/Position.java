package peaky_chess;

import pieces.*;

public class Position {
	
	private Piece piece;
	
	public Position() {
		piece = null;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public boolean hasPiece() {
		return this.piece != null;
	}
	
	public void printPiece() {
		System.out.print(this.piece.getName() + " ");
	}
}
