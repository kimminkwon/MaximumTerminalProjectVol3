package GenenticAlgorithm;

import Populations.Individual;

public class ChildIndividuals {

	private Individual childIndividualOne;
	private Individual childIndividualTwo;
	private Individual childIndividualThree;
	private Individual childIndividualFour;
	private double[] fitnessOfChildIndividuals;
	
	public ChildIndividuals(Individual childIndividualOne, Individual childIndividualTwo,
			Individual childIndividualThree, Individual childIndividualFour, double[] fitnessOfChildIndividuals) {
		this.childIndividualOne = childIndividualOne;
		this.childIndividualTwo = childIndividualTwo;
		this.childIndividualThree = childIndividualThree;
		this.childIndividualFour = childIndividualFour;
		this.fitnessOfChildIndividuals = fitnessOfChildIndividuals;
	}
	
	public double[] getFitnessOfChildIndividuals() {
		return fitnessOfChildIndividuals;
	}

	public void setFitnessOfChildIndividuals(double[] fitnesss) {
		this.fitnessOfChildIndividuals = fitnesss;
	}

	public void setFitnessOfChildIndividuals(int index, double fitness) {
		this.fitnessOfChildIndividuals[index] = fitness;
	}
	
	public Individual getIndexOfChildIndividual(int index) {
		if(index == 0) {
			return getChildIndividualOne();
		}
		else if(index == 1) {
			return getChildIndividualTwo();
		}
		else if(index == 2) {
			return getChildIndividualThree();
		}
		else if(index == 3) {
			return getChildIndividualFour();
		}
		else {
			return null;
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
	
	
}
