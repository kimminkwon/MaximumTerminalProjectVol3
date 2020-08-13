package GenenticAlgorithm;

import java.util.ArrayList;
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
		steinerDelete();
		System.out.println("delete 연산 후 totalTerminals: " + totalTerminals);
		UsingDistance usingDist = new UsingDistance(totalTerminals);
		this.dist = usingDist.preprocessingDist();
		
	}
	
	private void steinerDelete() {
		ArrayList<Integer> deleteIndexs = new ArrayList<Integer>();

		int cnt = 0;
		Iterator<Point> iter = totalTerminals.iterator();
		while (iter.hasNext()) {
			if (cnt >= ConstOfGA.NUMOFTERMINALS) {
				System.out.println("cnt : " + cnt);
				System.out.println("SteinerDegree: " + calIndividual.getDegreeOfSteinerPoint()[cnt - ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfTerminal()]);
				if(calIndividual.getDegreeOfSteinerPoint()[cnt - ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfTerminal()] == 1) {
					System.out.println((cnt - ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfTerminal()) + "번째 SteinerPoint Degree == 1");
					if(makeRandom() < ConstOfGA.PROBSTEINERDELETE) { 
						System.out.println("삭제할 것! : " + totalTerminals.get(cnt - ConstOfGA.NUMOFTERMINALS + calIndividual.getNumOfTerminal()));
						iter.remove();
					}
				}
			}
			cnt++;
		}
	}
	
	private void steinerAdd() {
		
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
