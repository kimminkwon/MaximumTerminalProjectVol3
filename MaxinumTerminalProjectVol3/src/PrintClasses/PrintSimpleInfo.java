package PrintClasses;

import java.util.Arrays;

import Populations.ConstOfGA;
import Populations.Population;

public class PrintSimpleInfo implements Print {

	@Override
	public void print(Population p) {
		int maxIncludedTerminals = 0;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			if(p.getIndividual(i).getNumOfTerminal() > maxIncludedTerminals)
				maxIncludedTerminals = p.getIndividual(i).getNumOfTerminal();
		}
		System.out.println("****** Population Information... ******");
		System.out.println("	- maxIncludedTerminals = " + maxIncludedTerminals);
		System.out.println("Fitness: " + Arrays.toString(p.getFitnessOfIndividual()));
	}

}
