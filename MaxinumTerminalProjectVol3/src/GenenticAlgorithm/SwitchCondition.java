package GenenticAlgorithm;

import java.util.*;
import java.util.HashSet;
import Populations.ConstOfGA;
import Populations.Point;
import Populations.Population;
import PopulationsInfo.CalFitness;
import PopulationsInfo.CalLength;
import PopulationsInfo.InputDataProcess;
import Preprocess.UsingDistance;

public class SwitchCondition {
	private Population population;
	private int givenLength;
	//////
	private HashSet<Point> inOfConvexHallSteinerPoints;
	private ArrayList<Point> inOfConvexHallSteinerPointsList;
	private Point[] terminals;
	/////
	
	public SwitchCondition(Population population, int givenLength) {
		this.population = population;
		this.givenLength = givenLength;
		//////
		this.inOfConvexHallSteinerPoints = new HashSet<Point>();
		this.inOfConvexHallSteinerPointsList = new ArrayList<Point>();
		//////
	}
	
	public boolean switchConst() {
		if(isSwitch() == true) {
			System.out.print("Swicth!");
			localSearchSwitch();
			fitnessSwitch();
			fitnessModify();
			mutationSwitch();
			makeNewSteinerPointInPopulation();
			return true;
		}
		else
			return false;
	}
	
