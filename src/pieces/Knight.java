package pieces;

import peaky_chess.Board;

public class Knight extends Piece {
	public Knight(Color team, int x, int y) {
		super(team, 30);
		this.name = team == Color.BLACK ? "bN" : "wN";
		this.x = x;
		this.y = y;
	}
	
	public Knight() {
		super();
	}

	public void generateAllPossibleMoves(Board board) {
		this.possibleMoves.clear();

		int newX = this.x + 1;
		int newY = this.y - 2;
		this.check(newX, newY, board);

		newX = this.x + 2;
		newY = this.y - 1;
		this.check(newX, newY, board);

		newX = this.x + 2;
		newY = this.y + 1;
		this.check(newX, newY, board);

		newX = this.x + 1;
		newY = this.y + 2;
		this.check(newX, newY, board);

		// =========================
		newX = this.x - 1;
		newY = this.y - 2;
		this.check(newX, newY, board);

		newX = this.x - 2;
		newY = this.y - 1;
		this.check(newX, newY, board);

		newX = this.x - 2;
		newY = this.y + 1;
		this.check(newX, newY, board);

		newX = this.x - 1;
		newY = this.y + 2;
		this.check(newX, newY, board);

		// print all moves.
//		System.out.println("--------------------");
//		for (int i = 0; i < this.possibleMoves.size(); i++) {
//			int[] move = this.possibleMoves.get(i);
//			for (int j = 0; j < move.length; j++) {
//				System.out.print(move[j] + " ");
//			}
//			System.out.println("");
//		}

	}

	private void check(int newX, int newY, Board board) {

		boolean positionValid = board.isValidCoordinate(newX, newY);
		boolean hasPiece = false;
		boolean isEnemyTeam = false;

		if (positionValid)
			hasPiece = board.hasPiece(newX, newY);

		if (hasPiece)
			isEnemyTeam = board.getPiece(newX, newY).getTeam() != this.team;

		if (positionValid) {
			if (hasPiece && isEnemyTeam)
				this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });
			else if (!hasPiece)
				this.possibleMoves.add(new int[] { this.x, this.y, newX, newY });
		}
	}

}
