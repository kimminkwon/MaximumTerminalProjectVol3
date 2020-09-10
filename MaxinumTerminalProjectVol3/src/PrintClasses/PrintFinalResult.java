package PrintClasses;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import Populations.ConstOfGA;
import Populations.Individual;

public class PrintFinalResult {

	public void print(int[] numOfGenerations, double[] time, int[] switchMaximumTerminals, Individual[] bestIndividuals) throws IOException {
		
		double averageTerminal = 0;
		double averageSwitchTerminal = 0;
		double averageSteiner = 0;
		double averageLength = 0;
		double averageTime = 0;
		for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
			averageTerminal = averageTerminal + (double)bestIndividuals[c].getNumOfTerminal();
			averageSwitchTerminal = averageSwitchTerminal + (double)switchMaximumTerminals[c];
			averageSteiner = averageSteiner + (double)bestIndividuals[c].getNumOfSteinerPoint();
			averageLength = averageLength + (double)bestIndividuals[c].getLength();
			averageTime = averageTime + (double)time[c];
		}
		averageTerminal = (double)(averageTerminal / (double)ConstOfGA.NUMOFCYCLE);
		averageSwitchTerminal = (double)(averageSwitchTerminal / (double)ConstOfGA.NUMOFCYCLE);
		averageSteiner = (double)(averageSteiner / (double)ConstOfGA.NUMOFCYCLE);
		averageLength = (double)(averageLength / (double)ConstOfGA.NUMOFCYCLE);
		averageTime = (double)(averageTime / (double)ConstOfGA.NUMOFCYCLE);
		
		// 제목, 터미널의 개수, 사이클 횟수, 스위치 기준, 파트1과 2의 적합도 변수 값
		System.out.println();
		System.out.println("================================= " + ConstOfGA.TOPIC + " =================================");
		System.out.println("- INPUT FILE NAME: " + ConstOfGA.INPUTNAME);
		System.out.println("- VALUE OF THIS GA...");
		System.out.println("	* Num of Cycles: " + ConstOfGA.NUMOFCYCLE);
		System.out.println("	* Size of Population: " + ConstOfGA.SIZEOFPOPULATION);
		System.out.println("	* Num of Terminals: " + ConstOfGA.NUMOFTERMINALS);
		System.out.println("	* Switch Flag: " + ConstOfGA.PROBOFSWITCHCONDITION);
		System.out.println("	* FitnessPart1: (" + ConstOfGA.ALPHA + ", " + ConstOfGA.BETA + ", " + ConstOfGA.GAMMA + ")");
		System.out.println("	* FitnessPart2: (" + ConstOfGA.ALPHA_PART2 + ", " + ConstOfGA.BETA_PART2 + ", " + ConstOfGA.GAMMA_PART2 + ")");
		System.out.println("- RESULT OF THIS GA...");
		System.out.println("	* Given Length: " + ConstOfGA.GIVENLENGTH);
		System.out.println("	* Control Group Value: " + ConstOfGA.CONTROLGROUPVALUE);
		System.out.println("	* Average Terminal: " + averageTerminal);
		System.out.println("	* Average SwitchTerminal: " + averageSwitchTerminal);
		System.out.println("	* Average Steiner: " + averageSteiner);
		System.out.println("	* Average Length: " + averageLength);
		System.out.println("	* Average Time: " + averageTime);
		
		System.out.println();
		
