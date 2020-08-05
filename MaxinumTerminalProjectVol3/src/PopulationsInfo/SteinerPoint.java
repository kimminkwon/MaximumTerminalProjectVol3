package PopulationsInfo;

public class SteinerPoint {
	private static SteinerPoint steinerPoint = null;
	private int[] hanan_horizental;
	private int[] hanan_vertical;
	
	public static SteinerPoint getSteinerPoint() {
		if(steinerPoint == null) {
			steinerPoint = new SteinerPoint();
		}
		return steinerPoint;
	}

	public void setHanan_horizental(int[] hanan_horizental) {
		this.hanan_horizental = hanan_horizental;
	}

	public void setHanan_vertical(int[] hanan_vertical) {
		this.hanan_vertical = hanan_vertical;
	}
}
