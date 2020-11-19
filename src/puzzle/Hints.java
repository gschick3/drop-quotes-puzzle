package puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hints {
	private List<String> dictionary = new ArrayList<>();
	
	public Hints(List<String> dictionary) {
		this.dictionary = dictionary;
	}
	
	public List<String> find(List<String> clues) {
		var result = new ArrayList<String>();
		for (String word: dictionary) {
			if (word.length() != clues.size())
				continue;
			boolean matches = true;
			for (int letterIndex = 0; letterIndex < word.length(); letterIndex++)
				if (!clues.get(letterIndex).contains(word.substring(letterIndex, letterIndex+1)))
					matches = false;
					
			if (matches)
				result.add(word);
		}
		Collections.sort(result);
		return result;
	}

}
