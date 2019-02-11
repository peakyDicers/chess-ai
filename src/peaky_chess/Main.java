package peaky_chess;

import java.util.Scanner;

import pieces.Color;

public class Main {
	public Main() {
		Board game;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter to color you'd like to play as: white or black.");
		String input = sc.nextLine();
		System.out.println(input);
		
		System.out.println("Enter difficulty: 1, 2, 3, 4, 5");
		int diff = sc.nextInt();
		System.out.println(input);
		if (input.compareTo("white") == 0 ) {
			game = new Board(Color.WHITE, diff);
		} else {
			game = new Board(Color.BLACK, diff);
		}
		game.reset();
		new Display(game);
		
	}

	public static void main(String[] args) {
		new Main();
	}
}
