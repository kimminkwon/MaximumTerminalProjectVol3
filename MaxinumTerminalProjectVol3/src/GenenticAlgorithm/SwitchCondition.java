package GenenticAlgorithm;

import ControlOrLength.GivenLength;
import Populations.ConstOfGA;
import Populations.Population;
import PopulationsInfo.CalFitness;

public class SwitchCondition {
	private Population population;
	private int givenLength;

	public SwitchCondition(Population population, int givenLength) {
		this.population = population;
		this.givenLength = givenLength;
	}
	
	public boolean switchConst() {
		if(isSwitch() == true) {
			System.out.println("========================= Swicth! ===========================");
			localSearchSwitch();
			fitnessSwitch();
			fitnessModify();
			return true;
		}
		else
			return false;
	}
	
	private void fitnessModify() {
		CalFitness cf;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			cf = new CalFitness(population.getIndividual(i), givenLength); 
			population.setFitnessOfIndividual(i, cf.calFitness());
		}
	}
	
	private void mutationSwitch() {
		ConstOfGA.setMutationporbSteiner(0.5);
	}
	
	private void localSearchSwitch() {
		ConstOfGA.setProbsteineradd(0.5);
		ConstOfGA.setProbsteinerdelete(0.9);
		ConstOfGA.setProbsteineraddparttwo(0.5);
	}
	
	private void fitnessSwitch() {
		ConstOfGA.setAlpha(0.05);
		ConstOfGA.setBeta(0.25);
		ConstOfGA.setGamma(0.7);
	}
	
	
	private boolean isSwitch() {
		boolean res = false;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			int cnt = 0;
			for(int j = 0; j < ConstOfGA.SIZEOFPOPULATION; j++) {
				if(i != j && population.getFitnessOfIndividual(i) == population.getFitnessOfIndividual(j)) {
					cnt++;
				}
				if(cnt > (ConstOfGA.SIZEOFPOPULATION * 0.65)) {
					res = true;
				}
			}
		}
	
		return res;
	}
	
	
}
