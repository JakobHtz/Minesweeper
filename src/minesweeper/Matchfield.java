package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/**
 * Minesweeper game
 * @author Jakob Heitzmann
 */
public class Matchfield{
	private BufferStrategy buffer;
	private Minefield minefield;
	private int FONT_HEIGHT = 10;
	private final int BOX_SIZE = 25;
	private final int INTERFACE_SIZE = 30;
	private final int BORDER_SIZE = 25;
	private final int GRID_SIZE = 25;
	private final int BTN_HEIGHT = INTERFACE_SIZE-10;
	private final int BTN_BORDER = (INTERFACE_SIZE-BTN_HEIGHT)/2;
	private final int BTN_WIDTH = 50;
	
	/**
	 * Initializes a match field for the game
	 * @param mf Minefield
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
				int buttonClicked = 0;
				if (SwingUtilities.isLeftMouseButton(e)) {
					buttonClicked = 1;
				} else if (SwingUtilities.isRightMouseButton(e)) {
					buttonClicked = 2;
				}
				if (e.getX()<BORDER_SIZE+minefield.getxDim()*GRID_SIZE-BTN_BORDER&&
						e.getX()>BORDER_SIZE+minefield.getxDim()*GRID_SIZE-BTN_WIDTH-BTN_BORDER&&
						e.getY()<BOX_SIZE+BORDER_SIZE+BTN_BORDER+BTN_HEIGHT&&
						e.getY()>BOX_SIZE+BORDER_SIZE+BTN_BORDER) {
					minefield = new Minefield(minefield.getxDim(), minefield.getyDim());
					System.out.println("Game Reset");
				}
				if (e.getX()-BORDER_SIZE > GRID_SIZE*minefield.getxDim()||
						e.getY()-BOX_SIZE-INTERFACE_SIZE-BORDER_SIZE > GRID_SIZE*minefield.getyDim()||
						e.getX() < BORDER_SIZE||
						e.getY() < BOX_SIZE+INTERFACE_SIZE+BORDER_SIZE||
						minefield.isGameOver())
					return;
				int gridIndexClicked = ((e.getX()-BORDER_SIZE)/GRID_SIZE)+((e.getY()-BOX_SIZE-INTERFACE_SIZE-BORDER_SIZE)/GRID_SIZE)*minefield.getxDim();
				if (buttonClicked==1) {
					if (minefield.getMineCount()==0) {
						minefield = new Minefield(minefield.getxDim(), minefield.getyDim(), gridIndexClicked, 40);
					}
				}
				minefield.minefieldClicked(gridIndexClicked, buttonClicked);	
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
		Font font = new Font("Verdana", Font.BOLD, 12);
		g.setColor(Color.WHITE);
		g.fillRect(BORDER_SIZE, BOX_SIZE+BORDER_SIZE, minefield.getxDim()*GRID_SIZE, INTERFACE_SIZE);
		g.setColor(Color.RED);
		g.fillRect(BORDER_SIZE+minefield.getxDim()*GRID_SIZE-BTN_WIDTH-BTN_BORDER, BOX_SIZE+BORDER_SIZE+BTN_BORDER, BTN_WIDTH, BTN_HEIGHT);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawRect(BORDER_SIZE, BOX_SIZE+BORDER_SIZE, minefield.getxDim()*GRID_SIZE, INTERFACE_SIZE);
		g.drawString("Reset", (int)(BORDER_SIZE+minefield.getxDim()*GRID_SIZE-BTN_WIDTH*(0.85)-BTN_BORDER), (int)(BOX_SIZE+BORDER_SIZE+(0.2)*BTN_HEIGHT+FONT_HEIGHT)+BTN_BORDER);
		g.drawString("" + (minefield.getMineCount()-minefield.getTilesMarked()), BORDER_SIZE+FONT_HEIGHT, GRID_SIZE+BORDER_SIZE+20);
		
		g.setColor(Color.BLACK);
		font = new Font("Verdana", Font.BOLD, 8);
		if (this.minefield.getGameWon()) 
			g.drawString("Game Won!!!", (int)(BORDER_SIZE+50), (int)(BOX_SIZE+BORDER_SIZE+(0.2)*BTN_HEIGHT+FONT_HEIGHT)+BTN_BORDER);
	}
	/**
	 * Draws the grid
	 * @param g Graphics
	 */
	private void drawGrid(Graphics g) {
		for (int i = 0; i < minefield.getyDim(); i++) {
			for (int j = 0; j < minefield.getxDim(); j++) {
				GridElement.TileStatus color = this.minefield.getStatusAtIndex(i*this.minefield.getxDim()+j);
				g.setColor(minefield.getColor(color));
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
}
