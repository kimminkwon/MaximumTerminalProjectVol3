package GenenticAlgorithm;

import Populations.*;

public class ParentIndividuals {
	private Individual parentIndividualOne;
	private Individual parentIndividualTwo;
	private double fitnessOfChildOne;
	private double fitnessOfChildTwo;
	
	

	public ParentIndividuals(Individual parentIndividualOne, Individual parentIndividualTwo, double fitnessOfChildOne,
			double fitnessOfChildTwo) {
		this.parentIndividualOne = parentIndividualOne;
		this.parentIndividualTwo = parentIndividualTwo;
		this.fitnessOfChildOne = fitnessOfChildOne;
		this.fitnessOfChildTwo = fitnessOfChildTwo;
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
}
