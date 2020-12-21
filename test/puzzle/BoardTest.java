package puzzle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	void boardStartsWithNoErrors() {
		Board board = new Board("abcde", 2);
		assertEquals(0, board.getScore().getErrors().size());

	}

	@Test
	void boardWithSpacesAndNoErrors() {
		Board board = new Board("a b", 1);
		assertEquals(0, board.getScore().getErrors().size());
	}

	@Test
	void boardWithOneErrors() {
		Board board = new Board("abcde", 2);
		board.setGuess('a', 0, 'd');
		assertEquals(1, board.getScore().getErrors().size());
	}

	@Test
	void boardWithMultipleErrors() {
		Board board = new Board("a b", 1);
		board.setGuess('a', 0, 'd');
		board.setGuess('a', 2, 'd');
		assertEquals(2, board.getScore().getErrors().size());
	}

	@Test
	void scoreboardKnowsErrorCoords() {
		Board board = new Board("a", 1);
		board.setGuess('a', 0, 'd');
		assertEquals(1, board.getScore().getErrors().size());
	}

	@Test
	void stringWithNoWhitespace() {
		Board board = new Board("test", 2);
		assertEquals(2, board.getCurrentBoard()[0].length);
	}

	@Test
	void stringWithOneWhitespace() {
		Board board = new Board("testing", 3);
		assertEquals(3, board.getCurrentBoard()[0].length);
	}

	@Test
	void boardFindsCluesForOneLine() {
		Board board = new Board("image", 1);
		var actual = board.findHints('A', 0, new Hints(Arrays.asList("IMAGE", "BOOK")));
		assertEquals(Arrays.asList("IMAGE"), actual);
	}

	@Test
	void boardFindsCluesForTwoLines() {
		Board board = new Board("image", 2);
		var actual = board.findHints('A', 0, new Hints(Arrays.asList("BOOK", "IMAGE")));
		assertEquals(Arrays.asList("IMAGE"), actual);
	}

	@Test
	void boardFindsCluesForFirstWord() {
		Board board = new Board("book image", 2);
		var actual = board.findHints('A', 0, new Hints(Arrays.asList("IMAGE", "BOOK")));
		assertEquals(Arrays.asList("BOOK"), actual);
	}

	@Test
	void boardFindsCluesForSecondWord() {
		Board board = new Board("image book", 2);
		var actual = board.findHints('B', 1, new Hints(Arrays.asList("IMAGE", "BOOK")));
		assertEquals(Arrays.asList("BOOK"), actual);
	}
	
	@Test
	void boardFindsCluesForMultiplePossibilities() {
		Board board = new Board("save", 2);
		var actual = board.findHints('A', 0, new Hints(Arrays.asList("VASE", "AGES", "SAVE")));
		assertEquals(Arrays.asList("SAVE", "VASE"), actual);
	}
}
