/*
 * Score data handling system
 * Author: Jehrick Robertson
 * Date: 2/11/11
 */

import java.io.Serializable;


public class Score implements Serializable{
	private static final long serialVersionUID = 8004834168726153475L;
	private String name;
	private int points;


	Score (String name, int points){
		this.name = name;
		this.points = points;
	}

	public int getPoints(){
		return points;
	}

	public String getName(){
		return name;
	}

	public int compareTo(Score score) {
		if (points < score.getPoints()){
			return 1;
		}
		else if (points > score.getPoints()){
			return 2;
		}
		else
			return 0;
	}
}
