package minesweeper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Minesweeper mine field
 * @author Jakob Heitzmann
 */
public class Minefield {
	private final boolean[] minefield;
	private final GridElement[] field;
	private final int MINE_COUNT;
	private final int X_DIM;
	private final int Y_DIM;
	
	/**
	 * Initializes minesweeper mine field
	 * @param xDim Size of mine field in x direction
	 * @param yDim Size of mine field in y direction
	 * @param startClick Field on which the player clicked
	 * @param mineCount Amount of mines in field
	 */
	public Minefield(int xDim, int yDim, int startClick, int mineCount) {
		this.X_DIM = xDim>3?xDim:3;
		this.Y_DIM = yDim>3?yDim:3;
		this.MINE_COUNT = mineCount;
		this.minefield = getRandField(startClick);
		this.field = calcMineField();
	}
	
	public Minefield(int xDim, int yDim) {
		this.X_DIM = xDim>3?xDim:3;
		this.Y_DIM = yDim>3?yDim:3;
		this.MINE_COUNT = 0;
		this.minefield = getRandField(0);
		this.field = calcMineField();
	}
	
	/**
	 * Returns the mine count of the mine field as String
	 * @return String filed attribute of instance
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		/* Displays the mines only
		for (int i = 0; i < mineField.length; i++) {
			s.append(String.format("%10b", mineField[i]));
			if ((i+1)%yDim==0) {
				s.append("\n");
			}
		}*/
		for (int i = 0; i < minefield.length; i++) {
			s.append(String.format("%2d", field[i].getMineCount()));
			if ((i+1)%Y_DIM==0) {
				s.append("\n");
			}
		}
		return s.toString();
	}
	
	/**
	 * Calculates mine count of mine field
	 * @return Integer array mine field of this instance
	 */
	private GridElement[] calcMineField() {
		GridElement[] field = new GridElement[this.minefield.length];
		for (int i = 0; i < field.length; i++) {
			field[i] = new GridElement();
			if (this.minefield[i]) {
				field[i].setMineCount(9);
				continue;
			}
			if (i>=this.X_DIM*(this.Y_DIM-1)) {
				if ((i+1)%this.X_DIM==0) {
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					continue;
				} else if ((i+1)%this.X_DIM==1) {
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					continue;
				} else {
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					continue;
				}
			} else if (i<X_DIM) {
				if ((i+1)%this.X_DIM==0) {
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					continue;
				} else if ((i+1)%this.X_DIM==1) {
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					continue;
				} else {
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					continue;
				}
			} else {
				if ((i+1)%this.X_DIM==0) {
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM-1]) {
						field[i].incMineCount();
					}
					continue;
				} else if ((i+1)%this.X_DIM==1) {
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM+1]) {
						field[i].incMineCount();
					}
					continue;
				} else {
					if (this.minefield[i-this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM+1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-this.X_DIM-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i-1]) {
						field[i].incMineCount();
					}
					if (this.minefield[i+this.X_DIM-1]) {
						field[i].incMineCount();
					}
					continue;
				}
			}
		}
		return field;
	}
	
	/**
	 * Creates random mine field
	 * @param startClick Initial click by the user
	 * @return Boolean array with randomized mine placement of instance
	 */
	private boolean[] getRandField(int startClick) {
		boolean[] field = new boolean[this.X_DIM*this.Y_DIM];
		for (int i = 0; i < field.length; i++) {
			field[i] = (i>=this.MINE_COUNT)?(false):(true);
		}
		
		Minefield.shuffleMinefield(field);
		
		if (startClick/this.X_DIM == 0) {
			if (startClick == 0) {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM-2?4:2;
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM-2?4:2;
					field[startClick+1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM-2?4:2;
					field[startClick+this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM+1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM-2?4:2;
					field[startClick+this.X_DIM+1] = field[temp];
					field[temp] = true;
				}
			} else if (startClick == this.X_DIM-1) {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM*2-4?4:temp>=this.X_DIM-2?2:0;
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM*2-4?4:temp>=this.X_DIM-2?2:0;
					field[startClick-1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM*2-4?4:temp>=this.X_DIM-2?2:0;
					field[startClick+this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM-1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += temp>=this.X_DIM*2-4?4:temp>=this.X_DIM-2?2:0;
					field[startClick+this.X_DIM-1] = field[temp];
					field[temp] = true;
				}
			} else {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick+1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick+this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM+1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick+this.X_DIM+1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick-1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+this.X_DIM-1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-1?3:0;
					field[startClick+this.X_DIM-1] = field[temp];
					field[temp] = true;
				}
			}
		} else if (startClick/this.X_DIM == this.Y_DIM-1) {
			if (startClick == field.length-1) {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(2):(0);
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(2):(0);
					field[startClick-1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(2):(0);
					field[startClick-this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM-1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(2):(0);
					field[startClick-this.X_DIM-1] = field[temp];
					field[temp] = true;
				}
			} else if (startClick == field.length-this.X_DIM) {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(4):((temp>=this.X_DIM*(this.Y_DIM-2))?(2):(0));
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(4):((temp>=this.X_DIM*(this.Y_DIM-2))?(2):(0));
					field[startClick+1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(4):((temp>=this.X_DIM*(this.Y_DIM-2))?(2):(0));
					field[startClick-this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM+1]) {
					int temp = (int)(Math.random()*(field.length-4));
					temp += (temp>=this.X_DIM*(this.Y_DIM-1)-2)?(4):((temp>=this.X_DIM*(this.Y_DIM-2))?(2):(0));
					field[startClick-this.X_DIM+1] = field[temp];
					field[temp] = true;
				}
			} else {
				while (field[startClick]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick-1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick-this.X_DIM] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM-1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick-this.X_DIM-1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick+1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick+1] = field[temp];
					field[temp] = true;
				}
				while (field[startClick-this.X_DIM+1]) {
					int temp = (int)(Math.random()*(field.length-6));
					temp += temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
					field[startClick-this.X_DIM+1] = field[temp];
					field[temp] = true;
				}
			}
		} else if (startClick%this.X_DIM == 0) {
			while (field[startClick]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick+this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM+1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick+this.X_DIM+1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick-this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick+1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM+1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-4?6:temp>=startClick-2?4:temp>=startClick-this.X_DIM?2:0;
				field[startClick-this.X_DIM+1] = field[temp];
				field[temp] = true;
			}
		} else if ((startClick+1)%this.X_DIM == 0) {
			while (field[startClick]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick-1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick+this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM-1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick+this.X_DIM-1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick-this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM-1]) {
				int temp = (int)(Math.random()*(field.length-6));
				temp += temp>=startClick+this.X_DIM-5?6:temp>=startClick-3?4:temp>=startClick-this.X_DIM-1?2:0;
				field[startClick-this.X_DIM-1] = field[temp];
				field[temp] = true;
			}
		} else {
			while (field[startClick]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick+this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM+1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick+this.X_DIM+1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick-this.X_DIM] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick+1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM+1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick-this.X_DIM+1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick-1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick+this.X_DIM-1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick+this.X_DIM-1] = field[temp];
				field[temp] = true;
			}
			while (field[startClick-this.X_DIM-1]) {
				int temp = (int)(Math.random()*(field.length-9));
				temp += temp>=startClick+this.X_DIM-7?9:temp>=startClick-4?6:temp>=startClick-this.X_DIM-1?3:0;
				field[startClick-this.X_DIM-1] = field[temp];
				field[temp] = true;
			}
		}
		return field;
	}
	private static void shuffleMinefield(boolean[] minefield) {
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = minefield.length - 1; i > 0; i--) {
	      int index = rnd.nextInt(i + 1);
	      if (index!=i) {
	    	  boolean a = minefield[index];
		      minefield[index] = minefield[i];
		      minefield[i] = a;  
	      }
	    }
	  }
	public int getMineCount() {
		return MINE_COUNT;
	}
	public int getxDim() {
		return X_DIM;
	}
	public int getyDim() {
		return Y_DIM;
	}
	public GridElement.TileStatus getStatusAtIndex(int index) {
		return this.field[index].getMineMarker();
	}
	public void setStatusAtIndex(int index, GridElement.TileStatus statusColor) {
		this.field[index].setMineMarker(statusColor);;
	}
	public int getMineCountAtIndex(int gridIndex) {
		return field[gridIndex].getMineCount();
	}
}
