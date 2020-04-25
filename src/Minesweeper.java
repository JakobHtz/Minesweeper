import minesweeper.*;

public class Minesweeper {
	public static void main(String[] args) {
		Minefield minefield = new Minefield(32, 32);
		Matchfield m = new Matchfield(minefield);
	}
}
