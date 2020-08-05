package PopulationsInfo;
import java.util.ArrayList;

import Populations.*;

public class CalFitness {
	private Point[] totalTerminals;
	private double[][] dist;
	private Individual calIndividual;
		 
	public CalFitness(Individual individual) {
		this.calIndividual = individual;
		boolean[] terminalbox = calIndividual.getTerminalStatus();
		ArrayList<Point> steinerBox = calIndividual.getSteinerPointStatus();
		this.totalTerminals = new Point[terminalbox.length + steinerBox.size()];
		
		InputDataProcess idp = InputDataProcess.getInputDataProcess();
		
		int cnt = 0;
		
		for(int i = 0; i < terminalbox.length; i++) {
			if(terminalbox[i] == true) {
				totalTerminals[cnt] = idp.getTerminalCoor(i);
				cnt++;
			}
		}
		for(Point p : steinerBox) {
		
		}
		
		
	}


	public double calFitness(Individual individual) {
		return 0.0;
	}
}
