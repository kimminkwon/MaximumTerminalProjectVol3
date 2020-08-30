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
		//1. 0번점과 1번점을 스택에 넣는다.
		s.push(new Point(terminals[0].getX(), terminals[0].getY()));
		s.push(new Point(terminals[1].getX(), terminals[1].getY()));
		
		int nextIndex = 2;

		while (nextIndex < ConstOfGA.NUMOFTERMINALS) {
			Point first, second, next;
			next = new Point(terminals[nextIndex].getX(), terminals[nextIndex].getY());
			System.out.println("현재 스택: " + s);
			System.out.println("next: " + next);
			
			while (s.size() >= 2) {
				second = new Point(s.peek().getX(), s.peek().getY());
				s.pop();
				first = new Point(s.peek().getX(), s.peek().getY());
				System.out.println("vector " + first + " to " + second + "에 대해 " + next + "는 어느쪽에 있는가 = " + CounterClockWise(first, second, next));

				// first, second, next가 좌회전 ( > 0 )이라면 second push
				// 우회전( < 0 )이라면 위의 while문 계속 반복
				if (CounterClockWise(first, second, next) == "left") {
					System.out.println(next + "는 좌회전입니다.");
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
	
	// 직선 AB와 C의 관계
	private String CounterClockWise(Point A, Point B, Point C) {
		int part1 = (A.getX() * B.getY() + B.getX() * C.getY() + C.getX() * A.getY());
		int part2 = (A.getX() * C.getY() + B.getX() * A.getY() + C.getX() * B.getY());
		int ccw = part1 - part2;
		
		if(ccw < 0)
			return "right";
		else if(ccw > 0)
			return "left";
		else
			return "same";
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
		for(int i = 0; i < terminals.length; i++) {
			if(minYCoor > terminals[i].getY()) {
				index = i;
				minYCoor = terminals[i].getY();
			}
		}
		for(int i = 0; i < terminals.length; i++) {
			if(terminals[index].getY() >= terminals[i].getY())
				if(terminals[index].getX() >= terminals[i].getX()) {
					index = i;
					minYCoor = terminals[i].getY();
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


}
