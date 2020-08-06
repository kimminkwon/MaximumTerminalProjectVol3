package PopulationsInfo;
import java.util.ArrayList;
import java.util.Arrays;

import Populations.*;
import Preprocess.UsingDistance;

public class CalLength {
	private Point[] totalTerminals;
	private int[][] dist;
	private Individual calIndividual;
	private int[] degree;
		 
	public CalLength(Individual individual) {
		this.calIndividual = individual;
		boolean[] terminalbox = calIndividual.getTerminalStatus();
		ArrayList<Point> steinerBox = calIndividual.getSteinerPointStatus();
		this.totalTerminals = new Point[calIndividual.getNumOfTerminal() + calIndividual.getNumOfSteinerPoint()];
		
		InputDataProcess idp = InputDataProcess.getInputDataProcess();
		
		int cnt = 0;
		
		for(int i = 0; i < terminalbox.length; i++) {
			if(terminalbox[i] == true) {
				totalTerminals[cnt] = idp.getTerminalCoor(i);
				cnt++;
			}
		}
		for(Point p : steinerBox) {
			totalTerminals[cnt] = p;
			cnt++;
		}
		// System.out.println(Arrays.toString(totalTerminals));
	}
	
	public int getLength() {
		UsingDistance ud = new UsingDistance(totalTerminals);
		this.dist = ud.preprocessingDist();
		
		int[] startTerminals = new int[calIndividual.getNumOfTerminal() + calIndividual.getNumOfSteinerPoint()];
		this.degree = new int[calIndividual.getNumOfTerminal() + calIndividual.getNumOfSteinerPoint()];
		boolean[] selected = new boolean[totalTerminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[totalTerminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0;
		
		// 길이 계산 연산 시작
		// 시작 터미널 고르기
		int selectedIndex = 0;
		selected[0] = true;
		Arrays.fill(degree, 0);
		
		// Prim Algorithm
		for(int k = 1; k < totalTerminals.length; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < totalTerminals.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
					startTerminals[i] = selectedIndex;
				}
			}
			
			int min = Integer.MAX_VALUE;
			
			// 가장 가까운 터미널 선택
			for(int i = 0; i < totalTerminals.length; i++) {
				if(min > minDist[i] && selected[i] == false) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			if(selectedIndex >= calIndividual.getNumOfTerminal()) { // 이번에 선택한게 스타이너 포인트라면?
				degree[selectedIndex]++; // 스타이너 포인트의 디그리 증가	
			}
			if(startTerminals[selectedIndex] >= calIndividual.getNumOfTerminal()) { // 이번 선택한 터미널과 연결된 터미널이 스타이너 포인트라면?
				degree[startTerminals[selectedIndex]]++; // 그것도 디그리 증가시킴
			}

			res = res + min;
			selected[selectedIndex] = true;
		}
		calIndividual.setDegreeOfSteinerPoint(this.degree);
		return res;
	}
}
