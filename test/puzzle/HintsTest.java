package puzzle;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class HintsTest {

	@Test
	void emptyHintsFindsNothing() {
		var hints = new Hints(new ArrayList<>());
		var actual = hints.find(Arrays.asList("x"));
		assertEquals(0, actual.size());
	}

	@Test
	void hintsFindsOneSingleLetterWord() {
		var hints = new Hints(Arrays.asList("I", "A"));
		var actual = hints.find(Arrays.asList("I"));
		assertEquals(Arrays.asList("I"), actual);
	}
	
	@Test
	void hintsFindsMultipleSingleLetterWords() {
		var hints = new Hints(Arrays.asList("I", "A"));
		var actual = hints.find(Arrays.asList("IA"));
		assertEquals(Arrays.asList("I", "A"), actual);
	}
	
	@Test
	void hintsFindsNothingWhenWordAndClueLengthMismatch() {
		var hints = new Hints(Arrays.asList("I", "A"));
		var actual = hints.find(Arrays.asList("IA", "T"));
		assertEquals(Arrays.asList(), actual);
	}
	
	@Test
	void hintsFindsMultiLetterMatches() {
		var hints = new Hints(Arrays.asList("I", "A", "AT"));
		var actual = hints.find(Arrays.asList("IA", "T"));
		assertEquals(Arrays.asList("AT"), actual);
	}
	
	@Test
	void hintsFindsAndSortsMultipleMultiLetterMatches() {
		var hints = new Hints(Arrays.asList("IMAGE", "LADEL", "DOG", "BREAD"));
		var actual = hints.find(Arrays.asList("BBTPL", "TRAL", "EAHJD", "ANE", "DEOL"));
		assertEquals(Arrays.asList("BREAD", "LADEL"), actual);
	}
}
