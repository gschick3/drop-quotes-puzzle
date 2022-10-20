package puzzle;

class BoardFormat {
	private static final String separator = "|";
	private final char space;

	private char[][] currentBoard;
	private char[][] clues;

	private final int rows;
	private final int columns;

	public BoardFormat(Board board) {
		this.space = board.space;
		this.currentBoard = board.getCurrentBoard();
		this.rows = currentBoard.length;
		this.columns = currentBoard[0].length;

		this.clues = board.getClues();
	}

	public void updateBoard(Board board) {
		currentBoard = board.getCurrentBoard();
		clues = board.getClues();
	}

	public String format() {
		/* Display current playing board */

		return "\n\n" + formatClues() +
				formatHorizontalLine() +
				formatBoxes() +
				formatHorizontalLine() +
				formatColumnNumbers();
	}

	private String formatClues() {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < clues[0].length; i++) {
			result.append("  ");
			for (char[] clue : clues) {
				result.append(separator + separator);
				if (clue[i] == space)
					result.append(' ');
				else
					result.append(clue[i]);
			}
			result.append(separator);
			result.append("\n");
		}
		return result.toString();
	}

	private String formatHorizontalLine() {

		return "  " + "---".repeat(Math.max(0, columns)) +
				"\n";
	}

	private String formatBoxes() {
		StringBuilder result = new StringBuilder();

		for (int r = 0; r < rows; r++) {
			result.append((char) (r + 'A')).append(separator); // row letter
			for (int c = 0; c < columns; c++) {
				result.append(separator + separator).append(currentBoard[r][c]);
			}
			result.append(separator);
			result.append("\n");
			if (r != rows - 1)
				result.append("\n"); // end of row
		}
		return result.toString();
	}

	private String formatColumnNumbers() {
		StringBuilder result = new StringBuilder("  ");
		for (int i = 0; i < columns; i++) {
			result.append(separator);
			if (i < 10)
				result.append(' ');
			result.append(i); // display column numbers under board
		}
		result.append(separator);
		result.append("\n");
		return result.toString();
	}
}
