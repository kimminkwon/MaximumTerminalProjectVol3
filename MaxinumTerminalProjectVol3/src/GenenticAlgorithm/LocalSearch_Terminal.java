package GenenticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;

import Populations.ConstOfGA;
import Populations.Individual;
import Populations.Point;
import PopulationsInfo.InputDataProcess;
import Preprocess.UsingDistance;

public class LocalSearch_Terminal {
	private int givenLength;
	private Individual calIndividual;
	private Point[] totalTerminals;
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
		UsingDistance ud = new UsingDistance(totalTerminals);
		this.dist = ud.preprocessingDist();
		
		int res = 0;
		
		System.out.println(Arrays.toString(totalTerminals));
		localSearchPartOne(selected, minDist);
		
		
		
	}
	
	// Part1. 선택된 터미널과 스타이너 포인트만 연결한다.
	private int localSearchPartOne(boolean[] selected, int[] minDist) {
		boolean[] possible = makePossibleArr();
		
		int selectedIndex = selectedTrueIndex();
		System.out.print("Started Index: " + selectedIndex + ", 0 -->");
		selected[selectedIndex] = true;
		
		int length = calIndividual.getNumOfSteinerPoint() + calIndividual.getNumOfTerminal();
		int[] startTerminals = new int[ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfSteinerPoint()];
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
			
			System.out.println("selected 배열" + Arrays.toString(selected));
			System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
			System.out.println("minDist 배열" + Arrays.toString(minDist));
			System.out.println("Degree배열" + Arrays.toString(degree));

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
				System.out.println(selectedIndex + ", " + (res+min) + "로 길이 초과 발생");
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
			System.out.print(selectedIndex + ", " + res + " --> \n");
			
			
		}
		
		// 마지막 선택에 대한 거리 값 업데이트
		for(int i = 0; i < totalTerminals.length; i++) {
			if(minDist[i] > dist[selectedIndex][i]) {
				minDist[i] = dist[selectedIndex][i];
				startTerminals[i] = selectedIndex;
			}
		}
		
		System.out.println("selected 배열" + Arrays.toString(selected));
		System.out.println("startTerminals 배열" + Arrays.toString(startTerminals));
		System.out.println("minDist 배열" + Arrays.toString(minDist));
		System.out.println("Degree배열" + Arrays.toString(degree));

		
		
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
}
