package ControlOrLength;

import Populations.*;
import PopulationsInfo.*;
import java.util.*;
import java.io.*;

public class GivenLength {
	private double IGP;
	private double totalLength;
	private int givenLength;
	private int[][] dist;
	private Point[] terminalArr;
	private InputDataProcess idp = null;
	
	public GivenLength() {
		idp = InputDataProcess.getInputDataProcess();
		terminalArr = new Point[ConstOfGA.NUMOFTERMINALS];
		for(int i = 0; i < terminalArr.length; i++) {
			terminalArr[i] = idp.getTerminalCoor(i);
		}
		preprocessingDist();
		totalLength = (0.6666666666666667) * (double)calGivenLength();
		givenLength = (int) (totalLength * (double)ConstOfGA.GIVENLENGTHVALUE);
		
		setIGP();
	}
	
	public int getGivenLength() {
		return givenLength;
	}
	
	public double getTotalLength() {
		return totalLength;
	}

	public double getIGP() { 
		return IGP;
	}
	
	private void setIGP() {
		double igp = Math.min(0.95, ConstOfGA.SCALROFIGP * ((double)givenLength / (double)totalLength));
		IGP = igp;
	}
	
	private int calGivenLength() {
		boolean[] selected = new boolean[terminalArr.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[terminalArr.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0;
		
		// 시작 터미널 고르기
		int selectedIndex = 0;
		selected[0] = true;
		
		// Prim Algorithm
		for(int k = 1; k < terminalArr.length; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < terminalArr.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
				}
			}
			
			int min = Integer.MAX_VALUE;
			
			// 가장 가까운 터미널 선택
			for(int i = 0; i < terminalArr.length; i++) {
				if(min > minDist[i] && selected[i] == false) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			// 거리 값 업데이트
			res = res + min;
			selected[selectedIndex] = true;
		}
		return res;
	}
	
	private void preprocessingDist() {
		dist = new int[terminalArr.length][terminalArr.length];
		
		for(int i = 0; i < terminalArr.length; i++) {
			for(int j = i; j < terminalArr.length; j++) {
				int d = getDist(terminalArr[i], terminalArr[j]);
				dist[i][j] = d;
				dist[j][i] = d;
			}
		}
	}
	
	private int getDist(Point p1, Point p2) {
		int d = 0;
		int x_dist = Math.abs(p1.getX() - p2.getX());
		int y_dist = Math.abs(p1.getY() - p2.getY());
		
		d = x_dist + y_dist;
		return d;
	}
}
