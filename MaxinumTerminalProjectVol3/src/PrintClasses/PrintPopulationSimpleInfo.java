package PrintClasses;

import Populations.ConstOfGA;
import Populations.Population;

public class PrintPopulationSimpleInfo implements Print {

	@Override
	public void print(Population p) {		
		int maxIncludedTerminals = 0;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			if(p.getIndividual(i).getNumOfTerminal() > maxIncludedTerminals)
				maxIncludedTerminals = p.getIndividual(i).getNumOfTerminal();
		}
	
		System.out.println("****** Population Information... ******");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.print("(" + p.getIndividual(i).getNumOfTerminal() + ", " + p.getIndividual(i).getNumOfSteinerPoint() + ", " + p.getFitnessOfIndividual(i) + "), ");
		}
		System.out.println();
		System.out.println("maximumTerminal: " + maxIncludedTerminals);
		
	}
}
