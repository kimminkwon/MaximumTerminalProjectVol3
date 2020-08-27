package Populations;

import java.util.HashSet;

public class Point {
	private int x;
	private int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		String s = String.valueOf(this.x) + String.valueOf(this.y);
		return 31 * Integer.parseInt(s);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Point) {
			Point p = (Point) obj;
			return p.getX() == this.x && p.getY() == this.y; 
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	

	public static void main(String[] args) {
		HashSet<Point> h = new HashSet<Point>();
		System.out.println(h.add(new Point(1, 1)));
		System.out.println(h.add(new Point(1, 2)));
		System.out.println(h.add(new Point(1, 1)));
		
	}
}
