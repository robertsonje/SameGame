/*
 * Game tile logic
 * Author: Jehrick Robertson
 * Date: 2/11/11
 */

/**
 * A game tile with a color. Used in the Same Game class.
 *
 * @version    1.1 11 Feb 2011
 * @author     Jehrick Robertson
 */

import java.awt.Color;
import java.util.Random;

import javax.swing.JButton;


public class GameTile extends JButton {


	/**
	  * The serial number.
	  * @see GameTile#serialVersionUID
	  */
	private static final long serialVersionUID = -2789633999486169188L;

	/**
	  * The color of the tile.
	  * @see GameTile#color
	  */
	private Color color;

	/**
	  * The color needed for the "Reset" button.
	  * @see GameTile#permanentColor
	  */
	private Color permanentColor;

	/**
	  * The random number generator needed for the generation of boards.
	  * @see GameTile#rGen
	  */
	private static Random rGen = new Random();
	private byte state;

	//CONSTANTS

	/**
	  * A constant 10-color array that contains the following colors: red, green, blue, yellow, cyan,
	  * orange, magenta, pink, white, and light gray.
	  * @see GameTile#COLORS
	  */
	private static final Color[] COLORS = {Color.red, Color.green, Color.blue, Color.yellow, Color.cyan, Color.orange,
												Color.magenta, Color.pink, Color.white, Color.lightGray};

	/**
	  * The color that the tile changes to when it's selected. It's gray.
	  * @see GameTile#SELECTED_COLOR
	  */
	public static final Color SELECTED_COLOR = Color.gray;

	/**
	  * The color that the tile changes to when it's removed. It's black.
	  * @see GameTile#REMOVED_COLOR
	  */
	public static final Color REMOVED_COLOR = Color.black;

	/**
	  * The state that indicates that the tile is normal.
	  * @see GameTile#NORMAL_STATE
	  */
	public static final byte NORMAL_STATE = 0;

	/**
	  * The state that indicates that the tile is removed, and that there is no tile in its position.
	  * @see GameTile#REMOVED_STATE
	  */
	public static final byte REMOVED_STATE = 2;

	/**
	  * The state that indicates that the tile is selected.
	  * @see GameTile#SELECTED_STATE
	  */
	public static final byte SELECTED_STATE = 1;

	/**
	  * The number of colors that the tile has at the start of the game.
	  * @see GameTile#BEGINNING_NUM_OF_COLORS
	  */
	public static final byte BEGINNING_NUM_OF_COLORS = 3;

	/**
	  * The number of colors that the tile has, currently.
	  * @see GameTile#NUM_OF_COLORS
	  */
	public static byte NUM_OF_COLORS = BEGINNING_NUM_OF_COLORS; //must be under 10

	/**
	  * The column the tile is on.
	  * @see GameTile#col
	  */
	private int col = 0;

	/**
	  * The row the tile is on.
	  * @see GameTile#row
	  */
	private int row = 0;

	/**
	  * Constructor of the GameTile.
	  * Sets up the tile.
	  * February 11, 2011
	  *
	  * @param none.
	  */
	public GameTile (){
		super("");
		state = NORMAL_STATE;
		permanentColor = COLORS[Math.abs((rGen.nextInt() % NUM_OF_COLORS))];
		color = permanentColor;
		setBackground(color);
		setForeground(color);
	}

	/**
	  * getColor
	  * Returns the color.
	  * February 11, 2011
	  *
	  * @param none.
	  * @return the color of the tile.
	  */
	public Color getColor(){
		return color;
	}

	/**
	  * setColor
	  * Sets the color.
	  * February 11, 2011
	  *
	  * @param color the color to set with.
	  */
	public void setColor(Color color){
		this.color = color;
		setBackground(this.color);
		setForeground(this.color);
	}

	/**
	  * getState
	  * Returns the state.
	  * February 11, 2011
	  *
	  * @param none.
	  * @return the state.
	  */
	public byte getState(){
		return state;
	}

	/**
	  * setState
	  * Sets the state and the color associated with it.
	  * February 11, 2011
	  *
	  * @param state the state to set with.
	  */
	public void setState (byte state){
		this.state = state;
		switch (this.state){
			case NORMAL_STATE:
				setBackground(color);
				setForeground(color);
				break;
			case SELECTED_STATE:
				setBackground(SELECTED_COLOR);
				setForeground(SELECTED_COLOR);
				break;
			case REMOVED_STATE:
				setBackground(REMOVED_COLOR);
				setForeground(REMOVED_COLOR);
				break;
			default:
		}
	}

	/**
	  * getCol
	  * returns the column.
	  * February 11, 2011
	  *
	  * @param none.
	  * @return the column.
	  * <pre>
	  *  tile.setState(REMOVED_STATE);
	  * </pre>
	  */
	public int getCol(){
		return col;
	}

	/**
	  * getRow
	  * returns the row.
	  * February 11, 2011
	  *
	  * @param none.
	  * @return the row.
	  */
	public int getRow(){
		return row;
	}

	/**
	  * setCol
	  * Sets the column for initialization.
	  * February 11, 2011
	  *
	  * @param col the column to set with.
	  */
	public void setCol(int col){
		this.col = col;
	}

	/**
	  * setRow
	  * Sets the row for initialization.
	  * February 11, 2011
	  *
	  * @param row the row to set with.
	  */
	public void setRow(int row){
		this.row = row;
	}

	/**
	  * reset
	  * Resets itself for either a new game or a new level.
	  * February 11, 2011
	  *
	  * @param none.
	  */
	public void reset(){
		state = NORMAL_STATE;
		permanentColor = COLORS[Math.abs((rGen.nextInt() % NUM_OF_COLORS))];
		color = permanentColor;
		setBackground(color);
		setForeground(color);
	}

	/**
	  * resetOriginal
	  * Resets the original color given to the tile..
	  * February 11, 2011
	  *
	  * @param none.
	  * <pre>
	  *  tile.setState(REMOVED_STATE);
	  * </pre>
	  */
	public void resetOriginal(){
		state = NORMAL_STATE;
		color = permanentColor;
		setBackground(color);
		setForeground(color);
	}

	/**
	  * increaseDifficulty
	  * increases the number of colors for added difficulty.
	  * February 11, 2011
	  *
	  * @param none.
	  */
	public static void increaseDifficulty(){
		if (NUM_OF_COLORS != 10){
			NUM_OF_COLORS++;
		}
	}
}
