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

		System.out.println("* HananGrid Info..");
		System.out.println("  - Vertical: " + hanan_vertical);
		System.out.println("  - Horizental: " + hanan_horizental);
		
	}
}
