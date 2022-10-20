package puzzle;

import java.util.*;
import java.util.stream.Collectors;

class Board {
	private static final char emptySpace = '_';
	public final char space = '/';

	private final String quote;
	private final int rows;

	private char[][] quoteArray;
	private char[][] currentBoard;
	private char[][] clues;

	private final Scoreboard scoreboard;
	private final List<Coord> allCoords = new ArrayList<>();

	public Board(String quote) {
		this.quote = quote;
		this.rows = (int)Math.ceil(quote.length() / 15.0); // just somewhere around 15 spaces long
		scoreboard = new Scoreboard();
		fillBoard();
	}
	public Board(String quote, int rows) {
		this.quote = quote;
		this.rows = rows;
		scoreboard = new Scoreboard();
		fillBoard();
	}

	private void fillBoard() {
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
			if (this.quoteArray[coord.x()][coord.y()] == ' ') {
				this.quoteArray[coord.x()][coord.y()] = space;
				this.currentBoard[coord.x()][coord.y()] = space;
			} else {
				this.currentBoard[coord.x()][coord.y()] = emptySpace;
			}
		}
	}

	private char[] normalizeString(String string) {
		string = string.replaceAll("[.]", " ").replaceAll("[,']", "");
		string = addWhitespace(string.toUpperCase(), this.rows);
		return string.toCharArray();
	}

	private static String addWhitespace(String string, int rows) {
		double whitespace = whitespaceOnEachEnd(string, rows);

		return " ".repeat(Math.max(0, (int) Math.floor(whitespace))) +
				string +
				" ".repeat(Math.max(0, (int) Math.ceil(whitespace)));
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
		int rowNum = charToNum(row); // converts letter input into numbers starting with a = 0
		if (this.currentBoard[rowNum][colNum] != space) { // as long as it isn't a black space
			this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
			scoreboard.setErrors(findErrors());
			scoreboard.setHasEmpties(hasEmptySpaces());
			return true;
		}
		return false;
	}
	
	private int charToNum(char c) {
		return Character.toUpperCase(c) - 'A';
	}
	
	public List<String> findHints(char row, int colNum, Hints hintFinder) {
		List<String> clueList = new ArrayList<>();
		for (int col: findWordRange(row, colNum)) {
			clueList.add(String.valueOf(clues[col]));
		}
		return hintFinder.find(clueList);
	}
	
	private List<Integer> findWordRange(char row, int colNum) {
		int rowNum = charToNum(row);
		int colStart = colNum;
		List<Integer> colNums = new ArrayList<>();
		for (int r = rowNum; r < currentBoard.length; r++) {
			for (int c = colStart; c < currentBoard[0].length; c++) {
				if (currentBoard[r][c] == space)
					return colNums;
				colNums.add(c);
			}
			colStart = 0;
		}
		return colNums;
	}

	public void eraseErrors() {
		scoreboard.getErrors()
		.stream()
		.filter(this::mismatchesAt)
		.forEach(coord -> currentBoard[coord.x()][coord.y()] = emptySpace);

		scoreboard.setErrors(new ArrayList<>());
		scoreboard.setHasEmpties(hasEmptySpaces());
	}

	private List<Coord> findErrors() {
		return allCoords
				.stream()
				.filter(coord -> mismatchesAt(coord) && !isEmptyAt(coord))
				.collect(Collectors.toList());
	}

	private boolean hasEmptySpaces() {
		return allCoords
				.stream()
				.anyMatch(this::isEmptyAt);
	}

	private boolean mismatchesAt(Coord coord) {
		return currentBoard[coord.x()][coord.y()] != quoteArray[coord.x()][coord.y()];
	}

	private boolean isEmptyAt(Coord coord) {
		return currentBoard[coord.x()][coord.y()] == emptySpace;
	}

	public Scoreboard getScore() {
		return scoreboard;
	}
}