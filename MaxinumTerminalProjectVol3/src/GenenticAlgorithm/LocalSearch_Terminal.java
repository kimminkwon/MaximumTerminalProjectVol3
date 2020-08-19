package GenenticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;


import Populations.ConstOfGA;
import Populations.Individual;
import Populations.Point;
import PopulationsInfo.CalLength;
import PopulationsInfo.InputDataProcess;
import Preprocess.UsingDistance;

public class LocalSearch_Terminal {
	private int givenLength;
	private Individual calIndividual;
	private Point[] totalTerminals;
	private Point[] totalTerminalsPlusSteiner;
	private InputDataProcess idp = null;
	private int[][] dist;
	private int[] degree;
	
	public LocalSearch_Terminal(Individual individual, int givenLength) {
		calIndividual = individual;
		this.givenLength = givenLength;
		idp = InputDataProcess.getInputDataProcess();
		makeTotalTerminals();
	}
	
	public void localSearch() {
		boolean[] selected = new boolean[totalTerminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[totalTerminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int[] startTerminals = new int[ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()];
		UsingDistance ud = new UsingDistance(totalTerminals);
		this.dist = ud.preprocessingDist();
		
		int res = 0;
		
		//System.out.println(Arrays.toString(totalTerminals));
		res = localSearchPartOne(selected, minDist, startTerminals);
		
//		System.out.println("selected 배열" + Arrays.toString(selected));
//		System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
//		System.out.println("minDist 배열" + Arrays.toString(minDist));
//		System.out.println("Degree배열" + Arrays.toString(degree));
		
		ArrayList<Point> SteinerFlag = new ArrayList<Point>();
		res = localSearchPartTwo(selected, minDist, startTerminals, res, SteinerFlag);
//		System.out.println("최종 res = " + res + ", 추가할 스타이너포인트의 기준: " + SteinerFlag);
	
		ArrayList<Point> newSteinerPoints = steinerAdd(SteinerFlag, selected);
//		System.out.println("newSteinerPoints : " + newSteinerPoints);
	
		makeNewIndividual(selected, newSteinerPoints);
		CalLength calLength = new CalLength(calIndividual);
		int length = calLength.getLength();
		calIndividual.setLength(length);
//		System.out.println("==============최종 형태=============");
//		System.out.println(calIndividual);
//		System.out.println("length: " + length);
	}
	
	// Part4. 토탈 터미널을 해로 변환한다.
	private void makeNewIndividual(boolean[] selected, ArrayList<Point> newSteinerPoints) {
		boolean[] terminalStatus = new boolean[totalTerminals.length + newSteinerPoints.size()];
		ArrayList<Point> steinerPointStatus = new ArrayList<Point>();
		
		int cnt = 0;
		// 1. 터미널 복사
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			terminalStatus[i] = selected[i];
			cnt++;
		}
		// 2. 기존 스타이너 포인트 추가
		for(int i = ConstOfGA.NUMOFTERMINALS; i < totalTerminals.length ; i++) {
			steinerPointStatus.add(totalTerminals[i]);
			cnt++;
		}
		// 3. 새로운 스타이너 포인트 추가
		for(Point p : newSteinerPoints) {
			steinerPointStatus.add(new Point(p.getX(), p.getY()));
		}
		
		calIndividual.setTerminalStatus(terminalStatus);
		calIndividual.setSteinerPointStatus(steinerPointStatus);	
	}
	
	// Part3. Steiner Point를 다시 추가한다.
	private ArrayList<Point> steinerAdd(ArrayList<Point> SteinerFlag, boolean[] selected) {
		ArrayList<Point> newSteinerPoints = new ArrayList<Point>();
		int numOfSelectedTerminal = 0;
		
		// TotalTerminal을 선택된 애들만 실제 Point 값으로 변환 
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(selected[i] == true) {
				numOfSelectedTerminal++;
			}
		}
		
		Point[] selectedTerminals = new Point[numOfSelectedTerminal];
		
		int cntOfTerminals = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			if(selected[i] == true) {
				selectedTerminals[cntOfTerminals] = totalTerminals[i];
				cntOfTerminals++;
			}
		}
		
		// 해당 선택된 터미널 배열과 새로운 스타이너 포인트의 기준을 사용해 새로운 스타이너 포인트 생성
		for(int k = 0; k < SteinerFlag.size(); k++) {
			Point newSteinerPoint = closedPairPoint(selectedTerminals, SteinerFlag.get(k));
			if(newSteinerPoint != null) {
				newSteinerPoints.add(newSteinerPoint);
			}
		}
		
