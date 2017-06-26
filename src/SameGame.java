/*
 * SameGame core program
 * Author: Jehrick Robertson
 * Date: 2/11/11
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class SameGame extends JFrame {
	//serial ID
	private static final long serialVersionUID = 6742817533186544929L;

	//constants
	private final int FRAME_HEIGHT = 780;
	private final int FRAME_WIDTH = 1024;
	private final int FRAME_X_ORIGIN = 400;
	private final int FRAME_Y_ORIGIN = 400;
	private final int NUM_COLS = 20;
	private final int NUM_ROWS = 20;
	//private final int TOTAL_TILES = NUM_COLS*NUM_ROWS;
	private final byte POINT_CONSTANT = 2;

	//variables
	private boolean game = true;
	private HighScore highscores = new HighScore("scores.dat");
	private int score = 0;
	private int highScore = highscores.returnHighScore();
	private GameTile[][] tiles = new GameTile[NUM_COLS][NUM_ROWS];
	private int numOfTilesSelected = 0;
	private int potentialPoints = 0;
	private int tilesLeft = NUM_COLS*NUM_ROWS;
	private int resets = 3;
	private int lastColumnSelected = 0;
	private int lastRowSelected = 0;

	//components
	private JLabel scoreLabel;
	private JLabel highScoreLabel;
	private JLabel tilesSelectedLabel;
	private JLabel potentialPointsLabel;
	private JLabel tilesLeftLabel;
	private JLabel resetsLeftLabel;
	private JButton quitBtn;
	private JButton newGameBtn;
	private JButton resetBtn;
	private JButton highScoreBtn;
	private JPanel P;
	private JPanel Q;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JPanel westPanel;

	//constructor
	SameGame(){

		//component initialization
		super("Same Game");
		scoreLabel = new JLabel ("Score: 0");
		highScoreLabel = new JLabel ("High Score: 0");
		tilesSelectedLabel = new JLabel ("Tiles \nSelected: \n0");
		potentialPointsLabel = new JLabel ("Potential \nPoints: \n0");
		tilesLeftLabel = new JLabel ("Tiles Left: \n0");
		resetsLeftLabel = new JLabel ("Resets Left: \n3");
		quitBtn = new JButton ("Quit");
		quitBtn.addActionListener(new GameHandler());
		quitBtn.setActionCommand("Quit");
		newGameBtn = new JButton ("New Game");
		newGameBtn.addActionListener(new GameHandler());
		newGameBtn.setActionCommand("New");
		resetBtn = new JButton ("Reset Board");
		resetBtn.addActionListener(new GameHandler());
		resetBtn.setActionCommand("Reset");
		highScoreBtn = new JButton ("Check High Scores");
		highScoreBtn.addActionListener(new GameHandler());
		highScoreBtn.setActionCommand("HighScore");
		P = new JPanel ();
		Q = new JPanel ();
		northPanel = new JPanel();
		southPanel = new JPanel();
		eastPanel = new JPanel();
		westPanel = new JPanel();

		//GUI setup
		setBounds(FRAME_X_ORIGIN, FRAME_Y_ORIGIN, FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		P.setLayout(new BorderLayout(10,10));
		setContentPane(P);
		Q.setLayout(new GridLayout(NUM_ROWS, NUM_COLS));
		northPanel.setLayout(new GridLayout (1,2,20,20));
		southPanel.setLayout(new GridLayout (1,4,20,20));
		eastPanel.setLayout(new GridLayout (2,1,20,20));
		westPanel.setLayout(new GridLayout (2,1,20,20));
		P.add(Q, BorderLayout.CENTER);
		P.add(northPanel, BorderLayout.NORTH);
		P.add(southPanel, BorderLayout.SOUTH);
		P.add(eastPanel, BorderLayout.EAST);
		P.add(westPanel, BorderLayout.WEST);

		northPanel.add(scoreLabel);
		northPanel.add(highScoreLabel);
		northPanel.setPreferredSize(new Dimension(800, 16));
		westPanel.add(tilesSelectedLabel);
		westPanel.add(potentialPointsLabel);
		westPanel.setPreferredSize(new Dimension(160, 800));
		eastPanel.add(tilesLeftLabel);
		eastPanel.add(resetsLeftLabel);
		eastPanel.setPreferredSize(new Dimension(160, 800));
		southPanel.add(quitBtn);
		southPanel.add(newGameBtn);
		southPanel.add(resetBtn);
		southPanel.add(highScoreBtn);
		southPanel.setPreferredSize(new Dimension(800, 32));

		//Initializing tiles
		for (int y = 0; y < NUM_ROWS; y++){
			for (int x = 0; x < NUM_COLS; x++){
				tiles[x][y] = new GameTile();
				tiles[x][y].addActionListener(new TileClickHandler());
				tiles[x][y].setCol(x);
				tiles[x][y].setRow(y);
				Q.add(tiles[x][y]);
			}
		}
		setVisible(true);
		UpdateHUD();
	}

	//methods
	public boolean areMovesLeft(){
		return true;
	}

	public void nextLevel(){
		if (tilesLeft == 0){
			GameTile.increaseDifficulty();
			for (int y = 0; y < NUM_ROWS; y++){
				for (int x = 0; x < NUM_COLS; x++){
					tiles[x][y].reset();
				}
			}
			numOfTilesSelected = 0;
			potentialPoints = 0;
			tilesLeft = NUM_COLS*NUM_ROWS;
			resets = 3;
			lastColumnSelected = 0;
			lastRowSelected = 0;
			UpdateHUD();
		}
	}

	private void gameOver(){
		JOptionPane.showMessageDialog(null, "Game Over!");
		if (highscores.getPointsToBeat() < score){
			String name = JOptionPane.showInputDialog("You've made the high score! \nPlease input your name.");
			if (!(name == null || name.equals(""))){
				highscores.addScore(new Score(name, score));
			}
			highscores.setVisible(true);
		}
		game = false;
		UpdateHUD();
	}

	public static void main (String args[]){
		SameGame S = new SameGame();
	}

	private void shiftColumnDown(int col, int row){
		for (int n = row; n >= 0; n--){
			if (n != 0){
				tiles[col][n].setColor(tiles[col][n-1].getColor());
				tiles[col][n].setState(tiles[col][n-1].getState());
			} else {
				tiles[col][0].setState(GameTile.REMOVED_STATE);
			}
		}
	}

	private void shiftColumnLeft(int col){
		for (int y = 0; y < NUM_ROWS; y++){
			for (int x = col; x < NUM_COLS; x++){
				if (x != NUM_COLS - 1){
					tiles[x][y].setColor(tiles[x+1][y].getColor());
					tiles[x][y].setState(tiles[x+1][y].getState());
				} else {
					tiles[x][y].setState(GameTile.REMOVED_STATE);
				}
			}
		}
	}

	private boolean CheckForAnyTilesLeft(int col){
		for (int x = col; x < NUM_COLS; x++){
			for (int y = 0; y < NUM_ROWS; y++){
				if (tiles[x][y].getState() != GameTile.REMOVED_STATE){
					return true;
				}
			}
		}
		return false;
	}

	private void ClearEmptyColumns(){
		boolean empty = false;
		for (int x = 0; x < NUM_COLS; x++){
			for (int y = 0; y < NUM_ROWS; y++){
				empty = true;
				if (tiles[x][y].getState() != GameTile.REMOVED_STATE){
					empty = false;
					break;
				}
			}
			if (empty){
				shiftColumnLeft(x);
				if (CheckForAnyTilesLeft(x)){
					x--;
				}
			}
		}
	}

	private void removeSelected(){
		for (int y = 0; y < NUM_ROWS; y++){
			for (int x = 0; x < NUM_COLS; x++){
				if (tiles[x][y].getState() == GameTile.SELECTED_STATE){
					tiles[x][y].setState(GameTile.REMOVED_STATE);
					shiftColumnDown(x,y);
				}
			}
		}
		ClearEmptyColumns();
	}

	private void resetGame(){
		GameTile.NUM_OF_COLORS = GameTile.BEGINNING_NUM_OF_COLORS;
		game = true;
		for (int y = 0; y < NUM_ROWS; y++){
			for (int x = 0; x < NUM_COLS; x++){
				tiles[x][y].reset();
			}
		}
		score = 0;
		numOfTilesSelected = 0;
		potentialPoints = 0;
		tilesLeft = NUM_COLS*NUM_ROWS;
		resets = 3;
		lastColumnSelected = 0;
		lastRowSelected = 0;
		UpdateHUD();
	}

	private void resetBoard(){
		if (resets > 0){
			for (int y = 0; y < NUM_ROWS; y++){
				for (int x = 0; x < NUM_COLS; x++){
					tiles[x][y].resetOriginal();
				}
			}
			numOfTilesSelected = 0;
			potentialPoints = 0;
			tilesLeft = NUM_COLS*NUM_ROWS;
			resets--;
			lastColumnSelected = 0;
			lastRowSelected = 0;
			UpdateHUD();
		} else {
			JOptionPane.showMessageDialog(null, "You are out of resets.");
		}
	}

	private void resetSelected(int col, int row){
		if(tiles[col][row].getState() == GameTile.SELECTED_STATE){
			tiles[col][row].setState(GameTile.NORMAL_STATE);

			//Find tiles to the LEFT
			if(col != 0){
				resetSelected(col-1, row);
			}

			//Find tiles ABOVE
			if(row != 0){
				resetSelected(col, row-1);
			}

			//Find tiles to the RIGHT
			if(col != NUM_COLS-1){
				resetSelected(col+1, row);
			}

			//Find tiles BELOW
			if(row != NUM_ROWS-1){
				resetSelected(col, row+1);
			}
		}
	}

	private void selectTiles(int col, int row, Color color){
		if (tiles[col][row].getState() == GameTile.NORMAL_STATE && color.equals(tiles[col][row].getColor())){
			tiles[col][row].setState(GameTile.SELECTED_STATE);
			numOfTilesSelected++;

			//Find tiles to the LEFT
			if(col != 0){
				selectTiles(col-1, row, color);
			}

			//Find tiles ABOVE
			if(row != 0){
				selectTiles(col, row-1, color);
			}

			//Find tiles to the RIGHT
			if(col != NUM_COLS-1){
				selectTiles(col+1, row, color);
			}

			//Find tiles BELOW
			if(row != NUM_ROWS-1){
				selectTiles(col, row+1, color);
			}
		}
		lastColumnSelected = col;
		lastRowSelected = row;
	}

	/*
	private int tilesLeft(){
		return 0;
	}
	*/

	private void UpdateHUD(){
		if (numOfTilesSelected > 1){
			potentialPoints = (int)Math.pow((double)(numOfTilesSelected - POINT_CONSTANT), 2);
		} else {
			potentialPoints = 0;
		}

		scoreLabel.setText("Score: " + score);
		scoreLabel.revalidate();
		if (score > highScore){
			highScoreLabel.setText("High Score: " + score);
		} else {
			highScoreLabel.setText("High Score: " + highScore);
		}
		highScoreLabel.revalidate();
		tilesSelectedLabel.setText("Tiles \nSelected: \n" + numOfTilesSelected);
		tilesSelectedLabel.revalidate();
		potentialPointsLabel.setText("Potential \nPoints: \n" + potentialPoints);
		potentialPointsLabel.revalidate();
		tilesLeftLabel.setText("Tiles Left: \n" + tilesLeft);
		tilesLeftLabel.revalidate();
		resetsLeftLabel.setText("Resets Left: \n" + resets);
		resetsLeftLabel.revalidate();
		quitBtn.setEnabled(game);
	}

	private class TileClickHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (game){
				GameTile g = (GameTile)e.getSource();
				if(g.getState() == GameTile.SELECTED_STATE && numOfTilesSelected > 1){
					removeSelected();
					score += potentialPoints;
					tilesLeft -= numOfTilesSelected;
					numOfTilesSelected = 0;
					resetSelected(lastColumnSelected, lastRowSelected);
					UpdateHUD();
					nextLevel();
				} else {
					numOfTilesSelected = 0;
					resetSelected(lastColumnSelected, lastRowSelected);
					selectTiles(g.getCol(), g.getRow(), g.getColor());
					UpdateHUD();
				}
			}
		}
	}

	private class GameHandler implements ActionListener{
		public void actionPerformed (ActionEvent e) {
			if ("Quit".equals(e.getActionCommand())){
				gameOver();
			} else if ("New".equals(e.getActionCommand())){
				resetGame();
			} else if ("Reset".equals(e.getActionCommand())){
				resetBoard();
			} else if ("HighScore".equals(e.getActionCommand())){
				highscores.setVisible(true);
			}
		}
	}

}
