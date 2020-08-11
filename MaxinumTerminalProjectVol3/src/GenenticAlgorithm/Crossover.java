package GenenticAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;

import Populations.ConstOfGA;
import Populations.Individual;
import Populations.Point;

public class Crossover {
	private ParentIndividuals parentIndividuals;
	private Individual parentIndividualOne;
	private Individual parentIndividualTwo;
	
	private Individual childIndividualOne;
	private Individual childIndividualTwo;
	private Individual childIndividualThree;
	private Individual childIndividualFour;
	
	public Crossover(ParentIndividuals parentIndividuals) {
		this.parentIndividuals = parentIndividuals;
		this.parentIndividualOne = parentIndividuals.getParentIndividualOne();
		this.parentIndividualTwo = parentIndividuals.getParentIndividualTwo();
		
		if(parentIndividualOne.getNumOfSteinerPoint() < parentIndividualTwo.getNumOfSteinerPoint()) {
			this.parentIndividualOne = parentIndividuals.getParentIndividualTwo();
			this.parentIndividualTwo = parentIndividuals.getParentIndividualOne();			
		}
	}
	
	public Individual getChildIndividualOne() {
		return childIndividualOne;
	}

	public Individual getChildIndividualTwo() {
		return childIndividualTwo;
	}

	public Individual getChildIndividualThree() {
		return childIndividualThree;
	}

	public Individual getChildIndividualFour() {
		return childIndividualFour;
	}

	public void crossover() {
		int divisionPoint = makeRandom(ConstOfGA.NUMOFTERMINALS);
		
		boolean[] terminalLR = new boolean[ConstOfGA.NUMOFTERMINALS];
		boolean[] terminalRL = new boolean[ConstOfGA.NUMOFTERMINALS];
		
		for(int i = 0; i < divisionPoint; i++) {
			terminalLR[i] = parentIndividualOne.getTerminalStatus()[i];
			terminalRL[i] = parentIndividualTwo.getTerminalStatus()[i];
		}
		
		for(int i = divisionPoint; i < ConstOfGA.NUMOFTERMINALS; i++) {
			terminalLR[i] = parentIndividualTwo.getTerminalStatus()[i];
			terminalRL[i] = parentIndividualOne.getTerminalStatus()[i];
		}
		
		System.out.println("TerminalDivisionPoint: " + divisionPoint);
		System.out.println("terminalLR: " + Arrays.toString(terminalLR));
		System.out.println("terminalRL: " + Arrays.toString(terminalRL));
		
		ArrayList<Point> steinerPointStatusLR = new ArrayList<>();
		ArrayList<Point> steinerPointStatusRL = new ArrayList<>();
		
		divisionPoint = parentIndividualOne.getNumOfSteinerPoint() >= parentIndividualTwo.getNumOfSteinerPoint()? makeRandom(parentIndividualTwo.getNumOfSteinerPoint()) : makeRandom(parentIndividualOne.getNumOfSteinerPoint());
		
		for(int i = 0; i < divisionPoint; i++) {
			steinerPointStatusLR.add(parentIndividualOne.getSteinerPointStatus().get(i));
			steinerPointStatusRL.add(parentIndividualTwo.getSteinerPointStatus().get(i));
		}
		
		for(int i = divisionPoint; i < parentIndividualTwo.getNumOfSteinerPoint(); i++) {
			steinerPointStatusLR.add(parentIndividualTwo.getSteinerPointStatus().get(i));
			steinerPointStatusRL.add(parentIndividualOne.getSteinerPointStatus().get(i));
		}
		
		for(int i = parentIndividualTwo.getNumOfSteinerPoint(); i < parentIndividualOne.getNumOfSteinerPoint(); i++) {
			steinerPointStatusRL.add(parentIndividualOne.getSteinerPointStatus().get(i));
		}
		
		System.out.println("SteinerDivisionPoint: " + divisionPoint);
		System.out.println("steinerPointStatusLR: " + steinerPointStatusLR);
		System.out.println("steinerPointStatusRL: " + steinerPointStatusRL);
		
		makeIndividual(terminalLR, terminalRL, steinerPointStatusLR, steinerPointStatusRL);
	}
	
	private void makeIndividual(boolean[] terminalLR, boolean[] terminalRL, ArrayList<Point> steinerPointStatusLR, ArrayList<Point> steinerPointStatusRL) {
		childIndividualOne = new Individual(terminalLR, steinerPointStatusLR);
		childIndividualTwo = new Individual(terminalLR, steinerPointStatusRL);
		childIndividualThree = new Individual(terminalRL, steinerPointStatusLR);
		childIndividualFour = new Individual(terminalRL, steinerPointStatusRL);
	}
	
	private int makeRandom(int limit) {
		int r = (int)(limit * Math.random());
		return r;
	}
	
}
