package puzzle;

class Board {
	private static char emptySpace = '_';
	public char space = '/';

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

	public char[][] getCurrentBoard() {
		return currentBoard;
	}

	public char[][] getClues() {
		return clues;
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

	public boolean input(char row, int colNum, char letter) {
		/**
		 * Submit a guess Returns whether or not guess was correct
		 */

		int rowNum = Character.toUpperCase(row) - 'A'; // converts letter input into numbers starting with a = 0
		if (this.currentBoard[rowNum][colNum] != space) // as long as it isn't a a black space
			this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
		else
			System.out.println("Invalid Choice"); // if they try changing a black space
		return this.currentBoard[rowNum][colNum] == this.quoteArray[rowNum][colNum] ? true : false;
	}

	public int[] countErrors() {
		// separate into counting and fixing errors
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
						erase(r, c); // removes incorrect guess
						errors++;
					}
				}
			}
		}
		int[] results = { errors, emptySpaces };
		return results;
	}

	private void erase(int row, int column) {
		this.currentBoard[row][column] = emptySpace;
	}
}