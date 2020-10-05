package puzzle;

public class Scoreboard {
	public int errors;
	public int empties;

	public void update(int errors, int empties) {
		this.errors = errors;
		this.empties = empties;
	}

	public boolean checkWin() {
		return this.errors + this.empties == 0;
	}
}
