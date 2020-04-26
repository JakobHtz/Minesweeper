package minesweeper;

/**
 * Mine element
 * @author Jakob Heitzmann
 */
public class GridElement {
	private int mineCount = 0;
	private TileStatus elementStatus = TileStatus.UNKNOWN;
	
	/**
	 * Initializes a mine element
	 */
	public GridElement() {
		super();
	}
	
	public void incMineCount() {
		this.mineCount += 1;
	}
	public int getMineCount() {
		return this.mineCount;
	}
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}
	public TileStatus isMineMarker() {
		return this.elementStatus;
	}
	public void setMineMarker(TileStatus elementStatus) {
		this.elementStatus = elementStatus;
	}
	public TileStatus getMineMarker() {
		return this.elementStatus;
	}
	public enum TileStatus {
		UNKNOWN,MARKED,KNOWN,GAME_OVER
	};
}
