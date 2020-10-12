package puzzle;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
	public int errors;
	public boolean hasEmpties = true;

	public List<Coord> coords = new ArrayList<>();

	public boolean checkWin() {
		return this.errors == 0 && !hasEmpties;
	}

	public List<Coord> getErrors() {
		return coords;
	}

	public void setErrors(List<Coord> coords) {
		this.coords = coords;
		errors = coords.size();
	}

	public void setHasEmpties(boolean hasEmpties) {
		this.hasEmpties = hasEmpties;
	}
}
