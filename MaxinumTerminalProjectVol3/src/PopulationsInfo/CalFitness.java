package PopulationsInfo;
import java.util.ArrayList;
import java.util.Arrays;

import Populations.*;

public class CalFitness {
	private int givenLength;
	private int[] terminalNumHash;
	private Point[] totalTerminals;
	private int[][] dist;
	private Individual calIndividual;
		 
	public CalFitness(Individual individual, int givenLength) {
		this.givenLength = givenLength;
		this.calIndividual = individual;
		boolean[] terminalbox = calIndividual.getTerminalStatus();
		terminalNumHash = new int[calIndividual.getNumOfTerminal()];
		ArrayList<Point> steinerBox = calIndividual.getSteinerPointStatus();
		this.totalTerminals = new Point[calIndividual.getNumOfTerminal() + calIndividual.getNumOfSteinerPoint()];
		
		InputDataProcess idp = InputDataProcess.getInputDataProcess();
		
		int cnt = 0;
		
		for(int i = 0; i < terminalbox.length; i++) {
			if(terminalbox[i] == true) {
				terminalNumHash[cnt] = i;
				totalTerminals[cnt] = idp.getTerminalCoor(i);
				cnt++;
			}
		}
		for(Point p : steinerBox) {
			totalTerminals[cnt] = p;
			cnt++;
		}
		System.out.println(Arrays.toString(totalTerminals));
	}
	
	public int getLength() {
		preprocessingDist();
		
		boolean[] selected = new boolean[totalTerminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[totalTerminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0;
		
		// 조정 연산 시작
		// 시작 터미널 고르기
		int selectedIndex = 0;
		selected[0] = true;
		
		// Prim Algorithm
		for(int k = 1; k < totalTerminals.length; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < totalTerminals.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
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
	
			res = res + min;
			selected[selectedIndex] = true;
			
		}
		return res;
	}
	

	public void adjustment() {
		preprocessingDist();
		
		boolean[] selected = new boolean[totalTerminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[totalTerminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0;
		
		// 조정 연산 시작
		// 시작 터미널 고르기
		int selectedIndex = makeRandom(totalTerminals.length);
		System.out.print("Started Index: " + selectedIndex + ", 0 -->");
		selected[selectedIndex] = true;
		
		// Prim Algorithm
		for(int k = 1; k < totalTerminals.length; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < totalTerminals.length; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
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

			
			// 거리 값 업데이트
			if(res + min > givenLength) {
				System.out.println(selectedIndex + ", " + (res+min) + "로 길이 초과 발생");
				break;
			}

			res = res + min;
			selected[selectedIndex] = true;
			System.out.print(selectedIndex + ", " + res + " -->");
			
		}
		
		// 선택되지 않은 터미널 + 스타이너 포인트를 삭제
		for(int i = 0; i < totalTerminals.length; i++) {
			if(selected[i] == false) { // 선택되지 않은 터미널
				if(i < calIndividual.getNumOfTerminal()) { // 터미널
					calIndividual.getTerminalStatus()[terminalNumHash[i]] = false;
				}
				else { // 스타이너 포인트
					calIndividual.getSteinerPointStatus().remove(totalTerminals[i]);
				}
			}
		}

		calIndividual.setNumber();
		System.out.println("Adjusted Individual...");
		System.out.println(calIndividual);
	}
	
	private void preprocessingDist() {
		dist = new int[totalTerminals.length][totalTerminals.length];
		
		for(int i = 0; i < totalTerminals.length; i++) {
			for(int j = i; j < totalTerminals.length; j++) {
				int d = getDist(totalTerminals[i], totalTerminals[j]);
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
	
	private int makeRandom(int limit) {
		int r = (int)(limit * Math.random());
		return r;
	}
}
