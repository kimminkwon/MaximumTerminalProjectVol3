package GenenticAlgorithm;

import Populations.ConstOfGA;
import Populations.Population;

public class TerminateCondition {
	private Population population;

	public TerminateCondition(Population population) {
		this.population = population;
	}

	public boolean isTerminate() {
		boolean res = false;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			int cnt = 0;
			for(int j = 0; j < ConstOfGA.SIZEOFPOPULATION; j++) {
				if(i != j && population.getFitnessOfIndividual(i) == population.getFitnessOfIndividual(j)) {
					cnt++;
				}
				if(cnt > (ConstOfGA.SIZEOFPOPULATION * 0.85)) {
					res = true;
				}
			}
		}
	
		return res;
	}
	
	
	
	
}
