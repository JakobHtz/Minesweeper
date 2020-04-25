package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Minesweeper game
 * @author Jakob Heitzmann
 */
public class Matchfield{
	private BufferStrategy buffer;
	private Minefield minefield;
	private int minesMarked = 0;
	private final int BOX_SIZE = 25;
	private final int INTERFACE_SIZE = 30;
	private final int BORDER_SIZE = 25;
	private final int GRID_SIZE = 25;
	
	/**
	 * Initializes a match field for the game
	 * @param minefield Mine field
	 */
	public Matchfield(Minefield mf) {
		this.minefield = mf;
		MouseListener mL = new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseClicked(MouseEvent e) {
				int gridIndexClicked = ((e.getX()-BORDER_SIZE)/GRID_SIZE)+((e.getY()-BOX_SIZE-INTERFACE_SIZE-BORDER_SIZE)/GRID_SIZE)*minefield.getxDim();
				if (minefield.getMineCount()==0) {
					minefield = new Minefield(minefield.getxDim(), minefield.getyDim(), gridIndexClicked, 100);
				}
				if (SwingUtilities.isLeftMouseButton(e)) {
					System.out.println("LeftClick at x: " + e.getX() + " ,y: " + e.getY() + " " + gridIndexClicked);
					minefieldClicked(gridIndexClicked);
				} else if (SwingUtilities.isRightMouseButton(e)) {
					System.out.println("RightClick at x: " + e.getX() + " ,y: " + e.getY() + " " + gridIndexClicked);
					if (!(minefield.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.KNOWN)) {
						if (minefield.getStatusAtIndex(gridIndexClicked)==GridElement.TileStatus.MARKED) {
							minefield.setStatusAtIndex(gridIndexClicked, GridElement.TileStatus.UNKNOWN);
							minesMarked--;
						} else {
							minefield.setStatusAtIndex(gridIndexClicked, GridElement.TileStatus.MARKED);
							minesMarked++;
						}
					}
				}
			}
		};
		JFrame frame = new JFrame("Minesweeper");
		initFrame(frame);
		frame.addMouseListener(mL);
		buffer = frame.getBufferStrategy();
		draw(frame);
	}
	/**
	 * @param frame
	 */
	private void initFrame(JFrame frame) {
		frame.setSize(this.GRID_SIZE*this.minefield.getxDim()+2*this.BORDER_SIZE, this.GRID_SIZE*this.minefield.getyDim()+BOX_SIZE+this.INTERFACE_SIZE+2*this.BORDER_SIZE);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setResizable(true);
		frame.setUndecorated(false);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.DARK_GRAY);
		frame.setVisible(true);
		frame.createBufferStrategy(3);
	}
	/*
	 * Draws the graphics
	 */
	private void draw(JFrame frame) {
		for (; true;) {
			Graphics g = buffer.getDrawGraphics();
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
			drawUI(g);
			drawGrid(g);
			g.dispose();
			buffer.show();
		}
	}
	/**
	 * @param g
	 */
	private void drawUI(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(BORDER_SIZE, BOX_SIZE+BORDER_SIZE, minefield.getxDim()*GRID_SIZE, INTERFACE_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(BORDER_SIZE, BOX_SIZE+BORDER_SIZE, minefield.getxDim()*GRID_SIZE, INTERFACE_SIZE);
		g.setColor(Color.BLACK);
		g.drawString("" + (minefield.getMineCount()-this.minesMarked), BORDER_SIZE+10, GRID_SIZE+BORDER_SIZE+20);
	}
	/**
	 * Draws the grid
	 * @param g Graphics
	 */
	private void drawGrid(Graphics g) {
		for (int i = 0; i < minefield.getyDim(); i++) {
			for (int j = 0; j < minefield.getxDim(); j++) {
				GridElement.TileStatus color = this.minefield.getStatusAtIndex(i*this.minefield.getxDim()+j);
				g.setColor(getColor(color));
				g.fillRect(this.BORDER_SIZE+j*this.GRID_SIZE, this.BORDER_SIZE+this.BOX_SIZE+this.INTERFACE_SIZE+i*this.GRID_SIZE, this.GRID_SIZE, this.GRID_SIZE);
				if (this.minefield.getStatusAtIndex(i*this.minefield.getxDim()+j)==GridElement.TileStatus.KNOWN) {
					g.setColor(Color.BLACK);
					g.drawString(Integer.toString(this.minefield.getMineCountAtIndex(i*this.minefield.getxDim()+j)),this.BORDER_SIZE+j*this.GRID_SIZE+this.GRID_SIZE/3, this.BORDER_SIZE+this.BOX_SIZE+this.INTERFACE_SIZE+i*this.GRID_SIZE+this.GRID_SIZE/3+10);
				}
				g.setColor(Color.BLACK);
				g.drawRect(this.BORDER_SIZE+j*this.GRID_SIZE, this.BORDER_SIZE+this.BOX_SIZE+this.INTERFACE_SIZE+i*this.GRID_SIZE, this.GRID_SIZE, this.GRID_SIZE);
			}
		}
	}
	/**
	 * @param gridIndex
	 */
	private void minefieldClicked(int gridIndex) {
		if (this.minefield.getMineCountAtIndex(gridIndex)==0) {
			zeroClicked(gridIndex);
		} else if (this.minefield.getMineCountAtIndex(gridIndex)<9) {
			this.minefield.setStatusAtIndex(gridIndex, GridElement.TileStatus.KNOWN);
		} else {
			System.out.println("Game Over!");
		}
	}
	/**
	 * @param gridIndex
	 */
	private void zeroClicked(int gridIndex) {
		this.minefield.setStatusAtIndex(gridIndex, GridElement.TileStatus.KNOWN);
		if (gridIndex>=minefield.getxDim()*(minefield.getyDim()-1)) {
			if ((gridIndex+1)%minefield.getxDim()==0) {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()-1);
					}
				}
			} else if ((gridIndex+1)%minefield.getxDim()==1) {
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()+1);
					}
				}
			} else {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()+1);
					}
				}
			}
		} else if (gridIndex<minefield.getxDim()) {
			if ((gridIndex+1)%minefield.getxDim()==0) {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()-1);
					}
				}
			} else if ((gridIndex+1)%minefield.getxDim()==1) {
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()+1);
					}
				}
			} else {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()+1);
					}
				}
			}
		} else {
			if ((gridIndex+1)%minefield.getxDim()==0) {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
			} else if ((gridIndex+1)%minefield.getxDim()==1) {
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()+1);
					}
				}
			} else {
				if (minefield.getMineCountAtIndex(gridIndex-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex-minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex-minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex-minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex-minefield.getxDim()+1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()-1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()-1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()-1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()-1);
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim())!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim(), GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim())!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim());
					}
				}
				if (minefield.getMineCountAtIndex(gridIndex+minefield.getxDim()+1)!=0) {
					this.minefield.setStatusAtIndex(gridIndex+minefield.getxDim()+1, GridElement.TileStatus.KNOWN);
				} else {
					if (minefield.getStatusAtIndex(gridIndex+minefield.getxDim()+1)!=GridElement.TileStatus.KNOWN) {
						zeroClicked(gridIndex+minefield.getxDim()+1);
					}
				}
			}
		}
	}
	/**
	 * @param
	 * @return
	 */
	private Color getColor(GridElement.TileStatus color) {
		Color elementColor;
		switch (color) {
		case UNKNOWN: {
			elementColor = Color.GRAY;
			break;
		}
		case KNOWN: {
			elementColor = Color.WHITE;
			break;
		}
		case MARKED: {
			elementColor = Color.RED;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}
		return elementColor;
	}
}
