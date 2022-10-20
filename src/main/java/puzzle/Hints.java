package puzzle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Hints {
	private final List<String> dictionary;
	
	public Hints(List<String> dictionary) {
		this.dictionary = dictionary;
	}
	public Hints(String fileName) throws IOException {
		this.dictionary = (Files
				.readAllLines(Paths.get(fileName))
				.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList())
				);
	}
	
	public List<String> find(List<String> clues) {
		var result = new ArrayList<String>();
		for (String word: dictionary) {
			if (word.length() != clues.size())
				continue;
			boolean matches = true;
			for (int letterIndex = 0; letterIndex < word.length(); letterIndex++)
				if (!clues.get(letterIndex).contains(word.substring(letterIndex, letterIndex + 1))) {
					matches = false;
					break;
				}
					
			if (matches)
				result.add(word);
		}
		Collections.sort(result);
		return result;
	}
}
