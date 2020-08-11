package Populations;
import java.util.*;

public class Population {
	private Individual[] population;
	private double[] fitnessOfIndividual;
	
	public Population() {
		this.population = new Individual[ConstOfGA.SIZEOFPOPULATION];
		this.fitnessOfIndividual = new double[ConstOfGA.SIZEOFPOPULATION];
	}
	
	public void setIndividual(int index, Individual individual, double fitness) {
		population[index] = individual;
		fitnessOfIndividual[index] = fitness;
	}
	
	public Individual getIndividual(int index) {
		return population[index];
	}

	public double getFitnessOfIndividual(int index) {
		return fitnessOfIndividual[index];
	}
	
	public double[] getFitnessOfIndividual() {
		return fitnessOfIndividual;
	}

	public void setFitnessOfIndividual(int index, double fitness) {
		this.fitnessOfIndividual[index] = fitness;
	}
	
	
}
