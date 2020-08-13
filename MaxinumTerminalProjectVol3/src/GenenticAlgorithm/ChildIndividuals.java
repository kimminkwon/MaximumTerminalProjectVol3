package GenenticAlgorithm;

import Populations.Individual;

public class ChildIndividuals {

	private Individual childIndividualOne;
	private Individual childIndividualTwo;
	private Individual childIndividualThree;
	private Individual childIndividualFour;
	
	public ChildIndividuals(Individual childIndividualOne, Individual childIndividualTwo,
			Individual childIndividualThree, Individual childIndividualFour) {
		this.childIndividualOne = childIndividualOne;
		this.childIndividualTwo = childIndividualTwo;
		this.childIndividualThree = childIndividualThree;
		this.childIndividualFour = childIndividualFour;
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
	
	
}
