package PopulationsInfo;
import Populations.*;
import Preprocess.Convexhall;

import java.util.*;
import java.math.*;

public class InputDataProcess {
	private static InputDataProcess inputDataProcess = null;
	private Point[] terminals;
	private ArrayList<Integer> hanan_horizental;
	private ArrayList<Integer> hanan_vertical;
	private HashSet<Point> outOfConvexHallSteinerPoints;
	
	public static InputDataProcess getInputDataProcess() {
		if(inputDataProcess == null) {
			inputDataProcess = new InputDataProcess();
			
		}
		return inputDataProcess;
	}
	
	public InputDataProcess() {
		this.terminals = new Point[ConstOfGA.NUMOFTERMINALS];
		outOfConvexHallSteinerPoints = new HashSet<Point>();
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
		
		makeOutOfConvexHallSteinerPoints();
	}
	
	public boolean isSteinerOverlap(Point steinerPoint) {
		if(SPTerminalOverlap(steinerPoint) == true)
			return true;
		else if(SPOutofConvexHall(steinerPoint) == true)
			return true;
		else 
			return false;
	}
	
	private boolean SPTerminalOverlap(Point steinerPoint) { // true면 스타이너 포인트가 터미널과 겹친다.
		boolean res = false;
		for(int i = 0; i < terminals.length; i++) {
			if(comparePoint(steinerPoint, terminals[i]) == true) {
				res = true;
			}
		}
		return res;
	}
	
	private boolean SPOutofConvexHall(Point steinerPoint) { // true면 컨벡스홀 밖이다.
		return outOfConvexHallSteinerPoints.contains(steinerPoint);
	}
	
	public HashSet<Point> getOutOfConvexHallSteinerPoints() {
		return outOfConvexHallSteinerPoints;
	}

	public void setOutOfConvexHallSteinerPoints(HashSet<Point> outOfConvexHallSteinerPoints) {
		this.outOfConvexHallSteinerPoints = outOfConvexHallSteinerPoints;
	}

	private boolean comparePoint(Point p1, Point p2) {
		boolean res = false;
		
		if(p1.getX() == p2.getX() && p1.getY() == p2.getY()) {
			res = true;
		}
		
		return res;
	}
	
	private void makeOutOfConvexHallSteinerPoints() {
		Convexhall ch = new Convexhall();
		Point[] convexhallList = ch.getConvexhallList();
		
		for(int v = 0; v < hanan_vertical.size(); v++) {
			for(int h = 0; h < hanan_horizental.size(); h++) {
				Point p = new Point(hanan_vertical.get(v), hanan_horizental.get(h));
				if(isOutofConvexhall(convexhallList, p) == true)
					if(SPTerminalOverlap(p) == false)
						outOfConvexHallSteinerPoints.add(p);
			}
		}
	}

	private boolean isOutofConvexhall(Point[] convexhallList, Point point) {
		int cnt = 0;

		System.out.println("============ " + point + "의 교차 회수 확인  ==============");
		for(int i = 0; i < convexhallList.length; i++) {
			if(i == convexhallList.length - 1) {
				if(isCross(convexhallList[i], convexhallList[0], point) == true)
					cnt++;
			}
			else {
				if(isCross(convexhallList[i], convexhallList[i+1], point) == true)
					cnt++;
			}
		}

		System.out.println("	" + point + "의 교차 회수(홀수이면 컨벡스 홀 안에 있다!): " + cnt);
		if(cnt % 2 == 1) // 해당 포인트는 컨벡스 홀 안에 있다.
			return false;
		else // 해당 포인트는 컨벡스 홀 바깥에 있다.
			return true;
	}
	
	private boolean isCross(Point line1, Point line2, Point p) {
		if(line2.getY() < line1.getY()) {
			Point box = line1;
			line2 = line1;
			line1 = box;
		}
		
		System.out.println("	" + line1 + "와 " + line2 + "로 이뤄진 선분과 " + p +  "의 교차점이 있는가?");
		
		
		if(p.getY() > line1.getY() && p.getY() < line2.getY()) {
			double crossPointX = ((double)line2.getX() - (double)line1.getX()) * ((double)p.getY() - (double)line1.getY()) 
					/ ((double)line2.getY() - (double)line1.getY()) + (double)line1.getX();
			System.out.println("	" + line1 + "과 " + line2 + "의 교차점: " + crossPointX);
			if(crossPointX > p.getX())
				return true;
			else {
				System.out.println("	없음!");
			}
		}
		else {
			System.out.println("	없음!");
		}
		
		return false;
	
	}		
}
