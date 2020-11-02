package puzzle;

import java.util.Scanner;

class Puzzle {
	public static void main(String[] args) {
		boolean end = false;
		String quote = "testing";
		int rows = 3;

		Board board = new Board(quote, rows);
		BoardFormat boardFormatter = new BoardFormat(board);

		Scanner input = new Scanner(System.in);
		while (!end) {
			boardFormatter.updateBoard(board);
			System.out.print(boardFormatter.format());
			System.out.print("Guess (help/check/fix/quit): ");
			String guess = input.next().toLowerCase();
			input.nextLine(); // just in case there is extra input
			var scoreboard = board.getScore();

			switch (guess) {
			case "check":
				if (scoreboard.hasWon()) {
					System.out.println("You Win!");
					end = true;
				} else {
					System.out.println("You have " + scoreboard.getErrors().size() + " error(s)");
				}
				break;
			case "fix":
				board.eraseErrors();
				break;
			case "quit":
				end = true;
				break;
			default:
				System.out.print("[Letter] [Number]: ");
				char row = input.next().charAt(0);
				int col = input.nextInt();
				char letter = guess.charAt(0);
				if (!board.setGuess(row, col, letter))
					System.out.println("Invalid Choice");
				break;
			}
		}
		input.close();
	}
}