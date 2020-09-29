class Board {
	private static char emptySpace = '_';
	private static char space = '/';
	private static String separator = "|";

	private String quote;
	private int rows;
	private int columns;

	private char[][] quoteArray;
	private char[][] clues;
	private char[][] currentBoard;

	public Board(String quote, int rows) {
		this.quote = quote;
		this.rows = rows;
		fillBoard();
	}

	public void fillBoard() {
		char[] normalizedQuote = this.normalizeString(this.quote);
		this.quoteArray = Array.dimensionalize(normalizedQuote, this.rows); // fill complete 2d array with uppercase
																			// letters
		this.clues = Array.sortArray(Array.transposeArray(quoteArray)); // randomized array with letters from
																		// each column
		this.columns = this.quoteArray[0].length; // number of characters in each row of 2d array

		this.currentBoard = new char[this.rows][this.columns]; // create empty board for player

		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.columns; c++) {
				if (this.quoteArray[r][c] == ' ') {
					this.quoteArray[r][c] = space;
					this.currentBoard[r][c] = space;
				} else {
					this.currentBoard[r][c] = emptySpace;
				}
			}
		}
	}

	private char[] normalizeString(String string) {
		//new
		string = addWhitespace(string.toUpperCase(), this.rows);
		return string.toCharArray();
	}

	private static String addWhitespace(String string, int rows) {
		double whitespace = (rows - (string.length() % rows)) / 2; // whitespace on each end

		for (int i = 0; i < (int) Math.floor(whitespace); i++)
			string = " " + string;

		for (int i = 0; i < (int) Math.ceil(whitespace); i++)
			string += " ";

		return string;
	}

	public void print() {
		/** Display current playing board */

		System.out.println("\n\n");

		printClues();
		printHorizontalLine(this.columns);

		printBoxes();

		printHorizontalLine(this.columns);
		printColumnNumbers();
	}

	private void printClues() {
		for (int i = 0; i < this.clues[0].length; i++) {
			System.out.print("  ");
			for (int j = 0; j < this.clues.length; j++) {
				System.out.print(separator + separator);
				if (this.clues[j][i] == space)
					System.out.print(' ');
				else
					System.out.print(this.clues[j][i]);
			}
			System.out.println(separator);
		}
	}

	private static void printHorizontalLine(int length) {
		System.out.print("  ");
		for (int i = 0; i < length; i++)
			System.out.print("---");
		System.out.println();
	}

	private void printBoxes() {
		for (int r = 0; r < this.rows; r++) {
			System.out.print((char) (r + 'A') + separator); // row letter
			for (int c = 0; c < this.columns; c++) {
				System.out.print(separator + separator + this.currentBoard[r][c]);
			}
			System.out.println(separator);
			if (r != this.rows - 1)
				System.out.println(); // end of row
		}
	}

	private void printColumnNumbers() {
		System.out.print("  ");
		for (int i = 0; i < this.columns; i++) {
			System.out.print(separator);
			if (i < 10)
				System.out.print(' ');
			System.out.print(i); // display column numbers under board
		}
		System.out.println(separator);
	}

	public boolean input(char row, int colNum, char letter) {
		/**
		 * Submit a guess Returns whether or not guess was correct
		 */

		int rowNum = (int) Character.toUpperCase(row) - 'A'; // converts letter input into numbers starting with a = 0
		if (this.currentBoard[rowNum][colNum] != space) // as long as it isn't a a black space
			this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
		else
			System.out.println("Invalid Choice"); // if they try changing a black space
		return this.currentBoard[rowNum][colNum] == this.quoteArray[rowNum][colNum] ? true : false;
	}

	public boolean checkWin() {
		/** Returns whether or not player has won */

		int errors = 0;
		int emptySpaces = 0;
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.columns; c++) {
				if (this.currentBoard[r][c] != this.quoteArray[r][c]) {
					if (this.currentBoard[r][c] == emptySpace) {
						// if block has been left unanswered
						emptySpaces++;
					} else {
						// if one of the guesses is wrong
						this.currentBoard[r][c] = emptySpace; // removes incorrect guess
						errors++;
					}
				}
			}
		}
		System.out.println("You have " + errors + " errors and " + emptySpaces + " blank spaces.");
		return errors > 0 || emptySpaces > 0 ? false : true;
	}
}