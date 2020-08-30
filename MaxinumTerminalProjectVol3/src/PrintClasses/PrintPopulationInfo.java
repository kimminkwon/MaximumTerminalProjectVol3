package PrintClasses;

import Populations.ConstOfGA;
import Populations.Population;

public class PrintPopulationInfo implements Print {
	@Override
	public void print(Population p) {
		System.out.println("****** Population Information... ******");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.println(p.getIndividual(i));
			System.out.print("    Fitness: " + p.getFitnessOfIndividual(i) + "\n");
		}
	}

}
