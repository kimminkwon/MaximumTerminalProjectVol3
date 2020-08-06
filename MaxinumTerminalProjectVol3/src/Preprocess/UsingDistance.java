package Preprocess;

import Populations.Point;

public class UsingDistance {
	Point[] totalTerminals;
	int[][] dist;
	
	 
	public UsingDistance(Point[] totalTerminals) {
		this.totalTerminals = totalTerminals;
	}

	public int[][] preprocessingDist() {
		dist = new int[totalTerminals.length][totalTerminals.length];
		
		for(int i = 0; i < totalTerminals.length; i++) {
			for(int j = i; j < totalTerminals.length; j++) {
				int d = getDist(totalTerminals[i], totalTerminals[j]);
				dist[i][j] = d;
				dist[j][i] = d;
			}
		}
		return dist;
	}
	
	private int getDist(Point p1, Point p2) {
		int d = 0;
		int x_dist = Math.abs(p1.getX() - p2.getX());
		int y_dist = Math.abs(p1.getY() - p2.getY());
		
		d = x_dist + y_dist;
		return d;
	}
	
	public int makeRandom(int limit) {
		int r = (int)(limit * Math.random());
		return r;
	}
}
