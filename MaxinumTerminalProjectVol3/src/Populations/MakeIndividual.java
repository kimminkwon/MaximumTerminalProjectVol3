package Populations;

import java.util.*;

import PopulationsInfo.InputDataProcess;

public class MakeIndividual {
	private double IGP;
	private double[] igpArr;
	private boolean[] terminalStatus;
	private int numOfTerminals;
	private ArrayList<Point> steinerPointStatus;
	
	public MakeIndividual(double IGP, double[] igpArr) {
		this.IGP = IGP;
		this.igpArr = Arrays.copyOf(igpArr, igpArr.length);
		
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
		for(int i = 0; i < limit; i++) {
			steinerPointStatus.add(new Point(hananV.get(makeRandom(hananV.size())), hananH.get(makeRandom(hananH.size()))));
		}
		/*
		System.out.print("생성된 스타이너 포인트: ");
		
		for(Point p : steinerPointStatus) {
			System.out.print("(" + p.getX() + ", " + p.getY() + ") - ");
		}
		*/
	}
	
	private int makeRandom(int limit) {
		int r = (int)(limit * Math.random());
		return r;
	}
	
}
