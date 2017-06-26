/*
 * High score table class
 * Author: Jehrick Robertson
 * Date: 2/11/11
 */

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class HighScore extends JFrame{
	private static final long serialVersionUID = 551026359166394006L;
	private String filename;
	private static int HEIGHT = 800;
	private static int WIDTH = 600;
	private JPanel P;
	private JPanel Q;
	private static final int MAX_NUM_SCORES = 15;
	private ArrayList<JLabel> names;
	private ArrayList<JLabel> points;
	private ArrayList<Score> scores;
	private static ObjectOutputStream fileOut;
	private static ObjectInputStream fileIn;

	public HighScore(String filename){

		super("Your High Scores");
		this.filename = filename;
		scores = new ArrayList<Score>();
		names = new ArrayList<JLabel>();
		points = new ArrayList<JLabel>();
		for (int n = 0; n < MAX_NUM_SCORES; n++){
			scores.add(new Score("Nobody",0));
			names.add(new JLabel("Nobody"));
			points.add(new JLabel("0"));
		}
		File fileExists = new File(filename);
		if (!fileExists.exists()){
			try {
				fileExists.createNewFile();
			} catch (IOException e){
			}
		} else {
			readScores();
		}
		updateArrays();
		createGUI();
	}

	private void createGUI(){
		P = new JPanel();
		P.setLayout(new GridLayout(2,1,10,10));
		Q = new JPanel();
		Q.setLayout(new GridLayout(15,2,10,10));
		P.add(Q);
		setContentPane(P);
		setBounds(200,200,HEIGHT, WIDTH);

		for (int n = 0; n < names.size(); n++){
			Q.add(names.get(n));
			Q.add(points.get(n));
		}
	}

	public int getPointsToBeat(){
		return scores.get(scores.size()-1).getPoints();
	}

	public void addScore(Score score){
		if (score.getPoints() > getPointsToBeat()){
			int n = 0;
			for (int i = 0; i < scores.size(); i++){
				if (scores.get(i).getPoints() > score.getPoints()) {
					n++;
				}
			}
			scores.add(n, score);
			scores.remove(15);
			writeScores();
			updateArrays();
		}
	}

	private void writeScores() {
		try {
			fileOut = new ObjectOutputStream(new FileOutputStream(filename));
			fileOut.writeObject(scores);
			fileOut.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: File \"" + filename + "\" not found");
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "ERROR: IO Error");
		}
	}

	private void readScores() {
		try {
			fileIn = new ObjectInputStream(new FileInputStream(filename));
			scores = (ArrayList<Score>) fileIn.readObject();
			fileIn.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ERROR: File \"" + filename + "\" not found");
		} catch (IOException e){
			JOptionPane.showMessageDialog(null, "ERROR: IO error");
		} catch (ClassNotFoundException e){
			JOptionPane.showMessageDialog(null, "ERROR: Class \"ArrayList<Score>\" not found");
		}
	}

	private void updateArrays() {
		for (int n = 0; n < scores.size(); n++){
			names.get(n).setText(scores.get(n).getName());
			names.get(n).revalidate();
			points.get(n).setText(Integer.toString((scores.get(n).getPoints())));
			points.get(n).revalidate();
		}
	}

	public int returnHighScore(){
		return scores.get(0).getPoints();
	}
}
