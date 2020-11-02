package puzzle;

import java.util.*;

class Board {
	private static char emptySpace = '_';
	public char space = '/';

	private String quote;
	private int rows;
	int columns;

	private char[][] quoteArray;
	private char[][] clues;
	private char[][] currentBoard;

	private Scoreboard scoreboard;
	private List<Coord> coords = new ArrayList<>();

	public Board(String quote, int rows) {
		this.quote = quote;
		this.rows = rows;
		scoreboard = new Scoreboard();
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

		for (int r = 0; r < this.rows; r++)
			for (int c = 0; c < this.columns; c++)
				this.coords.add(new Coord(r, c));

		for (Coord coord : coords) {
			if (this.quoteArray[coord.x][coord.y] == ' ') {
				this.quoteArray[coord.x][coord.y] = space;
				this.currentBoard[coord.x][coord.y] = space;
			} else {
				this.currentBoard[coord.x][coord.y] = emptySpace;
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
		double whitespace = whitespaceOnEachEnd(string, rows);

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < (int) Math.floor(whitespace); i++)
			result.append(" ");

		result.append(string);

		for (int i = 0; i < (int) Math.ceil(whitespace); i++)
			result.append(" ");

		return result.toString();
	}

	private static double whitespaceOnEachEnd(String string, int rows) {
		int overhang = string.length() % rows;
		return overhang == 0 ? 0 : (rows - overhang) / 2.0;
	}

	public boolean input(char row, int colNum, char letter) {
		/**
		 * Submit a guess Returns whether or not guess was correct
		 */

		int rowNum = Character.toUpperCase(row) - 'A'; // converts letter input into numbers starting with a = 0
		if (this.currentBoard[rowNum][colNum] != space) { // as long as it isn't a a black space
			this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
			return true;
		}
		return false;
	}

	public List<Coord> findErrors() {
		List<Coord> errorCoords = new ArrayList<>();

		for (Coord coord : this.coords) {
			if (this.currentBoard[coord.x][coord.y] != this.quoteArray[coord.x][coord.y]
					&& this.currentBoard[coord.x][coord.y] != emptySpace)
				errorCoords.add(coord);
		}

		return errorCoords;
	}

	public boolean hasEmptySpaces() {
		for (Coord coord : this.coords)
			if (this.currentBoard[coord.x][coord.y] != this.quoteArray[coord.x][coord.y]
					&& this.currentBoard[coord.x][coord.y] == emptySpace)
				return true;
		return false;
	}

	public Scoreboard getScore() {
		scoreboard.setErrors(findErrors());
		scoreboard.setHasEmpties(hasEmptySpaces());
		return scoreboard;
	}

	public void eraseErrors() {
		for (Coord coord : scoreboard.getErrors())
			if (currentBoard[coord.x][coord.y] != quoteArray[coord.x][coord.y])
				erase(coord);
	}

	private void erase(Coord coord) {
		this.currentBoard[coord.x][coord.y] = emptySpace;
	}
}