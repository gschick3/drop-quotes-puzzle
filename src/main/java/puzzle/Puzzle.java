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

		String quote = getQuote();
		Board board = new Board();
		board.loadQuote(quote, quote.length()/15);
		BoardFormat boardFormatter = new BoardFormat(board);

		Scanner input = new Scanner(System.in);
		
		Hints dictionary = new Hints("src/resources/_words.txt");

		while (!end) {
			boardFormatter.updateBoard(board);
			System.out.print(boardFormatter.format());
			System.out.println("Type ? for a list of commands");
			System.out.print("> ");
			String inputData = input.next().toUpperCase();
			var scoreboard = board.getScore();

			char row;
			int col;
			switch (inputData.charAt(0)) {
			case '?':
				System.out.println("""

						?					Help Menu
						+[r][c] [word]		Place a Letter
						*[r][c]				Get a Hint
						.					Check Solution
						x					Erase All Errors
						!					Quit""");
				break;
			case '+':
				row = inputData.charAt(1);
				col = Integer.parseInt(inputData.substring(2));
				String word = input.nextLine().substring(1);
				for (char c : word.toCharArray()) { // move this to Board
					if (col >= board.getCols()) {
						col = 0;
						if (++row - 'A' >= board.getRows()) break;
					}
					board.setGuess(row, col, c);
					col++;
				}
				break;
			case '*':
				row = inputData.charAt(1);
				col = Integer.parseInt(inputData.substring(2));
				List<String> hints = board.findHints(row, col, dictionary);
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
			case 'X':
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
	
	static String getQuote() throws IOException {
		Random r = new Random();
		String fileName = "src/resources/quotes/quote" + r.nextInt(4) + ".txt";
		return Files
				.readString(Paths.get(fileName))
				.toUpperCase();
	}
}