	private void fitnessModify() {
		CalFitness cf;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			cf = new CalFitness(population.getIndividual(i), givenLength); 
			population.setFitnessOfIndividual(i, cf.calFitness());
		}
	}
	
	private void mutationSwitch() {
		ConstOfGA.setMutationporbSteiner(0.5);
	}
	
	private void localSearchSwitch() {
		ConstOfGA.setProbsteineradd(0.9);
		ConstOfGA.setProbsteinerdelete(0.2);
		ConstOfGA.setProbsteineraddparttwo(0.8);
	}
	
	private void fitnessSwitch() {
		ConstOfGA.setAlpha(ConstOfGA.ALPHA_PART2);
		ConstOfGA.setBeta(ConstOfGA.BETA_PART2);
		ConstOfGA.setGamma(ConstOfGA.GAMMA_PART2);
	}
	
	
	private boolean isSwitch() {
		boolean res = false;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			int cnt = 0;
			for(int j = 0; j < ConstOfGA.SIZEOFPOPULATION; j++) {
				if(i != j && population.getFitnessOfIndividual(i) == population.getFitnessOfIndividual(j)) {
					cnt++;
				}
				if(cnt > (ConstOfGA.SIZEOFPOPULATION * ConstOfGA.PROBOFSWITCHCONDITION)) {
					res = true;
				}
			}
		}
	
		return res;
	}
	
	///////////////////////////////////////
	
	private void makeNewSteinerPointInPopulation() {
		int maxTerminalsIndex = findMaximumTerminalIndex();
		makeInOfConvexHallSteinerPointList(maxTerminalsIndex);
		// makeConvexHallList(maxTerminalsIndex);
		// makeHananGrid();
		// System.out.println(inOfConvexHallSteinerPoints);
		// System.out.println(inOfConvexHallSteinerPointsList);
		
		Adjustment aj;
		CalLength cl;
		CalFitness calFitness;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			if(makeRandom() < ConstOfGA.PROBOFSWITCHNEWSTEINERMAKE) {
				ArrayList<Point> steinerPointStatus = makeNewSteinerPointList(maxTerminalsIndex);
				population.getIndividual(i).setSteinerPointStatus(steinerPointStatus);
				aj = new Adjustment(population.getIndividual(i), givenLength);
				aj.adjustment();
				cl = new CalLength(population.getIndividual(i));
				population.getIndividual(i).setLength(cl.getLength());
				calFitness = new CalFitness(population.getIndividual(i), givenLength);
				population.setFitnessOfIndividual(i, calFitness.calFitness());
			}
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
	
	private ArrayList<Point> makeNewSteinerPointList(int maxTerminalsIndex) {
		int numOfSteiner = makeRandom(population.getIndividual(maxTerminalsIndex).getNumOfTerminal());
		HashSet<Point> newSteinerPointHash = new HashSet<Point>();
		ArrayList<Point> newSteinerPointList = new ArrayList<Point>();
		for(int i = 0; i < numOfSteiner; i++) {
			Point p = new Point(inOfConvexHallSteinerPointsList.get(makeRandom(inOfConvexHallSteinerPointsList.size())).getX(), inOfConvexHallSteinerPointsList.get(makeRandom(inOfConvexHallSteinerPointsList.size())).getY());
			if(newSteinerPointHash.add(p))
				newSteinerPointList.add(p);
		}
		return newSteinerPointList;
	}
	
	private void makeInOfConvexHallSteinerPointList(int maxTerminalsIndex) {
		makeTerminals(maxTerminalsIndex);
		UsingDistance ud = new UsingDistance(terminals);
		int dist[][] = ud.preprocessingDist();
		
		boolean[] selected = new boolean[terminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[terminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0; int selectedIndex = 0; selected[0] = true;
		
		for(int k = 1; k < terminals.length; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < terminals.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
				}
			}
			
			int min = Integer.MAX_VALUE;
			
			// 가장 가까운 터미널 선택
			for(int i = 0; i < terminals.length; i++) {
				if(min > minDist[i] && selected[i] == false) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			if(k >= 2) {
				Point p = closedPairPoint(selectedIndex, terminals[selectedIndex], terminals);
				if(p != null)
					if(inOfConvexHallSteinerPoints.add(p))
						inOfConvexHallSteinerPointsList.add(p);
			}
			
			if(res + min >= givenLength)
				break;

			res = res + min;
			selected[selectedIndex] = true;
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
		// System.out.println("기준 Point: " + pointPair[0]);
		// System.out.println("1번쨰 Pair [ index = " + minDistIndex + ", minDist = " + minDist + ", Point = " + terminalArr[minDistIndex]);	
		minDist = Integer.MAX_VALUE;
		int minDistIndexTwo = 0;
		
		for(int i = 0; i < terminalArr.length; i++) {
			int dist = getDist(p, terminalArr[i]);
			if(minDist > dist && i != indexOfP && i != minDistIndex) {
				minDist = dist;
				minDistIndexTwo = i;
			}
		}
		
		// System.out.println("2번쨰 Pair [ index = " + minDistIndexTwo + ", minDist = " + minDist + ", Point = " + terminalArr[minDistIndexTwo]);
		
		pointPair[1] = terminalArr[minDistIndex];
		pointPair[2] = terminalArr[minDistIndexTwo];
		
		// System.out.println(Arrays.toString(pointPair));
		
		Point newSteinerPoint = middlePoint(pointPair);
		
		boolean comparedRes = false;
		if(InputDataProcess.getInputDataProcess().isSteinerOverlap(newSteinerPoint) == true) {
			// System.out.println(newSteinerPoint + "는 터미널과 겹친다.");
			comparedRes = true;
		}
		
		if(comparedRes == true) {
			return null;
		}
		else return newSteinerPoint;
		
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

	private void makeTerminals(int maxTerminalsIndex) {
		int NumOfMaxTerminals = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(population.getIndividual(maxTerminalsIndex).getTerminalStatus()[i] == true) {
				NumOfMaxTerminals++;
			}
		}
		terminals = new Point[NumOfMaxTerminals];
		int index = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(population.getIndividual(maxTerminalsIndex).getTerminalStatus()[i] == true) {
				terminals[index] = new Point(InputDataProcess.getInputDataProcess().getTerminalCoor(i).getX(), InputDataProcess.getInputDataProcess().getTerminalCoor(i).getY());
				index++;
			}
		}
	}
	
	private int findMaximumTerminalIndex() {
		int maxTerminalsIndex = 0;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			if(maxTerminalsIndex < population.getIndividual(i).getNumOfTerminal()) {
				maxTerminalsIndex = i;
			}
		}
		return maxTerminalsIndex;
	}
	

	/*
	private void makeConvexHallList(int maxTerminalsIndex) {
		int NumOfMaxTerminals = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(population.getIndividual(maxTerminalsIndex).getTerminalStatus()[i] == true) {
				NumOfMaxTerminals++;
			}
		}
		terminals = new Point[NumOfMaxTerminals];
		int index = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(population.getIndividual(maxTerminalsIndex).getTerminalStatus()[i] == true) {
				terminals[index] = new Point(InputDataProcess.getInputDataProcess().getTerminalCoor(i).getX(), InputDataProcess.getInputDataProcess().getTerminalCoor(i).getY());
				index++;
			}
		}
		Convexhall ch = new Convexhall(terminals);
		ConvexHallList = ch.getConvexhallList();
	}

	private int findMaximumTerminalIndex() {
		int maxTerminalsIndex = 0;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			if(maxTerminalsIndex < population.getIndividual(i).getNumOfTerminal()) {
				maxTerminalsIndex = i;
			}
		}
		return maxTerminalsIndex;
	}

	private ArrayList<Point> makeNewSteinerPointList(int maxTerminalsIndex) {
		int numOfSteiner = makeRandom(population.getIndividual(maxTerminalsIndex).getNumOfTerminal());
		HashSet<Point> newSteinerPointHash = new HashSet<Point>();
		ArrayList<Point> newSteinerPointList = new ArrayList<Point>();
		for(int i = 0; i < numOfSteiner; i++) {
			Point p = inOfConvexHallSteinerPointsList.get(makeRandom(inOfConvexHallSteinerPointsList.size()));
			if(newSteinerPointHash.add(p))
				newSteinerPointList.add(p);
		}
		return newSteinerPointList;
	}
	
	public void makeHananGrid() {
		hanan_vertical = new ArrayList<>();
		hanan_horizental = new ArrayList<>();
		
		for(Point p : terminals) {
			int x = p.getX();
			int y = p.getY();
			if(!hanan_vertical.contains(x))
				hanan_vertical.add(x);
			if(!hanan_horizental.contains(y))
				hanan_horizental.add(y);
		}
		
		Collections.sort(hanan_horizental);
		Collections.sort(hanan_vertical);
		
		makeOutOfConvexHallSteinerPoints();
	}
	
	private int makeRandom(int limit) {
		int r = (int) (limit * Math.random());
		return r;
	}
	
	private double makeRandom() {
		double r = Math.random();
		return r;
	}
	
	private void makeOutOfConvexHallSteinerPoints() {
		Convexhall ch = new Convexhall();
		Point[] convexhallList = ch.getConvexhallList();
		
		for(int v = 0; v < hanan_vertical.size(); v++) {
			for(int h = 0; h < hanan_horizental.size(); h++) {
				Point p = new Point(hanan_vertical.get(v), hanan_horizental.get(h));
				if(isOutofConvexhall(convexhallList, p) == false)
					if(SPTerminalOverlap(p) == false) {
						inOfConvexHallSteinerPointsList.add(p);
						inOfConvexHallSteinerPoints.add(p);
					}
			}
		}
	}
	
	private boolean SPTerminalOverlap(Point steinerPoint) { // true면 스타이너 포인트가 터미널과 겹친다.
		boolean res = false;
		for(int i = 0; i < terminals.length; i++) {
			if(comparePoint(steinerPoint, terminals[i]) == true) {
				res = true;
			}
		}
		return res;
	}
	
	private boolean comparePoint(Point p1, Point p2) {
		boolean res = false;
		
		if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
			res = true;
		}
		
		return res;
	}

	private boolean isOutofConvexhall(Point[] convexhallList, Point point) {
		int cnt = 0;
		
		// System.out.println("============ " + point + "의 교차 회수 확인  ==============");
		for(int i = 0; i < convexhallList.length; i++) {
			if(i == convexhallList.length - 1) {
				// System.out.println(convexhallList[i] + ", " +  convexhallList[0]);
				if(isCross(convexhallList[i], convexhallList[0], point) == true)
					cnt++;
			}
			else {
				// System.out.println(convexhallList[i] + ", " +  convexhallList[i+1]);
				if(isCross(convexhallList[i], convexhallList[i+1], point) == true)
					cnt++;
			}
		}

		// System.out.println("	" + point + "의 교차 회수(홀수이면 컨벡스 홀 안에 있다!): " + cnt);
		if(cnt % 2 == 1) // 해당 포인트는 컨벡스 홀 안에 있다.
			return false;
		else // 해당 포인트는 컨벡스 홀 바깥에 있다.
			return true;
	}
	
	private boolean isCross(Point line1Origin, Point line2Origin, Point pOrigin) {
		Point line1 = new Point(line1Origin.getX(), line1Origin.getY());
		Point line2 = new Point(line2Origin.getX(), line2Origin.getY());
		Point p = new Point(pOrigin.getX(), pOrigin.getY());
		
		if(line2.getY() < line1.getY()) {
			Point box = line1;
			line1 = line2;
			line2 = box;
		}
		
		//System.out.println("	" + line1 + "와 " + line2 + "로 이뤄진 선분과 " + p +  "의 교차점이 있는가?");
		double py = (double)p.getY() + 0.00001d;
		
		if(py > line1.getY() && py < line2.getY()) {
			double crossPointX = ((double)line2.getX() - (double)line1.getX()) * (py - (double)line1.getY()) 
					/ ((double)line2.getY() - (double)line1.getY()) + (double)line1.getX();
			// System.out.println("	" + line1 + "과 " + line2 + "의 교차점: " + crossPointX);
			if(crossPointX > p.getX())
				return true;
		}
		
		return false;
	
	}
	*/
	
}
