package GenenticAlgorithm;

import Populations.Population;

public class Replacement {
	private Population population;
	private ParentIndividuals parentIndividuals;
	private ChildIndividuals childIndividuals;

	
	public Replacement(Population population, ParentIndividuals parentIndividuals, ChildIndividuals childIndividuals) {
		this.population = population;
		this.parentIndividuals = parentIndividuals;
		this.childIndividuals = childIndividuals;
	}

	public void replacement() {
		int childIndex = findFinalChildIndividualIndexOfChildIndividuals();
		int parentIndex = findWorstParentIndividualIndexOfPopulation();
		
		population.setIndividual(parentIndex, childIndividuals.getIndexOfChildIndividual(childIndex), childIndividuals.getFitnessOfChildIndividuals()[childIndex]);
	}

	private int findFinalChildIndividualIndexOfChildIndividuals() {
		double[] childindividualFitness = childIndividuals.getFitnessOfChildIndividuals();
		double maxFitness = Double.MIN_VALUE;
		int indexOfmaxFitness = 0;
		for(int i = 0; i < childindividualFitness.length; i++) {
			if(maxFitness < childindividualFitness[i]) {
				maxFitness = childindividualFitness[i];
				indexOfmaxFitness = i;
			}
		}

		return indexOfmaxFitness;
	}

	private int findWorstParentIndividualIndexOfPopulation() {
		return parentIndividuals.getWorstIndexOfParent();
	}
}
