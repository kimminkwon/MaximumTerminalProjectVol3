package Populations;
import java.util.*;

public class Individual {
	private boolean[] terminalStatus;
	private ArrayList<Point> steinerPointStatus;
	private int numOfTerminal;
	private int numOfSteinerPoint;
	
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


	@Override
	public String toString() {
		return "  - terminalStatus=" + Arrays.toString(terminalStatus) + "\n    steinerPointStatus="
				+ steinerPointStatus + "\n    numOfTerminal=" + numOfTerminal + ", numOfSteinerPoint=" + numOfSteinerPoint
				+ "\n";
	}
}
