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
		var actual = board.findHints('A', 0, Arrays.asList("I", "M", "A", "G", "E"));
		assertEquals(Arrays.asList("IMAGE"), actual);
	}
}
