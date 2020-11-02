package puzzle;

import java.util.*;
import java.util.stream.Collectors;

class Board {
	private static char emptySpace = '_';
	public char space = '/';

	private String quote;
	private int rows;

	private char[][] quoteArray;
	private char[][] currentBoard;
	private char[][] clues;

	private Scoreboard scoreboard;
	private List<Coord> allCoords = new ArrayList<>();

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
		var columns = this.quoteArray[0].length; // number of characters in each row of 2d array

		this.currentBoard = new char[this.rows][columns]; // create empty board for player

		for (int r = 0; r < this.rows; r++)
			for (int c = 0; c < columns; c++)
				this.allCoords.add(new Coord(r, c));

		for (Coord coord : allCoords) {
			if (this.quoteArray[coord.x][coord.y] == ' ') {
				this.quoteArray[coord.x][coord.y] = space;
				this.currentBoard[coord.x][coord.y] = space;
			} else {
				this.currentBoard[coord.x][coord.y] = emptySpace;
			}
		}
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

	public char[][] getCurrentBoard() {
		return currentBoard;
	}

	public char[][] getClues() {
		return clues;
	}

	public boolean setGuess(char row, int colNum, char letter) {
		int rowNum = Character.toUpperCase(row) - 'A'; // converts letter input into numbers starting with a = 0
		if (this.currentBoard[rowNum][colNum] != space) { // as long as it isn't a a black space
			this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
			scoreboard.setErrors(findErrors());
			scoreboard.setHasEmpties(hasEmptySpaces());
			return true;
		}
		return false;
	}

	public void eraseErrors() {
		scoreboard.getErrors()
		.stream()
		.filter(coord -> mismatchesAt(coord))	
		.forEach(coord -> currentBoard[coord.x][coord.y] = emptySpace);

		scoreboard.setErrors(new ArrayList<Coord>());
		scoreboard.setHasEmpties(hasEmptySpaces());
	}

	public List<Coord> findErrors() {
		return allCoords
				.stream()
				.filter(coord -> mismatchesAt(coord) && !isEmptyAt(coord))
				.collect(Collectors.toList());
	}

	public boolean hasEmptySpaces() {
		return allCoords
				.stream()
				.anyMatch(coord -> isEmptyAt(coord));
	}

	private boolean mismatchesAt(Coord coord) {
		return currentBoard[coord.x][coord.y] != quoteArray[coord.x][coord.y];
	}

	private boolean isEmptyAt(Coord coord) {
		return currentBoard[coord.x][coord.y] == emptySpace;
	}

	public Scoreboard getScore() {
		return scoreboard;
	}
}