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
		char rowInput;
		int colInput;

		while (!end) {
			boardFormatter.updateBoard(board);
			System.out.print(boardFormatter.format());
			System.out.println("Type ? for a list of commands");
			System.out.print("> ");
			String inputData = input.next().toLowerCase();
			input.nextLine(); // just in case there is extra input
			var scoreboard = board.getScore();

			switch (inputData.charAt(0)) {
			case '?':
				System.out.println("\n" 
						+ "?\tHelp Menu\n" 
						+ "+[r][c]\tMake Guess\n" 
						+ "*[r][c]\tGet a Hint\n"
						+ ".\tCount Errors\n" 
						+ "x\tErase All Errors\n" 
						+ "!\tQuit");
				break;
			case '+':
				rowInput = inputData.charAt(1);
				colInput = Integer.parseInt(inputData.substring(2, inputData.length()));
				System.out.print("Enter guess: ");
				char letter = input.next().charAt(0);
				if (!board.setGuess(rowInput, colInput, letter))
					System.out.println("Invalid Choice");
				break;
			case '*':
				rowInput = inputData.charAt(1);
				colInput = Integer.parseInt(inputData.substring(2, inputData.length()));
				System.out.println("Not yet implemented.");
				break;
			case '.':
				if (scoreboard.hasWon()) {
					System.out.println("You Win!");
					end = true;
				} else {
					System.out.println("You have " + scoreboard.getErrors().size() + " error(s)");
				}
				break;
			case 'x':
				board.eraseErrors();
				break;
			case '!':
				end = true;
				break;
			default:
				System.out.println("Invalid input");
				break;
			}
		}
		input.close();
	}
}