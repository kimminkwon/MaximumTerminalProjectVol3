package Populations;
import java.util.*;

public class Individual implements Cloneable {
	private boolean[] terminalStatus;
	private ArrayList<Point> steinerPointStatus;
	private int numOfTerminal;
	private int numOfSteinerPoint;
	private int[] degreeOfSteinerPoint;
	private int length;
	
	public Individual(boolean[] terminalStatus, ArrayList<Point> steinerPointStatus) {
		this.terminalStatus = new boolean[ConstOfGA.NUMOFTERMINALS];
		int cnt = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			this.terminalStatus[i] = terminalStatus[i];
			if(terminalStatus[i] == true) cnt++;
		}
		this.numOfTerminal = cnt;
		this.steinerPointStatus = steinerPointStatus;
		this.numOfSteinerPoint = steinerPointStatus.size();
		this.length = 0;
	}

	public void setNumber() {
		int cnt = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			this.terminalStatus[i] = terminalStatus[i];
			if(terminalStatus[i] == true) cnt++;
		}
		this.numOfTerminal = cnt;
		this.numOfSteinerPoint = steinerPointStatus.size();
	}

	public int getNumOfTerminal() {
		return numOfTerminal;
	}


	public int getNumOfSteinerPoint() {
		return numOfSteinerPoint;
	}


	public boolean[] getTerminalStatus() {
		return terminalStatus;
	}

	public void setTerminalStatus(boolean[] terminalStatus) {
		int cnt = 0;
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			this.terminalStatus[i] = terminalStatus[i];
			if(terminalStatus[i] == true) cnt++;
		}
		this.numOfTerminal = cnt;
	}

	public ArrayList<Point> getSteinerPointStatus() {
		return steinerPointStatus;
	}

	public void setSteinerPointStatus(ArrayList<Point> steinerPointStatus) {
		this.steinerPointStatus = steinerPointStatus;
		this.numOfSteinerPoint = steinerPointStatus.size();
	}

	public int[] getDegreeOfSteinerPoint() {
		return degreeOfSteinerPoint;
	}


	public void setDegreeOfSteinerPoint(int[] degreeOfSteinerPoint) {
		this.degreeOfSteinerPoint = degreeOfSteinerPoint;
	}
	
	public int getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "  - terminalStatus=" + Arrays.toString(this.terminalStatus) + "\n    steinerPointStatus="
				+ this.steinerPointStatus + "\n    numOfTerminal=" + numOfTerminal + ", numOfSteinerPoint=" + numOfSteinerPoint + ", length=" + length
				+ "\n    degreeOfSteiner=" + Arrays.toString(degreeOfSteinerPoint);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