		// 사이클 별; 세대 수, 걸린 시간, 최대 터미널 개수
		for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
			System.out.println("================================= Cycle " + c + " =================================");
			System.out.println("- Num of Generations: " + numOfGenerations[c]);
			System.out.println("- Time: " + time[c]);
			System.out.println("- Switch Maximum Terminal Value: " + switchMaximumTerminals[c]);
			System.out.println("- Best Individual Terminal Value: " + bestIndividuals[c].getNumOfTerminal());
			System.out.println("- Best Individual Steiner Value:" + bestIndividuals[c].getNumOfSteinerPoint());
			System.out.println("- Best Individual Length: " + bestIndividuals[c].getLength());
			System.out.println("- QBI: " + ((double)bestIndividuals[c].getNumOfTerminal() / (double)ConstOfGA.CONTROLGROUPVALUE) * 100.0f);
			System.out.println();
		}
		
		for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
			System.out.println("================================= Best Individual in Cycle " + c + " =================================");
			System.out.println(bestIndividuals[c]);
			System.out.println();
		}
		BufferedOutputStream bs = null;
		try {
			bs = new BufferedOutputStream(new FileOutputStream(ConstOfGA.TOPIC + "(Memetic)_result_" + ConstOfGA.INPUTNAME + ".txt"));
			String s = "\n================================= " + ConstOfGA.TOPIC + " =================================\n";
			bs.write(s.getBytes());
			s = "- INPUT FILE NAME: " + ConstOfGA.INPUTNAME + "\n";
			bs.write(s.getBytes());
			s = "- VALUE OF THIS GA...\n";
			bs.write(s.getBytes());
			s = "	* Num of Cycles: " + ConstOfGA.NUMOFCYCLE + "\n";
			bs.write(s.getBytes());
			s = "	* Size of Population: " + ConstOfGA.SIZEOFPOPULATION + "\n";
			bs.write(s.getBytes());
			s = "	* Num of Terminals: " + ConstOfGA.NUMOFTERMINALS + "\n";
			bs.write(s.getBytes());
			s = "	* Switch Flag: " + ConstOfGA.PROBOFSWITCHCONDITION + "\n";
			bs.write(s.getBytes());
			s = "	* FitnessPart1: (" + ConstOfGA.ALPHA + ", " + ConstOfGA.BETA + ", " + ConstOfGA.GAMMA + ")\n";
			bs.write(s.getBytes());
			s = "	* FitnessPart2: (" + ConstOfGA.ALPHA_PART2 + ", " + ConstOfGA.BETA_PART2 + ", " + ConstOfGA.GAMMA_PART2 + ")\n";
			bs.write(s.getBytes());
			s = "- RESULT OF THIS GA...\n";
			bs.write(s.getBytes());
			s = "	* Given Length: " + ConstOfGA.GIVENLENGTH + "\n";
			bs.write(s.getBytes());
			s = "	* Control Group Value: " + ConstOfGA.CONTROLGROUPVALUE + "\n";
			bs.write(s.getBytes());
			s = "	* Average Terminal: " + averageTerminal + "\n";
			bs.write(s.getBytes());
			s = "	* Average SwitchTerminal: " + averageSwitchTerminal + "\n";
			bs.write(s.getBytes());
			s = "	* Average Steiner: " + averageSteiner + "\n";
			bs.write(s.getBytes());	
			s = "	* Average Length: " + averageLength + "\n";
			bs.write(s.getBytes());
			s = "	* Average Time: " + averageTime + "\n";
			bs.write(s.getBytes());
			
			for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
				s = "\n================================= Cycle " + c + " =================================\n";
				bs.write(s.getBytes());
				s = "- Num of Generations: " + numOfGenerations[c] + "\n";
				bs.write(s.getBytes());
				s = "- Time: " + time[c] + "\n";
				bs.write(s.getBytes());
				s = "- Switch Maximum Terminal Value: " + switchMaximumTerminals[c] + "\n";
				bs.write(s.getBytes());
				s = "- Best Individual Terminal Value: " + bestIndividuals[c].getNumOfTerminal() + "\n";
				bs.write(s.getBytes());
				s = "- Best Individual Steiner Value: " + bestIndividuals[c].getNumOfSteinerPoint() +"\n";
				bs.write(s.getBytes());
				s = "- Best Individual Length: " + bestIndividuals[c].getLength() +"\n";
				bs.write(s.getBytes());
			}

			for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
				s = "\n================================= Best Individual in Cycle " + c + " =================================\n";
				bs.write(s.getBytes());
				s = bestIndividuals[c].toString();
				bs.write(s.getBytes());
				s = "\n";
				bs.write(s.getBytes());
			}
			
			s = "\n================================= InData =================================\n";
			bs.write(s.getBytes());
			s = ConstOfGA.CONTROLGROUPVALUE + "\n";
			bs.write(s.getBytes());
			for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
				s = numOfGenerations[c] + "\n";
				bs.write(s.getBytes());
				s = time[c] + "\n";
				bs.write(s.getBytes());
				s = bestIndividuals[c].getNumOfTerminal() + "\n";
				bs.write(s.getBytes());
				s = bestIndividuals[c].getNumOfSteinerPoint() +"\n";
				bs.write(s.getBytes());
				s = bestIndividuals[c].getLength() +"\n";
				bs.write(s.getBytes());
				s = ((double)bestIndividuals[c].getNumOfTerminal() / (double)ConstOfGA.CONTROLGROUPVALUE) * 100.0f + "\n";
				bs.write(s.getBytes());
			}
		} 
		catch (Exception e) {
			e.getStackTrace();
		} 
		finally {
			bs.close();
		} 
	}
}
