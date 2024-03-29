package puzzle;

import java.util.*;
import java.util.stream.Collectors;

class Board {
	public static final char emptySpace = '_';
	public static final char space = '/';

	private int rows;
	private int columns;

	private char[][] quoteArray;
	private char[][] currentBoard;
	private char[][] clues;

	private final Scoreboard scoreboard;
	private final List<Coord> allCoords = new ArrayList<>();

	public Board() {
		this.rows = 0;
		this.columns = 0;
		scoreboard = new Scoreboard();
	}

	public int getRows() {
		return rows;
	}
	public int getCols() {
		return columns;
	}
	public char[][] getCurrentBoard() {
		return currentBoard;
	}
	public char[][] getClues() {
		return clues;
	}
	public Scoreboard getScore() {
		return scoreboard;
	}

	public void loadQuote(String quote, int rows) {
		this.rows = rows > 0 ? rows : 1;

		char[] normalizedQuote = this.normalizeString(quote);
		this.quoteArray = Array.dimensionalize(normalizedQuote, this.rows); // fill complete 2d array with uppercase
																			// letters
		this.clues = Array.sortArray(Array.transposeArray(quoteArray)); // randomized array with letters from
																		// each column
		this.columns = this.quoteArray[0].length; // number of characters in each row of 2d array

		this.currentBoard = new char[this.rows][this.columns]; // create empty board for player

		for (int r = 0; r < this.rows; r++)
			for (int c = 0; c < this.columns; c++)
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
		int totalWhitespace = (rows - (string.length() % rows)) % rows; // I have no idea how this works

		return " ".repeat(Math.max(0, (int) Math.floor(totalWhitespace / 2.0))) +
				string +
				" ".repeat(Math.max(0, (int) Math.ceil(totalWhitespace / 2.0)));
	}


	public void setGuess(char row, int colNum, char letter) {
		int rowNum = charToNum(row); // converts letter input into numbers starting with a = 0

		if (this.currentBoard[rowNum][colNum] == space) return; // as long as it isn't a black space

		this.currentBoard[rowNum][colNum] = Character.toUpperCase(letter);
		scoreboard.setErrors(findErrors());
		scoreboard.setHasEmpties(hasEmptySpaces());
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

}