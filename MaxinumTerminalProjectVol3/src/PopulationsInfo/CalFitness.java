package PopulationsInfo;

import Populations.*;

public class CalFitness {
	private Individual calIndividual;
	private double fitness;
	private int givenLength;
	
	public CalFitness(Individual calIndividual, int givenLength) {
		this.calIndividual = calIndividual;
		this.givenLength = givenLength;
	}
	
	public double calFitness() {
		double f, f_prime, A, B, C;
		
		double numOfTerminals = calIndividual.getNumOfTerminal();
		double length = calIndividual.getLength();
		int[] degreeArr = calIndividual.getDegreeOfSteinerPoint();
		int sumOfDegree = 0;
		for(int i = 0; i < degreeArr.length; i++) {
			sumOfDegree= sumOfDegree + degreeArr[i];
		}
		
		A = ConstOfGA.ALPHA * Math.min(1.0, (length / (0.95 * givenLength)));
		B = ConstOfGA.BETA * (1-(numOfTerminals / ConstOfGA.NUMOFTERMINALS));
		double boxOfC = (3 * (ConstOfGA.NUMOFTERMINALS - 2)) - sumOfDegree;
		C = ConstOfGA.GAMMA * (boxOfC / (3 * (ConstOfGA.NUMOFTERMINALS - 2)));
		// System.out.println("A=" + A + ", B= " + B + ", C= " + C);
		f_prime = A+B+C;
		f = (1.0 - f_prime);
		
		return f;
	}
	
}
