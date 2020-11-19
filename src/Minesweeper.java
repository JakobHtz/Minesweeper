import minesweeper.*;

public class Minesweeper {
	public static void main(String[] args) {
		Minefield minefield = new Minefield(16, 16);
		Matchfield m = new Matchfield(minefield);
	}
}
