package puzzle;
class Board {
	private static char emptySpace = '_';
	private static char space = '/';
	private static String separator = "|";

	private String quote;
	private int rows;
	int columns;

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
		string = addWhitespace(string.toUpperCase(), this.rows);
		return string.toCharArray();
	}

	private static String addWhitespace(String string, int rows) {
		double whitespace = (rows - (string.length() % rows)) / 2.0; // whitespace on each end
		
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < (int) Math.floor(whitespace); i++)
			result.append(" ");
		
		result.append(string);
		
		for (int i = 0; i < (int) Math.ceil(whitespace); i++)
			result.append(" ");
		
		return result.toString();
	}
	
	public String format() {
		/** Display current playing board */
		StringBuffer result = new StringBuffer("\n\n");
		
		result.append(this.formatClues());
		result.append(this.formatHorizontalLine());
		
		result.append(this.formatBoxes());
		
		result.append(this.formatHorizontalLine());
		result.append(this.formatColumnNumbers());
		
		return result.toString();
	}

	public String formatClues() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.clues[0].length; i++) {
			result.append("  ");
			for (int j = 0; j < this.clues.length; j++) {
				result.append(separator + separator);
				if (this.clues[j][i] == space)
					result.append(' ');
				else
					result.append(this.clues[j][i]);
			}
			result.append(separator);
			result.append("\n");
		}
		return result.toString();
	}

	public String formatHorizontalLine() {
		StringBuffer result = new StringBuffer("  ");
		for (int i = 0; i < this.columns; i++)
			result.append("---");
		result.append("\n");
		
		return result.toString();
	}

	public String formatBoxes() {
		StringBuffer result = new StringBuffer();
		for (int r = 0; r < this.rows; r++) {
			result.append((char) (r + 'A') + separator); // row letter
			for (int c = 0; c < this.columns; c++) {
				result.append(separator + separator + this.currentBoard[r][c]);
			}
			result.append(separator);
			result.append("\n");
			if (r != this.rows - 1)
				result.append("\n"); // end of row
		}
		return result.toString();
	}

	public String formatColumnNumbers() {
		StringBuffer result = new StringBuffer("  ");
		for (int i = 0; i < this.columns; i++) {
			result.append(separator);
			if (i < 10)
				result.append(' ');
			result.append(i); // display column numbers under board
		}
		result.append(separator);
		result.append("\n");
		return result.toString();
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
		System.out.println("You have " + errors + " error(s) and " + emptySpaces + " blank space(s).");
		return errors == 0 && emptySpaces == 0;
	}

}