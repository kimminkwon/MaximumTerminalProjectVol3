package Populations;

import java.util.*;

import PopulationsInfo.InputDataProcess;

public class MakeIndividual {
	private double IGP;
	private double[] igpArr;
	private boolean[] terminalStatus;
	private int numOfTerminals;
	private ArrayList<Point> steinerPointStatus;
	private Point[] terminals;
	
	
	public MakeIndividual(double IGP, double[] igpArr) {
		this.IGP = IGP;
		this.igpArr = Arrays.copyOf(igpArr, igpArr.length);
		this.terminals = InputDataProcess.getInputDataProcess().getTerminals();
		// System.out.println(Arrays.toString(this.igpArr));
		makeTerminalStatus();
		makeSteinerPointStatus();
	}
	
	public boolean[] getTerminalStatus() {
		return terminalStatus;
	}

	public ArrayList<Point> getSteinerPointStatus() {
		return steinerPointStatus;
	}

	private void makeTerminalStatus() {
		terminalStatus = new boolean[ConstOfGA.NUMOFTERMINALS];
		Arrays.fill(terminalStatus, false);
		double[] terminalRand = new double[ConstOfGA.NUMOFTERMINALS];
		
		int flag = (int)(ConstOfGA.NUMOFTERMINALS / igpArr.length);
		int cnt = 0;
		int box = flag;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(i > box && cnt < igpArr.length - 1) {
				box = box + flag;
				cnt++;
			}
			terminalRand[i] = igpArr[cnt] * IGP;
		}
		
		numOfTerminals = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			double randValue = Math.random();
			if(randValue < terminalRand[i]) {
				terminalStatus[i] = true;
				numOfTerminals++;
			}
		}
		// System.out.println("terminalStatus: " + Arrays.toString(terminalStatus));
	}
	
	private void makeSteinerPointStatus() {
		steinerPointStatus = new ArrayList<>();
		
		ArrayList<Integer> hananV = InputDataProcess.getInputDataProcess().getHanan_vertical();
		ArrayList<Integer> hananH = InputDataProcess.getInputDataProcess().getHanan_horizental();
		
		int limit = Math.max(0, makeRandom(numOfTerminals) - 1);
		
		// System.out.println("터미널 개수보다 적은 난수(스타이너 포인트 개수): " + limit);
		int cnt = 0;
		while(cnt < limit) {
			Point p = new Point(hananV.get(makeRandom(hananV.size())), hananH.get(makeRandom(hananH.size())));
			if(isOverlap(p) == false) {
				steinerPointStatus.add(p);	
				cnt++;
			}
		}
		/*
		System.out.print("생성된 스타이너 포인트: ");
		
		for(Point p : steinerPointStatus) {
			System.out.print("(" + p.getX() + ", " + p.getY() + ") - ");
		}
		*/
	}
	
	private boolean isOverlap(Point p1) {
		boolean res = false;
		for(int i = 0; i < terminals.length; i++) {
			Point p2 = terminals[i];
			if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
				res = true;
			}
		}
		for(Point p2 : steinerPointStatus) {
			if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
				res = true;
			}
		}
		return res;
	}
	
	private int makeRandom(int limit) {
		int r = (int)(limit * Math.random());
		return r;
	}
	
}
