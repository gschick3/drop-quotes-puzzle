package puzzle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

class Puzzle {
	public static void main(String[] args) throws IOException {
		boolean end = false;

		Board board = new Board(loadPuzzle());
		BoardFormat boardFormatter = new BoardFormat(board);

		Scanner input = new Scanner(System.in);
		char rowInput;
		int colInput;
		
		Hints dictionary = new Hints("src/resources/_words.txt");

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
				System.out.println("""

						?\tHelp Menu
						+[r][c]\tPlace a Letter
						*[r][c]\tGet a Hint
						.\tCheck Solution
						x\tErase All Errors
						!\tQuit""");
				break;
			case '+':
				rowInput = inputData.charAt(1);
				colInput = Integer.parseInt(inputData.substring(2));
				System.out.print("Enter letter: ");
				char letter = input.next().charAt(0);
				if (!board.setGuess(rowInput, colInput, letter))
					System.out.println("Invalid Choice");
				break;
			case '*':
				rowInput = inputData.charAt(1);
				colInput = Integer.parseInt(inputData.substring(2));
				List<String> hints = board.findHints(rowInput, colInput, dictionary);
				if(hints.size() > 0) {
					System.out.println("Hints:");
					for(String hint: hints)
						System.out.println(hint);
				} else
					System.out.println("No matches found.");
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
	
	static String loadPuzzle() throws IOException {
		Random r = new Random();
		String fileName = "src/resources/quotes/quote" + r.nextInt(4) + ".txt";
		return Files
				.readString(Paths.get(fileName))
				.toUpperCase();
	}
}