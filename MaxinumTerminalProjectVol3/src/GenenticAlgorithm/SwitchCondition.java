package GenenticAlgorithm;

import Populations.ConstOfGA;
import Populations.Population;

public class SwitchCondition {
	private Population population;

	public SwitchCondition(Population population) {
		this.population = population;
	}
	
	public void switchConst() {
		if(isSwitch() == true) {
			localSearchSwitch();
			fitnessSwitch();
		}
	}
	
	private void localSearchSwitch() {
		ConstOfGA.setProbsteineradd(0.3);
		ConstOfGA.setProbsteinerdelete(0.9);
		ConstOfGA.setProbsteineraddparttwo(0.3);
	}
	
	private void fitnessSwitch() {
		ConstOfGA.setAlpha(0.3);
		ConstOfGA.setBeta(0.1);
		ConstOfGA.setGamma(0.6);
	}
	
	
	private boolean isSwitch() {
		boolean res = false;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			int cnt = 0;
			for(int j = 0; j < ConstOfGA.SIZEOFPOPULATION; j++) {
				if(i != j && population.getFitnessOfIndividual(i) == population.getFitnessOfIndividual(j)) {
					cnt++;
				}
				if(cnt > (ConstOfGA.SIZEOFPOPULATION * 0.7)) {
					res = true;
				}
			}
		}
	
		return res;
	}
	
	
}