		return newSteinerPoints;
		
//		this.totalTerminalsPlusSteiner = new Point[totalTerminals.length + newSteinerPoints.size()];
//		int cnt = 0;
//		for(int i = 0; i < totalTerminals.length; i++) {
//			totalTerminalsPlusSteiner[cnt] = totalTerminals[i];
//			cnt++;
//		}
//		
//		for(Point p : newSteinerPoints) {
//			totalTerminalsPlusSteiner[cnt] = p;
//			cnt++;
//		}
	}
	
	private Point closedPairPoint(Point[] selectedTerminals, Point flagPoint) {
		int minDist = Integer.MAX_VALUE;
		int minDistIndex = 0;
		
		Point[] pointPair = new Point[3];
		pointPair[0] = flagPoint;
		
		for(int i = 0; i < selectedTerminals.length; i++) {
			int dist = getDist(flagPoint, selectedTerminals[i]);
			if(minDist > dist && comparePoint(flagPoint, selectedTerminals[i]) == false) {
				minDist = dist;
				minDistIndex = i;
			}
		}
//		System.out.println("기준 Point: " + flagPoint);
//		System.out.println("1번쨰 Pair [ index = " + minDistIndex + ", minDist = " + minDist + ", Point = " + selectedTerminals[minDistIndex]);
//		
		minDist = Integer.MAX_VALUE;
		int minDistIndexTwo = 0;
		
		for(int i = 0; i < selectedTerminals.length; i++) {
			int dist = getDist(flagPoint, selectedTerminals[i]);
			if(minDist > dist && comparePoint(flagPoint, selectedTerminals[i]) == false && comparePoint(selectedTerminals[minDistIndex], selectedTerminals[i]) == false) {
				minDist = dist;
				minDistIndexTwo = i;
			}
		}
		
//		System.out.println("2번쨰 Pair [ index = " + minDistIndexTwo + ", minDist = " + minDist + ", Point = " + selectedTerminals[minDistIndexTwo]);
		
		
		pointPair[1] = selectedTerminals[minDistIndex];
		pointPair[2] = selectedTerminals[minDistIndexTwo];
		
//		System.out.println(Arrays.toString(pointPair));
		
		Point newSteinerPoint = middlePoint(pointPair);
		
		boolean comparedRes = false;
		for(int i = 0; i < pointPair.length; i++) {
			if(idp.isSteinerOverlap(newSteinerPoint) == true) {
//				System.out.println(newSteinerPoint + "는 터미널과 겹친다.");
				comparedRes = true;
				break;
			}
		}
		
		if(comparedRes == true) {
			return null;
		}
		
		else return newSteinerPoint;
	}
	
	// Part2, 4. 추가적인 연결을 만든다. 이때 일정 확률로 추가할 스타이너 포인트의 개수를 증가시킨다.
	private int localSearchPartTwo(boolean[] selected, int[] minDist, int[] startTerminals, int res, ArrayList<Point> SteinerFlag) {
//		System.out.println("PART 2!!");
		while(true) {
//			System.out.println("selected 배열" + Arrays.toString(selected));
//			System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
//			System.out.println("minDist 배열" + Arrays.toString(minDist));
//			System.out.println("Degree 배열" + Arrays.toString(degree));
			
			// 가장 가까운 터미널 선택
			int min = Integer.MAX_VALUE;
			int selectedIndex = -1;
			for(int i = 0; i < totalTerminals.length; i++) {
				if(min > minDist[i] && selected[i] == false) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			selected[selectedIndex] = true;		
			int resbox = calculateIndividualLength(selected);
			
			// System.out.print(selectedIndex + ", " + res + " --> \n");
			
			// 거리 값 업데이트
			if(resbox >= givenLength) {
				// System.out.println("거리값 초과!");
				selected[selectedIndex] = false;
				break;
			}
			
			res = resbox;
			
			// 추가할 스타이너 포인트의 개수, 터미널을 선택했을 때 한정
			if(makeRandom() < ConstOfGA.PROBSTEINERADDPARTTWO && selectedIndex < ConstOfGA.NUMOFTERMINALS)
				SteinerFlag.add(totalTerminals[selectedIndex]);
			
			if(selectedIndex >= ConstOfGA.NUMOFTERMINALS) { // 이번에 선택한게 스타이너 포인트라면?
				degree[selectedIndex]++; // 스타이너 포인트의 디그리 증가	
			}
			
			if(startTerminals[selectedIndex] >= ConstOfGA.NUMOFTERMINALS) { // 이번 선택한 터미널과 연결된 터미널이 스타이너 포인트라면?
				degree[startTerminals[selectedIndex]]++; // 그것도 디그리 증가시킴
			}
		}
		
//		System.out.println("selected 배열" + Arrays.toString(selected));
//		System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
//		System.out.println("minDist 배열" + Arrays.toString(minDist));
//		System.out.println("Degree배열" + Arrays.toString(degree));
		
		return res;
	}
	
	private int calculateIndividualLength(boolean[] selected) {
		boolean[] terminalStatus = new boolean[ConstOfGA.NUMOFTERMINALS];
		ArrayList<Point> steinerPointStatus = new ArrayList<>();
		
		int i = 0;
		for(;i < terminalStatus.length; i++) {
			terminalStatus[i] = selected[i];
		}
		for(; i < totalTerminals.length; i++) {
			steinerPointStatus.add(totalTerminals[i]);
		}
		CalLength calLength = new CalLength(new Individual(terminalStatus, steinerPointStatus));
		return calLength.getLength();
	}

	// Part1. 선택된 터미널과 스타이너 포인트만 연결한다.
	private int localSearchPartOne(boolean[] selected, int[] minDist, int[] startTerminals) {
		boolean[] possible = makePossibleArr();
		
		int selectedIndex = selectedTrueIndex();
		// System.out.print("Started Index: " + selectedIndex + ", 0 -->");
		selected[selectedIndex] = true;
		
		int length = calIndividual.getNumOfSteinerPoint() + calIndividual.getNumOfTerminal();
		this.degree = new int[ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()];
		
		Arrays.fill(degree, 0);
		
		int res = 0;
		for(int k = 1; k < length; k++) {
			// 거리 값 업데이트: 모든 터미널에 대해서
			for(int i = 0; i < totalTerminals.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
					startTerminals[i] = selectedIndex;
				}
			}
			
//			System.out.println("selected 배열" + Arrays.toString(selected));
//			System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
//			System.out.println("minDist 배열" + Arrays.toString(minDist));
//			System.out.println("Degree배열" + Arrays.toString(degree));

			// 가장 가까운 터미널 선택
			int min = Integer.MAX_VALUE;
			for(int i = 0; i < totalTerminals.length; i++) {
				if(min > minDist[i] && selected[i] == false && possible[i] == true) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			// 거리 값 업데이트
			if(res + min > givenLength) {
				// System.out.println(selectedIndex + ", " + (res+min) + "로 길이 초과 발생");
				break;
			}
			
			if(selectedIndex >= ConstOfGA.NUMOFTERMINALS) { // 이번에 선택한게 스타이너 포인트라면?
				degree[selectedIndex]++; // 스타이너 포인트의 디그리 증가	
			}
			
			if(startTerminals[selectedIndex] >= ConstOfGA.NUMOFTERMINALS) { // 이번 선택한 터미널과 연결된 터미널이 스타이너 포인트라면?
				degree[startTerminals[selectedIndex]]++; // 그것도 디그리 증가시킴
			}
			
			res = res + min;
			selected[selectedIndex] = true;		
			//System.out.print(selectedIndex + ", " + res + " --> \n");
		}
		
		// 마지막 선택에 대한 거리 값 업데이트
		for(int i = 0; i < totalTerminals.length; i++) {
			if(minDist[i] > dist[selectedIndex][i]) {
				minDist[i] = dist[selectedIndex][i];
				startTerminals[i] = selectedIndex;
			}
		}
		
		return res;
	}
	
	private int selectedTrueIndex() {
		int length = calIndividual.getNumOfSteinerPoint() + calIndividual.getNumOfTerminal();
		
		int[] indexArr = new int[length];
		int cnt = 0;
		for(int i = 0; i < totalTerminals.length; i++) {
			if(i >= ConstOfGA.NUMOFTERMINALS) {
				indexArr[cnt] = i;
				cnt++;
			}
			else {
				if(calIndividual.getTerminalStatus()[i] == true) {
					indexArr[cnt] = i;
					cnt++;
				}
			}
		}
			
		return indexArr[makeRandom(length)];
	}

	private boolean[] makePossibleArr() {
		boolean[] possible = new boolean[totalTerminals.length];
		
		for(int i = 0; i < totalTerminals.length; i++) {
			if(i >= ConstOfGA.NUMOFTERMINALS) {
				possible[i] = true;
			}
			else {
				if(calIndividual.getTerminalStatus()[i] == true) {
					possible[i] = true;
				}
			}
		}
		
		return possible;
	}
	
	private void makeTotalTerminals() {
		// make TotalTerminals
		this.totalTerminals = new Point[ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()];
		
		int cnt = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			totalTerminals[cnt] = idp.getTerminalCoor(i);
			cnt++;			
		}
				
		for(Point p : calIndividual.getSteinerPointStatus()) {
			totalTerminals[cnt] = p;
			cnt++;
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
}
