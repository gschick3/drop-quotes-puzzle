package puzzle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Puzzle {
	public static void main(String[] args) throws IOException {
		boolean end = false;
		String quote = "testing";
		int rows = 3;

		Board board = new Board(quote, rows);
		BoardFormat boardFormatter = new BoardFormat(board);

		Scanner input = new Scanner(System.in);
		char rowInput;
		int colInput;
		
		Hints dictionary = listFromFile("_words.txt");

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
	
	public static Hints listFromFile(String fileName) throws IOException {
		return new Hints(Files
				.readAllLines(Paths.get(fileName))
				.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList())
				);
	}
}