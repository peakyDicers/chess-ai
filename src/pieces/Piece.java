package pieces;

import java.util.ArrayList;

import peaky_chess.Board;

public abstract class Piece {
	Color team;
	String name;
	int x;
	int y;
	int pieceValue;
	int dir;
	boolean hasMoved;
	ArrayList<int[]> possibleMoves;

	public Piece(Color team, int val) {
		this.possibleMoves = new ArrayList<int[]>();
		this.team = team;
		if (this.team == Color.BLACK)
			this.pieceValue = val*-1;
		else 
			this.pieceValue = val;
	}
	
	public Piece() {
		this.possibleMoves = new ArrayList<int[]>();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.x;
	}
	
	public boolean hasMoved() {
		return this.hasMoved;
	}
	
	public void setMoved() {
		this.hasMoved = true;
	}
	
	public void copyVals(Piece piece) {
		this.team = piece.team;
		this.name = piece.name;
		this.x = piece.x;
		this.y = piece.y;
		this.pieceValue = piece.pieceValue;
		this.hasMoved = piece.hasMoved;
		this.dir = piece.dir;
	}
	
	public Piece(Piece p) {
		this.x = p.x;
		this.y = p.y;
		this.pieceValue = p.pieceValue;
		this.name = p.name;
		this.dir = p.dir;
	}
	
	public int getVal() {
		return this.pieceValue;
	}

	public String getName() {
		return this.name;
	}

	public Color getTeam() {
		return this.team;
	}
	
	public ArrayList<int[]> getMoves(){
		return this.possibleMoves;
	}
	
	public void forceMove(int[] coords, Board board) {
		board.finalizeMove(coords);
		this.x = coords[2];
		this.y = coords[3];
	}

	public boolean move(int[] coords, Board board) {
		for (int i = 0; i < this.possibleMoves.size(); i++) {
			int[] newCoords = this.possibleMoves.get(i);
			if (coords[2] == newCoords[2] && coords[3] == newCoords[3]) {
//				if (resultInCheck(coords, board))
//					return false;
				board.finalizeMove(newCoords);
				this.x = newCoords[2];
				this.y = newCoords[3];
				return true;
			}
		}
		return false;
	}
	
//	private boolean resultInCheck(int[] coords, Board board) {
//		if (board.checkForResultInCheck) {
//			Board iBoard = new Board(board, board.getCurrPlayer());
//			iBoard.checkForResultInCheck = false;
//			iBoard.regenerateAllPossibleMoves(board.getCurrPlayer());
//			iBoard.move(coords);
//			if (iBoard.kingInCheck(board.getCurrPlayer()))
//				return true;
//		}
//		return false;
//	}

	public abstract void generateAllPossibleMoves(Board board);
}
