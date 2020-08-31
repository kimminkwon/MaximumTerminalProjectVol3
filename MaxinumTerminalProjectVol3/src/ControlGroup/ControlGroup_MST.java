package ControlGroup;

import java.util.Arrays;

import Populations.ConstOfGA;
import PopulationsInfo.InputDataProcess;
import Preprocess.UsingDistance;

public class ControlGroup_MST implements ControlGroup {

	@Override
	public int getControlGroupValue(int givenLength) {
		int dist[][] = getDist();
		int res = Integer.MIN_VALUE;
		
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			int valueOfi = mstValueOfStartedIndex(i, dist, givenLength);
			if(valueOfi > res)
				res = valueOfi;
		}
		
		return res;
	}
	
	// startedIndex에서 출발한 MCTR using Rectilinear edge
	private int mstValueOfStartedIndex(int startedIndex, int dist[][], int givenLength) {
		int res = 0;
		int cnt = 0;
		boolean[] selected = new boolean[ConstOfGA.NUMOFTERMINALS];
		int[] minDist = new int[ConstOfGA.NUMOFTERMINALS];
		Arrays.fill(selected, false);
		Arrays.fill(minDist, Integer.MAX_VALUE);
		

		int selectedIndex = startedIndex;
		selected[startedIndex] = true;
		cnt++;
		
		// Prim Algorithm
		for(int k = 1; k < ConstOfGA.NUMOFTERMINALS; k++) {
			// 거리 값 업데이트
			for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
				if(minDist[i] > dist[selectedIndex][i]) {
					minDist[i] = dist[selectedIndex][i];
				}
			}
					
			int min = Integer.MAX_VALUE;
					
			// 가장 가까운 터미널 선택
			for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
				if(min > minDist[i] && selected[i] == false) {
					min = minDist[i];
					selectedIndex = i;
				}
			}
			
			if(res + min > givenLength) {
				break;
			}
					
			res = res + min;
			selected[selectedIndex] = true;
			cnt++;
		}
		
		return cnt;
	}
	
	private int[][] getDist() {
		UsingDistance ud = new UsingDistance(InputDataProcess.getInputDataProcess().getTerminals());
		int dist[][] = ud.preprocessingDist();
		
		return dist;
	}
	
}
