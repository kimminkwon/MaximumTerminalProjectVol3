package GenenticAlgorithm;

import Populations.*;

public class Selection {
	private int isOverlap;
	private Population population;
	private double[] fitnessOfIndividual;
	private double[] rouletteValues;
	
	public Selection(Population population, int isOverlap) {
		this.population = population;
		this.fitnessOfIndividual = this.population.getFitnessOfIndividual();
		this.rouletteValues = new double[fitnessOfIndividual.length];
		this.isOverlap = isOverlap;
	}
	
	public int selection() {
		int choiceIndividual = 0;
		double sumOfRouletteValues = setRouletteValues();
		// System.out.println("RouletteValues: " + Arrays.toString(rouletteValues));
		while(true) {
			choiceIndividual = choiceIndividual(makeRandom(sumOfRouletteValues));
			if(isOverlap != choiceIndividual)
				break;
			// System.out.println("중복 발생!!");
		}
	
		return choiceIndividual;
	}
	
	private double setRouletteValues() {
		double maxFitness = Double.MIN_VALUE;
		double minFitness = Double.MAX_VALUE;
		double sumOfRouletteValues = 0.0;
		
		for(int i = 0; i < rouletteValues.length; i++) {
			if(maxFitness < fitnessOfIndividual[i])
				maxFitness = fitnessOfIndividual[i];
			if(minFitness > fitnessOfIndividual[i])
				minFitness = fitnessOfIndividual[i];
		}
		
		for(int i = 0; i < rouletteValues.length; i++) {
			rouletteValues[i] = makeRouletteValue(maxFitness, minFitness, fitnessOfIndividual[i]);
			sumOfRouletteValues = sumOfRouletteValues + rouletteValues[i];
		}
		
		return sumOfRouletteValues;
	}
	
	private double makeRouletteValue(double maxFitness, double minFitness, double fitness) {
		double rouletteValue = 0.0;
		rouletteValue = (fitness - minFitness) + ((maxFitness - minFitness) / (3 - 1));
		return rouletteValue;
	}
	
	private int choiceIndividual(double rand) {
		double box = 0.0;
		int choice = 0;
		
		for(choice = 0; choice < rouletteValues.length; choice++) {
			box = box + rouletteValues[choice];
			if(box > rand) 
				break;
		}
		
		return choice;
	}
	
	private double makeRandom(double limit) {
		double r = limit * Math.random();
		return r;
	}
	


}
