package minesweeper;

import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import minesweeper.GridElement.TileStatus;

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
	private int minesMarked = 0;
	private boolean gameOver = false;
	
	/**
	 * Initializes minesweeper minefield
	 * @param xDim Size of mine field in x direction
	 * @param yDim Size of mine field in y direction
	 * @param startClick Field on which the player clicked
	 * @param mineCount Amount of mines in field
	 */
	public Minefield(int xDim, int yDim, int startClick, int mineCount) {
		this.X_DIM = xDim>3?xDim:3;
		this.Y_DIM = yDim>3?yDim:3;
		this.MINE_COUNT = mineCount>X_DIM*Y_DIM-9?X_DIM*Y_DIM-9:mineCount;
		this.minefield = getRandField(startClick);
		this.field = calcMineField();
	}
	
	/**
	 * Initializes empty minefield
	 * @param xDim Size of mine field in x direction
	 * @param yDim Size of mine field in y direction
	 */
	public Minefield(int xDim, int yDim) {
		this.X_DIM = xDim>3?xDim:3;
		this.Y_DIM = yDim>3?yDim:3;
		this.MINE_COUNT = 0;
		this.minefield = getRandField(0);
		this.field = calcMineField();
	}
	
	/**
	 * Returns the mine count of the minefield as String
	 * @return String filed attribute of instance
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		for (int i = 0; i < minefield.length; i++) {
			s.append(String.format("%2d", field[i].getMineCount()));
			if ((i+1)%X_DIM==0) {
				s.append("\n");
			}
		}
		return s.toString();
	}
	
	/**
	 * Returns a binary formated String of the location of all mines
	 * @return String
	 */
	public String justMinesToString() {
		StringBuilder s = new StringBuilder("");
		for (int i = 0; i < minefield.length; i++) {
			s.append(String.format("%10b", minefield[i]));
			if ((i+1)%X_DIM==0) {
				s.append("\n");
			}
		}
		return s.toString();
	}
	
	/**
	 * Calculates mine count of minefield
	 * @return Integer array minefield of this instance
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
	/**
	 * @param gridIndex
	 * @param buttonClicked - 0 - No button at all; 1 - Left Click; 2 - Right Click
	 */
	public void minefieldClicked(int gridIndexClicked, int buttonClicked) {
		if (buttonClicked == 1) {
			if (this.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.UNKNOWN) {
				if (this.getMineCountAtIndex(gridIndexClicked)==0) {
					zeroClicked(gridIndexClicked);
				} else if (this.getMineCountAtIndex(gridIndexClicked)<9) {
					this.setStatusAtIndex(gridIndexClicked, GridElement.TileStatus.KNOWN);
				} else {
					gameOver = true;
					this.setStatusAtIndex(gridIndexClicked, TileStatus.GAME_OVER);
					System.out.println("Game Over!");
				}
			} else if (this.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.KNOWN) {
				knownTileClicked(gridIndexClicked);
			}
		} else if (buttonClicked == 2) {
			if (!(this.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.KNOWN)) {
				if (this.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.MARKED) {
					this.setStatusAtIndex(gridIndexClicked, GridElement.TileStatus.UNKNOWN);
					minesMarked--;
				} else {
					this.setStatusAtIndex(gridIndexClicked, GridElement.TileStatus.MARKED);
					minesMarked++;
				}
			}
		}
	}
	private void knownTileClicked(int gridIndex) {
		if (gridIndex>=this.getxDim()*(this.getyDim()-1)) {
			if ((gridIndex+1)%this.getxDim()==0) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
				}
			} else {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()-1, 1);
					}
				}
			}
		} else if (gridIndex<this.getxDim()) {
			if ((gridIndex+1)%this.getxDim()==0) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()-1, 1);
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
				}
			} else {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()-1, 1);
					}
				}
			}
		} else {
			if ((gridIndex+1)%this.getxDim()==0) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()-1, 1);
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
				}
			} else {
				int markedCount = 0;
				if (this.getStatusAtIndex(gridIndex+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.MARKED) {
					markedCount++;
				}
				if (markedCount == this.getMineCountAtIndex(gridIndex)) {
					if (this.getStatusAtIndex(gridIndex+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()+1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim(), 1);
					}
					if (this.getStatusAtIndex(gridIndex-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex+this.getxDim()-1, 1);
					}
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1) == TileStatus.UNKNOWN) {
						this.minefieldClicked(gridIndex-this.getxDim()-1, 1);
					}
				}
			}
		}
	}
	/**
	 * @param gridIndex
	 */
	private void zeroClicked(int gridIndex) {
		this.setStatusAtIndex(gridIndex, GridElement.TileStatus.KNOWN);
		if (gridIndex>=this.getxDim()*(this.getyDim()-1)) {
			if ((gridIndex+1)%this.getxDim()==0) {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()-1);
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()+1);
					}
				}
			} else {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()+1);
					}
				}
			}
		} else if (gridIndex<this.getxDim()) {
			if ((gridIndex+1)%this.getxDim()==0) {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()-1);
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()+1);
					}
				}
			} else {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()+1);
					}
				}
			}
		} else {
			if ((gridIndex+1)%this.getxDim()==0) {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
			} else if ((gridIndex+1)%this.getxDim()==1) {
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()+1);
					}
				}
			} else {
				if (this.getMineCountAtIndex(gridIndex-1)!=0) {
					this.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+1)!=0) {
					this.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex-this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex-this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex-this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-this.getxDim()+1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()-1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()-1);
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim())!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim());
					}
				}
				if (this.getMineCountAtIndex(gridIndex+this.getxDim()+1)!=0) {
					this.setStatusAtIndex(gridIndex+this.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (this.getStatusAtIndex(gridIndex+this.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+this.getxDim()+1);
					}
				}
			}
		}
	}
	/**
	 * @param tileStatus
	 * @return Color of responding TilteStatus
	 */
	public Color getColor(GridElement.TileStatus tileStatus) {
		Color color;
		switch (tileStatus) {
		case UNKNOWN: {
			color = Color.GRAY;
			break;
		}
		case KNOWN: {
			color = Color.WHITE;
			break;
		}
		case MARKED: {
			color = Color.RED;
			break;
		}
		case GAME_OVER: {
			color = Color.BLUE;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		return color;
	}
	public int getMineCount() {
		return MINE_COUNT;
	}
	public boolean isGameOver() {
		return this.gameOver;
	}
	public void gameOver() {
		this.gameOver = true;
	}
	public int getMinesMarked() {
		return this.minesMarked;
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
