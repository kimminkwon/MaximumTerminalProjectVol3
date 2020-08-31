package PrintClasses;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import Populations.ConstOfGA;
import Populations.Individual;

public class PrintFinalResult {

	public void print(int[] numOfGenerations, double[] time, int[] maximumTerminals, Individual[] bestIndividuals) throws IOException {
		
		// 제목, 터미널의 개수, 사이클 횟수, 스위치 기준, 파트1과 2의 적합도 변수 값
		System.out.println();
		System.out.println("================================= " + ConstOfGA.TOPIC + " =================================");
		System.out.println("- VALUE OF THIS GA...");
		System.out.println("	* Num of Cycles: " + ConstOfGA.NUMOFCYCLE);
		System.out.println("	* Size of Population: " + ConstOfGA.SIZEOFPOPULATION);
		System.out.println("	* Num of Terminals: " + ConstOfGA.NUMOFTERMINALS);
		System.out.println("	* Switch Flag: " + ConstOfGA.PROBOFSWITCHCONDITION);
		System.out.println("	* FitnessPart1: (" + ConstOfGA.ALPHA + ", " + ConstOfGA.BETA + ", " + ConstOfGA.GAMMA + ")");
		System.out.println("	* FitnessPart2: (" + ConstOfGA.ALPHA_PART2 + ", " + ConstOfGA.BETA_PART2 + ", " + ConstOfGA.GAMMA_PART2 + ")");
		System.out.println("- PREPROCESSING OF THIS GA...");
		System.out.println("	* Given Length: " + ConstOfGA.GIVENLENGTH);
		System.out.println("	* Control Group Value: " + ConstOfGA.CONTROLGROUPVALUE);
		System.out.println();
		
		// 사이클 별; 세대 수, 걸린 시간, 최대 터미널 개수
		for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
			System.out.println("================================= Cycle " + c + " =================================");
			System.out.println("- Num of Generations: " + numOfGenerations[c]);
			System.out.println("- Time: " + time[c]);
			System.out.println("- Maximum Terminal Value: " + maximumTerminals[c]);
			System.out.println("- Best Individual");
			System.out.println(bestIndividuals[c]);
			System.out.println();
		}
		BufferedOutputStream bs = null;
		try {
			bs = new BufferedOutputStream(new FileOutputStream("result_" + ConstOfGA.INPUTNAME + ".txt"));
			String s = "\n================================= " + ConstOfGA.TOPIC + " =================================\n";
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
			s = "- PREPROCESSING OF THIS GA...\n";
			bs.write(s.getBytes());
			s = "	* Given Length: " + ConstOfGA.GIVENLENGTH + "\n";
			bs.write(s.getBytes());
			s = "	* Control Group Value: " + ConstOfGA.CONTROLGROUPVALUE + "\n";
			bs.write(s.getBytes());
			
			for(int c = 0; c < ConstOfGA.NUMOFCYCLE; c++) {
				s = "\n================================= Cycle " + c + " =================================\n";
				bs.write(s.getBytes());
				s = "- Num of Generations: " + numOfGenerations[c] + "\n";
				bs.write(s.getBytes());
				s = "- Time: " + time[c] + "\n";
				bs.write(s.getBytes());
				s = "- Maximum Terminal Value: " + maximumTerminals[c] + "\n";
				bs.write(s.getBytes());
				s = "- Best Individual\n";
				bs.write(s.getBytes());
				s = bestIndividuals[c].toString();
				bs.write(s.getBytes());
				s = "\n";
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
