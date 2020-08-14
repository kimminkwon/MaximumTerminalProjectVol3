package GenenticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import Populations.ConstOfGA;
import Populations.Individual;
import Populations.Point;
import PopulationsInfo.CalLength;
import PopulationsInfo.InputDataProcess;
import Preprocess.UsingDistance;

public class LocalSearch {
	private double totalLength;
	private int givenLength;
	private int[][] dist;
	private ArrayList<Point> totalTerminals;
	private InputDataProcess idp = null;
	private Individual calIndividual;
	private CalLength calLength;
	private boolean[] terminalbox;
	private int[] terminalNumHash;
	
	public LocalSearch(Individual individual, int givenLength) {
		this.givenLength = givenLength;
		this.calIndividual = individual;
		this.idp = InputDataProcess.getInputDataProcess();
		this.dist = new int[ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()][ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()];
		this.terminalNumHash = new int[ConstOfGA.NUMOFTERMINALS];
		
		
		// 합친 터미널 집합 만들기
		totalTerminals = new ArrayList<Point>();
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			terminalNumHash[i] = i;
			totalTerminals.add(idp.getTerminalCoor(i));
		}

		for(Point p : calIndividual.getSteinerPointStatus()) {
			totalTerminals.add(p);
		}
		
		System.out.println("TotalTerminals: " + totalTerminals);
	}
	
	public void localSearch() {
		int cntOfNewSteiner = steinerDelete();
		System.out.println("delete 연산 후 totalTerminals: " + totalTerminals);
		
		steinerAdd(cntOfNewSteiner);
		System.out.println("add 연산 후 totalTerminals: " + totalTerminals);
		// UsingDistance usingDist = new UsingDistance(totalTerminals);
		// this.dist = usingDist.preprocessingDist();
		
	}
	
	private int steinerDelete() {
		int cntOfNewSteiner = 0;
		int cnt = 0;
		int cntDegree = calIndividual.getNumOfTerminal();
		Iterator<Point> iter = totalTerminals.iterator();
		for(int i = 0; i < totalTerminals.size(); i++) {
			if(i >= ConstOfGA.NUMOFTERMINALS) {
				int degree = calIndividual.getDegreeOfSteinerPoint()[cntDegree];
				System.out.println("cnt: " + i + ", totalTerminals[" + i + "] = " + totalTerminals.get(i));
				System.out.println("steinerDegree[cntDegree] = " + calIndividual.getDegreeOfSteinerPoint()[cntDegree]);
				
				if(degree == 1) {
					if(makeRandom() < ConstOfGA.PROBSTEINERDELETE) {
						System.out.println("degree 1이 삭제 확률에 걸림");
						totalTerminals.set(i, null);
					}
				}
				
				if(degree == 2) {
					if(makeRandom() < ConstOfGA.PROBSTEINERDELETE) {
						System.out.println("degree 2가 삭제 확률에 걸림");
						totalTerminals.set(i, null);
						if(makeRandom() < ConstOfGA.PROBSTEINERADD) {
							System.out.println("새로운 스타이너로 추가 예정");
							cntOfNewSteiner++;
						}
					}
				}
				cntDegree++;
			}	
		}
		terminalListChange();
		return cntOfNewSteiner;
	}
	
	private void steinerAdd(int cntOfNewSteiner) {
		for(int k = 0; k < cntOfNewSteiner; k++) {
			Point[] terminalArr = new Point[calIndividual.getNumOfTerminal()];
			int cnt = 0;
			for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
				if(calIndividual.getTerminalStatus()[i] == true) {
					terminalArr[cnt] = idp.getTerminalCoor(i);
					cnt++;
				}
			}
			System.out.println("termianlArr = " + Arrays.toString(terminalArr));
			
			int r = makeRandom(terminalArr.length);
			Point p = terminalArr[r];
			
			Point newSteinerPoint = closedPairPoint(r, p, terminalArr);
			if(newSteinerPoint != null) {
				totalTerminals.add(newSteinerPoint);
			}
		}
	}
	
	private Point closedPairPoint(int indexOfP, Point p, Point[] terminalArr) {
		Point[] pointPair = new Point[3];
		pointPair[0] = p;
		int minDist = Integer.MAX_VALUE;
		int minDistIndex = 0;
		
		for(int i = 0; i < terminalArr.length; i++) {
			int dist = getDist(p, terminalArr[i]);
			if(minDist > dist && i != indexOfP) {
				minDist = dist;
				minDistIndex = i;
			}
		}
		System.out.println("기준 Point: " + pointPair[0]);
		System.out.println("1번쨰 Pair [ index = " + minDistIndex + ", minDist = " + minDist + ", Point = " + terminalArr[minDistIndex]);
		
		minDist = Integer.MAX_VALUE;
		int minDistIndexTwo = 0;
		
		for(int i = 0; i < terminalArr.length; i++) {
			int dist = getDist(p, terminalArr[i]);
			if(minDist > dist && i != indexOfP && i != minDistIndex) {
				minDist = dist;
				minDistIndexTwo = i;
			}
		}
		
		System.out.println("2번쨰 Pair [ index = " + minDistIndexTwo + ", minDist = " + minDist + ", Point = " + terminalArr[minDistIndexTwo]);
		
		pointPair[1] = terminalArr[minDistIndex];
		pointPair[2] = terminalArr[minDistIndexTwo];
		
		System.out.println(Arrays.toString(pointPair));
		
		Point newSteinerPoint = middlePoint(pointPair);
		
		boolean comparedRes = false;
		for(int i = 0; i < pointPair.length; i++) {
			comparedRes = comparePoint(newSteinerPoint, pointPair[i]);
		}
		
		if(comparedRes == true) {
			return null;
		}
		else return newSteinerPoint;
		
	}
	
	private boolean comparePoint(Point p1, Point p2) {
		boolean res = false;
		
		if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
			res = true;
		}
		
		return res;
	}
	
	private Point middlePoint(Point[] pointPair) {
		int[] xArr = new int[3];
		int[] yArr = new int[3];
		
		for(int i = 0; i < pointPair.length; i++) {
			xArr[i] = pointPair[i].getX();
			yArr[i] = pointPair[i].getY();
		}

		Arrays.sort(xArr);
		Arrays.sort(yArr);
		
		return new Point(xArr[1], yArr[1]);
		
	}
	
	private int getDist(Point p1, Point p2) {
		int d = 0;
		int x_dist = Math.abs(p1.getX() - p2.getX());
		int y_dist = Math.abs(p1.getY() - p2.getY());
		
		d = x_dist + y_dist;
		return d;
	}
	
	private void terminalListChange() {
		ArrayList<Point> totalTerminalsBox = new ArrayList<Point>();
		
		for(int i = 0; i < totalTerminals.size(); i++) {
			if(totalTerminals.get(i) != null) {
				totalTerminalsBox.add(totalTerminals.get(i));
			}
		}
		
		totalTerminals = new ArrayList<Point>();
		for(int i = 0; i < totalTerminalsBox.size(); i++) {
			totalTerminals.add(totalTerminalsBox.get(i));
		}
	}
	
	private double makeRandom() {
		double r = Math.random();
		return r;
	}
	
	private int makeRandom(int limit) {
		int r = (int) (limit * Math.random());
		return r;
	}
}
