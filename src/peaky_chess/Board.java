package peaky_chess;

import pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Board implements Cloneable {

	// game settings to play with.
	Color player;
	final boolean testMode = false;

	Position[][] boardRep;
	public final int boardSize = 8;
	ArrayList<Board> boardPossibilities;
	ArrayList<int[]> allPossibleMoves = new ArrayList<int[]>();
	Board bestNextBoard;
	Color turn;
	Color computer;
	int difficulty;
	boolean gameOver;
	public boolean checkForResultInCheck = true;

	public Board(Color team, int diff) {
		checkForResultInCheck = true;
		difficulty = diff;
		player = team;
		gameOver = false;
		bestNextBoard = null;
		boardPossibilities = new ArrayList<Board>();
		boardRep = new Position[8][8];
		turn = player;
		computer = player == Color.WHITE ? Color.BLACK : Color.WHITE;
		for (int i = 0; i < boardRep.length; i++) {
			for (int j = 0; j < boardRep.length; j++) {
				boardRep[i][j] = new Position();
			}
		}
	}
	
	public Board(Board board, Color player) {
		this.turn = player;
		this.boardPossibilities = new ArrayList<Board>();
		// copy board representation.
		this.boardRep = new Position[8][8];

		for (int i = 0; i < boardRep.length; i++) {
			for (int j = 0; j < boardRep.length; j++) {
				boardRep[i][j] = new Position();
			}
		}

		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				if (board.hasPiece(i, j)) {
					Piece piece = board.getPiece(i, j);
					Class<? extends Piece> x = piece.getClass(); // rook pawn
					Piece newPiece = null;
					// rook.. pawn etc. inherits copy constructor from Piece.
					try {
						newPiece = x.newInstance();
						newPiece.copyVals(piece);

					} catch (Exception e) {
						e.printStackTrace();
					}
					this.boardRep[j][i].setPiece(newPiece);
				}
			}
		}
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	public void computerMove() {
		if (this.gameOver)
			return;

		this.setTurn(computer);
		int[] computerMove = this.minimax(this, this.difficulty, computer == Color.WHITE);

		boolean moved = this.move(computerMove);
		if (!moved) {
			System.out.println("Computer move failed.");
		}
		this.setTurn(player);
		this.regenerateAllPossibleMoves(player);
	}

	public boolean hasPiece(int x, int y) {
		return this.boardRep[y][x].hasPiece();
	}

	public Piece getPiece(int x, int y) {
		return this.boardRep[y][x].getPiece();
	}

	private void setPiece(int x, int y, Piece piece) {
		this.boardRep[y][x].setPiece(piece);
	}

	public void setTurn(Color color) {
		this.turn = color;
	}

	private int getHeuristic() {
		int val = 0;
		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				if (this.hasPiece(i, j)) {
					Piece piece = this.getPiece(i, j);
					val += piece.getVal();
					int team = piece.getTeam() == Color.WHITE ? 1 : -1;
					if (i == 3 && j == 3 || i == 4 && j == 3 || i == 3 && j == 4 || i == 4 && j == 4) {
						val += 3 * team;
					}

					if (piece.hasMoved()) {
						val += 1 * team;
					}

					if (piece.getName() == "bK" || piece.getName() == "wK") {
						King king = (King) piece;
						if (king.castled()) {
							val += 10000 * team;
						}
					}
				}
			}
		}
		return val;
	}

	private boolean isTerminal() {
		return false;
	}

	public int[] minimax(Board board, int depth, boolean maximizingPlayer) {
		// x1, y1, x2, y2, heuristic val.

		Color player;
		if (maximizingPlayer)
			player = Color.WHITE;
		else
			player = Color.BLACK;

		if (depth == 0 || board.isTerminal()) {
			int[] result = new int[5];
			result[4] = board.getHeuristic();
			return result;
		}

		board.regenerateAllPossibleMoves(player);

		if (maximizingPlayer) {
			int[] result = new int[5];
			result[4] = Integer.MIN_VALUE;

			for (int i = 0; i < board.allPossibleMoves.size(); i++) {
				// makes the move on imaginary board.
				int[] theMove = board.allPossibleMoves.get(i);

				Board iBoard = new Board(board, player);
				iBoard.regenerateAllPossibleMoves(player);
				iBoard.move(theMove);

				int[] a = minimax(iBoard, depth - 1, false);
				int b = result[4];

				if (a[4] > b) {
					for (int j = 0; j < 4; j++) {
						result[j] = theMove[j];
					}
					result[4] = a[4];
				}
			}
			return result;
		} else {
			int[] result = new int[5];
			result[4] = Integer.MAX_VALUE;

			for (int i = 0; i < board.allPossibleMoves.size(); i++) {
				// makes the move on imaginary board.
				int[] theMove = board.allPossibleMoves.get(i);

				Board iBoard = new Board(board, player);
				iBoard.regenerateAllPossibleMoves(player);
				iBoard.move(theMove);

				int[] a = minimax(iBoard, depth - 1, true);
				int b = result[4];

				if (a[4] < b) {
					for (int j = 0; j < 4; j++) {
						result[j] = theMove[j];
					}
					result[4] = a[4];
				}
			}
			return result;
		}
	}

	public boolean isValidCoordinate(int x, int y) {
		if (x < 0 || x > 7 || y < 0 || y > 7)
			return false;
		return true;
	}

	public Position[][] getBoardRep() {
		return boardRep;
	}
	
	public Color getCurrPlayer() {
		return this.turn;
	}

	// check if TEAM is in check.
