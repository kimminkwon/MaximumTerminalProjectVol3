package GenenticAlgorithm;

import java.util.*;
import Populations.*;
import PopulationsInfo.*;
import Preprocess.*;

public class Adjustment {
	private int givenLength;
	private int[] terminalNumHash;
	private Point[] totalTerminals;
	private int[][] dist;
	private Individual calIndividual;
	
	public Adjustment(Individual individual, int givenLength) {
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
		// System.out.println("steinerBox: " + steinerBox);
		for(Point p : steinerBox) {
			// System.out.println(p + "를 저장");
			totalTerminals[cnt] = p;
			cnt++;
		}
		// System.out.println("완성된 TotalTermianls = " + Arrays.toString(totalTerminals));
	}
	
	public void adjustment() {
		UsingDistance ud = new UsingDistance(totalTerminals);
		this.dist = ud.preprocessingDist();
		
		boolean[] selected = new boolean[totalTerminals.length];
		Arrays.fill(selected, false);
		int[] minDist = new int[totalTerminals.length];
		Arrays.fill(minDist, Integer.MAX_VALUE);
		int res = 0;
		
		// 조정 연산 시작
		// 시작 터미널 고르기
		int selectedIndex = ud.makeRandom(totalTerminals.length - 1);
		// System.out.print("Started Index: " + selectedIndex + ", 0 -->");
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
				// System.out.println(selectedIndex + ", " + (res+min) + "로 길이 초과 발생");
				break;
			}

			res = res + min;
			selected[selectedIndex] = true;
			// System.out.print(selectedIndex + ", " + res + " -->");
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
		// System.out.println("Adjusted Individual...");
		// System.out.println(calIndividual);
	}
}
