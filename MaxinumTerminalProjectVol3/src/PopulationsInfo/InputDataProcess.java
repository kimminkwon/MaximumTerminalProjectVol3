package PopulationsInfo;
import Populations.*;
import java.util.*;
import java.math.*;

public class InputDataProcess {
	private static InputDataProcess inputDataProcess = null;
	private Point[] terminals;
	private ArrayList<Integer> hanan_horizental;
	private ArrayList<Integer> hanan_vertical;
	
	public static InputDataProcess getInputDataProcess() {
		if(inputDataProcess == null) {
			inputDataProcess = new InputDataProcess();
			
		}
		return inputDataProcess;
	}
	
	public InputDataProcess() {
		this.terminals = new Point[ConstOfGA.NUMOFTERMINALS];
	}
	
	public void setTerminal(int index, int x, int y) {
		terminals[index] = new Point(x, y);
	}
	
	public Point getTerminalCoor(int index) {
		return terminals[index];
	}
	
	public Point[] getTerminals() {
		return terminals;
	}
	
	public ArrayList<Integer> getHanan_horizental() {
		return hanan_horizental;
	}

	public ArrayList<Integer> getHanan_vertical() {
		return hanan_vertical;
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
	}
	
	public boolean isSteinerOverlap(Point steinerPoint) {
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
}