//	public boolean kingInCheck(Color currPlayer) {
//		int[] kingCoords = new int[]{-1, -1};
//		String name = currPlayer == Color.WHITE ? "wK" : "bK";
//		for (int i = 0; i < this.boardSize; i++) {
//			for (int j = 0; j < this.boardSize; j++) {
//				if (this.hasPiece(i, j) && this.getPiece(i, j).getName().compareTo(name) == 0) {
//					kingCoords[0] = this.getPiece(i, j).getX();
//					kingCoords[1] = this.getPiece(i, j).getY();
//				}
//			}
//		}
//
//		Color enemyTeam = currPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
//		this.regenerateAllPossibleMoves(enemyTeam);
//		for (int i = 0; i < this.allPossibleMoves.size(); i++) {
//			int[] move = this.allPossibleMoves.get(i);
//			if (move[2] == kingCoords[0] && move[3] == kingCoords[1]) {
//				return true;
//			}
//		}
//		return false;
//	}

	public void regenerateAllPossibleMoves(Color team) {
		this.allPossibleMoves.clear();
		for (int i = 0; i < this.boardSize; i++) {
			for (int j = 0; j < this.boardSize; j++) {
				Piece piece;
				if (this.hasPiece(i, j)) {
					piece = this.getPiece(i, j);
					piece.generateAllPossibleMoves(this);

					if (piece.getTeam() == team)
						this.allPossibleMoves.addAll(piece.getMoves());
				}
			}
		}
	}

	public Color getPlayerTeam() {
		return this.player;
	}

	public boolean move(int[] coords) {
		if (coords == null)
			return false;

		// move the piece.
		Piece piece = this.getPiece(coords[0], coords[1]);

		// check if piece is valid.
		if (piece == null || piece.getTeam() != turn) {
			return false;
		}

		// extra move if castling.
		if (piece.getName() == "wK" || piece.getName() == "bK" && Math.abs(coords[2] - coords[0]) == 2) {
			King king = (King) this.getPiece(coords[0], coords[1]);
			boolean success = piece.move(coords, this);
			if (success) {
				if (coords[2] - coords[0] == 2) { // kingside castle. (move rook)
					Piece rightRook = this.getPiece(7, coords[1]);
					rightRook.forceMove(new int[] { 7, coords[1], 5, coords[1] }, this);
				} else if (coords[2] - coords[0] == -2) {
					Piece leftRook = this.getPiece(0, coords[1]);
					leftRook.forceMove(new int[] { 0, coords[1], 3, coords[1] }, this);
				}
				king.setCastled();
			}
			return success;
		}

		// pawn promotion
		if (piece.getName() == "wP" || piece.getName() == "bP") {
			if (player == Color.BLACK && coords[3] == 7 || player == Color.WHITE && coords[3] == 0) {
				this.setPiece(coords[0], coords[1], null);
				this.setPiece(coords[2], coords[3], new Queen(player, coords[2], coords[3]));
				return true;
			}
		}

		return piece.move(coords, this);
	}

	public void finalizeMove(int[] coords) {

		// move piece
		Piece piece = this.getPiece(coords[0], coords[1]);
		Piece destination = this.getPiece(coords[2], coords[3]);
		if (destination != null && (destination.getName() == "bK" || destination.getName() == "wK"))
			this.gameOver = true;
		this.setPiece(coords[2], coords[3], piece);
		this.setPiece(coords[0], coords[1], null);
		piece.setMoved();
	}

	// set board to inital state.
	public void reset() {
		for (int row = 0; row < boardRep.length; row++) {
			for (int col = 0; col < boardRep.length; col++) {
				switch (row) {
				case 0:
					switch (col) {
					case 0:
					case 7:
						if (!testMode)
							boardRep[row][col].setPiece(new Rook(Color.BLACK, col, row));
						break;
					case 1:
					case 6:
						if (!testMode)
							boardRep[row][col].setPiece(new Knight(Color.BLACK, col, row));
						break;
					case 2:
					case 5:
						if (!testMode)
							boardRep[row][col].setPiece(new Bishop(Color.BLACK, col, row));
						break;
					case 3:
						if (!testMode)
							boardRep[row][col].setPiece(new Queen(Color.BLACK, col, row));
						break;
					case 4:
						if (!testMode)
							boardRep[row][col].setPiece(new King(Color.BLACK, col, row));
						break;
					}
					break;
				case 1:
					if (!testMode)
						boardRep[row][col].setPiece(new Pawn(Color.BLACK, col, row));
					break;

				case 2:
				case 3:
				case 4:
				case 5:
					boardRep[row][col].setPiece(null);
					break;

				case 6:
					boardRep[row][col].setPiece(new Pawn(Color.WHITE, col, row));
					break;

				case 7:
					switch (col) {
					case 0:
					case 7:
						boardRep[row][col].setPiece(new Rook(Color.WHITE, col, row));
						break;
					case 1:
					case 6:
						boardRep[row][col].setPiece(new Knight(Color.WHITE, col, row));
						break;
					case 2:
					case 5:
						boardRep[row][col].setPiece(new Bishop(Color.WHITE, col, row));
						break;
					case 3:
						boardRep[row][col].setPiece(new Queen(Color.WHITE, col, row));
						break;
					case 4:
						boardRep[row][col].setPiece(new King(Color.WHITE, col, row));
						break;
					}
					break;
				}
			}
		}
		this.regenerateAllPossibleMoves(player);
	}

	public void printBoard() {
		for (int i = 0; i < boardRep.length; i++) {
			for (int j = 0; j < boardRep.length; j++) {
				Position temp = boardRep[i][j];
				if (temp.getPiece() == null) {
					System.out.print(" . ");
				} else {
					temp.printPiece();
				}
			}
			System.out.println("");
		}
	}
}
