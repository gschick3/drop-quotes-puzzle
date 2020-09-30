package puzzle;

import java.util.Scanner;

class Puzzle {
	public static void main(String[] args) {
		boolean end = false;
		String quote = "Test";
		int rows = 2;

		Board board = new Board(quote, rows);
		BoardFormat boardFormatter = new BoardFormat(board);

		Scanner input = new Scanner(System.in);
		while (!end) {
			boardFormatter.updateBoard(board);
			System.out.print(boardFormatter.format());
			System.out.print("Guess (help/check/quit): ");
			String guess = input.next().toLowerCase();
			input.nextLine(); // just in case there is extra input
			switch (guess) {
			case "check":
				if (board.checkWin()) {
					System.out.println("You Win!");
					end = true;
				}
				break;
			case "quit":
				end = true;
				break;
			default:
				System.out.print("[Row] [Column]: ");
				char row = input.next().charAt(0);
				int col = input.nextInt();
				char letter = guess.charAt(0);
				board.input(row, col, letter);
				break;
			}
		}
		input.close();
	}
}