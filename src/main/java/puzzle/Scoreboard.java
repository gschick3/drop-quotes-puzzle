package puzzle;

import java.util.ArrayList;
import java.util.List;

class Scoreboard {
	private boolean hasEmpties = true;

	private List<Coord> coords = new ArrayList<>();

	public boolean hasWon() {
		return this.coords.size() == 0 && !hasEmpties;
	}

	public List<Coord> getErrors() {
		return coords;
	}

	public void setErrors(List<Coord> coords) {
		this.coords = coords;
	}

	public void setHasEmpties(boolean hasEmpties) {
		this.hasEmpties = hasEmpties;
	}
}
