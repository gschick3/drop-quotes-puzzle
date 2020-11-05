package puzzle;

public class BoardFormat {
	private static String separator = "|";
	private char space;

	private char[][] currentBoard;
	private char[][] clues;

	private int rows;
	private int columns;

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
		/** Display current playing board */

		StringBuffer result = new StringBuffer("\n\n");

		result.append(formatClues());
		result.append(formatHorizontalLine());

		result.append(formatBoxes());

		result.append(formatHorizontalLine());
		result.append(formatColumnNumbers());

		return result.toString();
	}

	private String formatClues() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < clues[0].length; i++) {
			result.append("  ");
			for (int j = 0; j < clues.length; j++) {
				result.append(separator + separator);
				if (this.clues[j][i] == space)
					result.append(' ');
				else
					result.append(clues[j][i]);
			}
			result.append(separator);
			result.append("\n");
		}
		return result.toString();
	}

	private String formatHorizontalLine() {
		StringBuffer result = new StringBuffer("  ");
		for (int i = 0; i < columns; i++)
			result.append("---");
		result.append("\n");

		return result.toString();
	}

	private String formatBoxes() {
		StringBuffer result = new StringBuffer();

		for (int r = 0; r < rows; r++) {
			result.append((char) (r + 'A') + separator); // row letter
			for (int c = 0; c < columns; c++) {
				result.append(separator + separator + currentBoard[r][c]);
			}
			result.append(separator);
			result.append("\n");
			if (r != rows - 1)
				result.append("\n"); // end of row
		}
		return result.toString();
	}

	private String formatColumnNumbers() {
		StringBuffer result = new StringBuffer("  ");
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
