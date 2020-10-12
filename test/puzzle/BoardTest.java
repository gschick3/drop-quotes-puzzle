package puzzle;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BoardTest {

	@Test
	void boardStartsWithNoErrors() {
		Board board = new Board("abcde", 2);
		assertEquals(0, board.findErrors().size());
	}

	@Test
	void boardWithSpacesAndNoErrors() {
		Board board = new Board("a b", 1);
		assertEquals(0, board.findErrors().size());
	}

	@Test
	void boardWithOneErrors() {
		Board board = new Board("abcde", 2);
		board.input('a', 0, 'd');
		assertEquals(1, board.findErrors().size());
	}

	@Test
	void boardWithMultipleErrors() {
		Board board = new Board("a b", 1);
		board.input('a', 0, 'd');
		board.input('a', 2, 'd');
		assertEquals(2, board.findErrors().size());
	}

	@Test
	void scoreboardKnowsErrorCoords() {
		Board board = new Board("a", 1);
		board.input('a', 0, 'd');
		assertEquals(1, board.score().getErrors().size());
	}
}
