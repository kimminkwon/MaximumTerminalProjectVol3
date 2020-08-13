package GenenticAlgorithm;

import Populations.ConstOfGA;
import Populations.Individual;
import Populations.Point;
import PopulationsInfo.InputDataProcess;

public class Mutation {
	private Individual individual;

	public Mutation(Individual individual) {
		this.individual = individual;
	}
	
	public void mutation() {
		if(makeRandom() < ConstOfGA.MUTATIONPORB_TERMINAL) {
			// System.out.println("TERMINAL 변이 발생!");
			terminalMutation();
		}
		
		if(makeRandom() < ConstOfGA.MUTATIONPORB_STEINER) {
			// System.out.println("STEINER 변이 발생!");
			steinerMutation();
		}
		
		// System.out.println("변이 결과: " + this.individual);
	}
	
	private void terminalMutation() {
		int index = makeRandom(ConstOfGA.NUMOFTERMINALS);
		int cnt = 0;
		while(cnt < ConstOfGA.NUMOFMUATATION_TERMINAL) {
			if(index == ConstOfGA.NUMOFTERMINALS) {
				index = 0;
			}
			// System.out.println(index + "-th Terminal을 변이!");
			this.individual.getTerminalStatus()[index] = !this.individual.getTerminalStatus()[index];
			index++;
			cnt++;
		}
		this.individual.setNumber();
	}
	
	private void steinerMutation() {
		int index = makeRandom(this.individual.getNumOfSteinerPoint());
		int size = this.individual.getNumOfSteinerPoint();
		int cnt = 0;
		while(cnt < ConstOfGA.NUMOFMUATATION_STEINER && size > 0) {
			if(index == this.individual.getNumOfSteinerPoint()) {
				index = 0;
			}
			// System.out.println(index + "-th Steiner를 변이!");
			
			InputDataProcess idp = InputDataProcess.getInputDataProcess();

			int x = idp.getHanan_vertical().get(makeRandom(idp.getHanan_vertical().size()));
			int y = idp.getHanan_horizental().get(makeRandom(idp.getHanan_horizental().size()));
			this.individual.getSteinerPointStatus().set(index, new Point(x, y));
			index++;
			cnt++;
		}
	}
	
	private double makeRandom() {
		double r = Math.random();
		return r;
	}
	
	private int makeRandom(int limit) {
		int r = (int) (limit * Math.random());
		return r;
	}
	
	
}
