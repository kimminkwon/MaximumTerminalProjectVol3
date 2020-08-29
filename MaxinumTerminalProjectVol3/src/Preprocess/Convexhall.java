package Preprocess;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

import Populations.ConstOfGA;
import Populations.Point;
import PopulationsInfo.InputDataProcess;

public class Convexhall {
	private InputDataProcess idp;
	private Point[] terminals;
	private Point[] convexhallList;
	
	public Convexhall() {
		this.idp = InputDataProcess.getInputDataProcess();
		this.terminals = idp.getTerminals().clone();
		makeConvexhall();
	}
	
	public Point[] getConvexhallList() {
		System.out.println("==================ConvexHall================");
		System.out.println(Arrays.toString(convexhallList));
		return convexhallList;
	}
	
	private void makeConvexhall() {
		int flagPointIndex = findFlagPoint();
		swapTerminals(flagPointIndex, 0);
		System.out.println(InputDataProcess.getInputDataProcess().getTerminalCoor(flagPointIndex));
		sortPointListUsingDegree();

		System.out.println(Arrays.toString(terminals));
		
		Stack<Point> s = new Stack<Point>();
		s.push(terminals[0]);
		s.push(terminals[1]);
		
		int nextIndex = 2;

		while (nextIndex < ConstOfGA.NUMOFTERMINALS) {
			Point first, second, next;
			next = terminals[nextIndex];
			while (s.size() >= 2) {
				second = s.peek();
				s.pop();
				first = s.peek();
				// first, second, next가 좌회전 ( > 0 )이라면 second push
				// 우회전( < 0 )이라면 위의 while문 계속 반복
				if (CounterClockWise(first, second, next) == "left") {
					s.push(second);
					break;
				}
			}
		    s.push(next);
		    nextIndex++;
		}
		
		/*
		for(int i = 2; i < terminals.length; i++) {
			System.out.println(i);
			System.out.println(s);
			
			Point second = s.pop();
			Point first = s.peek();
			Point next = terminals[i];
			String res = CounterClockWise(first, second, next);
			if(res.equals("left") == false) { // 컨벡스홀이 될 수 없다.
				i--;
			}
			else {
				s.push(second);
				s.push(next);
			}
		}
		*/
		
		makeConvexhallList(s);
	}
	
	

	private void makeConvexhallList(Stack<Point> s) {
		convexhallList = new Point[s.size()];
		for(int i = 0; i < convexhallList.length; i++) {
			convexhallList[i] = new Point(s.get(i).getX(), s.get(i).getY());
		}
	}

	private void swapTerminals(int flagPointIndex, int i) {
		Point box = terminals[0];
		terminals[0] = terminals[flagPointIndex];
		terminals[flagPointIndex] = box;
	}

	private int findFlagPoint() {
		int index = 0;
		int minYCoor = Integer.MAX_VALUE;
		int thisXcoor = 0;
		for(int i = 0; i < terminals.length; i++) {
			if(minYCoor > terminals[i].getY()) {
				index = i;
				minYCoor = terminals[i].getY();
				thisXcoor = terminals[i].getX();
			}
			else if(minYCoor == terminals[i].getY() && thisXcoor < terminals[i].getX()) {
				index = i;
				minYCoor = terminals[i].getY();
				thisXcoor = terminals[i].getX();
			}
		}
		
		return index;
	}
	
	private void sortPointListUsingDegree() {
		 Comparator<Point> anglePointComparator = new Comparator<Point>() {
	            @Override
	            public int compare(Point o1, Point o2) {
	            	double o1Angle = Math.atan2(terminals[0].getY() - o1.getY(), terminals[0].getX() - o1.getX());
	            	double o2Angle = Math.atan2(terminals[0].getY() - o2.getY(), terminals[0].getX() - o2.getX());
	            	
	            	if(o1Angle > o2Angle)
	            		return 1;
	            	else if(o1Angle < o2Angle)
	            		return -1;
	            	else return 0;
	            }
	        };

	        Arrays.sort(terminals, 1, terminals.length, anglePointComparator);
	}
	
	
	private String CounterClockWise(Point flag, Point p1, Point p2) {
		int ccw = (flag.getX() * p1.getY() + p1.getX() * p2.getY() + p2.getX() * flag.getY())
				- (flag.getY() * p1.getX() + p1.getY() * p2.getX() + p2.getY() * p1.getX());
		if(ccw < 0)
			return "right";
		else if(ccw > 0)
			return "left";
		else
			return "same";
	}

}
