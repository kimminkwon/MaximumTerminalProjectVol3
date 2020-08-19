package GenenticAlgorithm;

import Populations.*;

public class ParentIndividuals {
	private Individual parentIndividualOne;
	private Individual parentIndividualTwo;
	private int indexOfparentIndividualOne;
	private int indexOfparentIndividualTwo;
	private double fitnessOfParentOne;
	private double fitnessOfParnetTwo;
	

	public ParentIndividuals(Individual parentIndividualOne, Individual parentIndividualTwo,
			int indexOfparentIndividualOne, int indexOfparentIndividualTwo, double fitnessOfParentOne,
			double fitnessOfParnetTwo) {
		this.parentIndividualOne = parentIndividualOne;
		this.parentIndividualTwo = parentIndividualTwo;
		this.indexOfparentIndividualOne = indexOfparentIndividualOne;
		this.indexOfparentIndividualTwo = indexOfparentIndividualTwo;
		this.fitnessOfParentOne = fitnessOfParentOne;
		this.fitnessOfParnetTwo = fitnessOfParnetTwo;
	}

	public Individual getParentIndividualOne() {
		return parentIndividualOne;
	}

	public Individual getParentIndividualTwo() {
		return parentIndividualTwo;
	}

	public void setParentIndividualOne(Individual parentIndividualOne) {
		this.parentIndividualOne = parentIndividualOne;
	}

	public void setParentIndividualTwo(Individual parentIndividualTwo) {
		this.parentIndividualTwo = parentIndividualTwo;
	}

	public int getWorstIndexOfParent() {
		return fitnessOfParentOne > fitnessOfParnetTwo ? indexOfparentIndividualTwo : indexOfparentIndividualOne;
	}
	
}
