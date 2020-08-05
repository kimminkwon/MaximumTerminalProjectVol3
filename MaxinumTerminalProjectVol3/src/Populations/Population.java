package Populations;
import java.util.*;

public class Population {
	private Individual[] population;
	private double[] fitnessOfIndividual;
	
	public Population() {
		this.population = new Individual[ConstOfGA.SIZEOFPOPULATION];
	}
	
	public void setIndividual(int index, Individual individual, double fitness) {
		population[index] = individual;
		fitnessOfIndividual[index] = fitness;
	}
	
	public Individual getIndividual(int index) {
		return population[index];
	}
}
