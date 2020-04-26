import minesweeper.*;

public class Minesweeper {
	public static void main(String[] args) {
		Minefield minefield = new Minefield(8, 8);
		Matchfield m = new Matchfield(minefield);
	}
}
