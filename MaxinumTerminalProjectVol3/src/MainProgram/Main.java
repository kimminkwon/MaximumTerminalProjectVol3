package MainProgram;

import java.io.IOException;
import java.util.*;
import ControlGroup.*;
import ControlOrLength.*;
import GenenticAlgorithm.*;
import Populations.*;
import PopulationsInfo.*;
import PrintClasses.*;

public class Main {
	public static void main(String[] args) {
		
		// 입력 받기
		Scanner input = new Scanner(System.in);
		String topic = input.nextLine();
		ConstOfGA.setTOPIC(topic);
		ConstOfGA.setINPUTNAME(input.nextLine());
		int numOfTerminals = input.nextInt();
		ConstOfGA.setNumofterminals(numOfTerminals);
		int sizeOfPopulation = input.nextInt();
		ConstOfGA.setSIZEOFPOPULATION(sizeOfPopulation);
		int numOfCycle = input.nextInt();
		ConstOfGA.setNUMOFCYCLE(numOfCycle);

		InputDataProcess idp = InputDataProcess.getInputDataProcess();

		for (int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			int x = input.nextInt();
			int y = input.nextInt();
			idp.setTerminal(i, x, y);
		}
		
		// Cycle마다 저장할 배열
		int[] numOfGenerations = new int[ConstOfGA.NUMOFCYCLE]; 
		double[] time = new double[ConstOfGA.NUMOFCYCLE]; 
		int[] switchMaximumTerminals = new int[ConstOfGA.NUMOFCYCLE]; 
		Individual[] bestIndividuals = new Individual[ConstOfGA.NUMOFCYCLE];
		
		// 필요한 변수
		int givenLength;
		double IGP;
		
		// GivenLength 계산 후 IGP 얻기
		GivenLength gl = new GivenLength();
		givenLength = gl.getGivenLength();
		ConstOfGA.setGIVENLENGTH(givenLength);
		IGP = gl.getIGP();

		// ControlGroup 생성
		ControlGroup controlGroup = new ControlGroup_MST();
		int controlGroupValue = controlGroup.getControlGroupValue(givenLength);
		ConstOfGA.setCONTROLGROUPVALUE(controlGroupValue);

		for(int cycle = 0; cycle < ConstOfGA.NUMOFCYCLE; cycle++) { // cycle만큼 반복
			
			long startTime = System.currentTimeMillis();
			
			// 필요한 변수 선언
			boolean isPart2 = false;
			
			// 하난 그리드 생성			
			idp.makeHananGrid();
			
			// 초기 해 생성. Population 생성 (해 생성 -> 조정 -> 적합도)
			double[] igpArr = { 0.6, 0.7, 0.8, 1.0, 1.1 }; // igp는 배열로 선언 가능, 각 Index 범위에 따라 확률을 다르게 주고 싶을 때 사용

			MakeIndividual makeIndividual;
			Individual[] individuals = new Individual[ConstOfGA.SIZEOFPOPULATION];

			// 해 생성
			for (int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
				makeIndividual = new MakeIndividual(IGP, igpArr);
				individuals[i] = new Individual(makeIndividual.getTerminalStatus(), makeIndividual.getSteinerPointStatus());
			}

			// 해 집단 조정연산
			Adjustment ad;
			CalLength cl;
			for (int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
				ad = new Adjustment(individuals[i], givenLength);
				ad.adjustment();
				cl = new CalLength(individuals[i]);
				individuals[i].setLength(cl.getLength());
				// System.out.println(i + "-th Individual Length: " + cl.getLength());
			}

			// 적합도 배열 생성
			double[] fitnessArr = new double[ConstOfGA.SIZEOFPOPULATION];
			CalFitness calFitness;
			for (int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
				calFitness = new CalFitness(individuals[i], givenLength);
				fitnessArr[i] = calFitness.calFitness();
			}

			// 해집단에 저장
			Population population = new Population();
			for (int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
				population.setIndividual(i, individuals[i], fitnessArr[i]);
			}

			int numOfGeneration = 0;
			while (numOfGeneration <= ConstOfGA.NUMOFMAXIMUMGENERATIONS) {
				// GA1. Selection
				int selectionNum1, selectionNum2;

				Selection s1 = new Selection(population, -1);
				selectionNum1 = s1.selection();
				Selection s2 = new Selection(population, selectionNum1);
				selectionNum2 = s2.selection();

				ParentIndividuals parentIndividuals = new ParentIndividuals(population.getIndividual(selectionNum1),
						population.getIndividual(selectionNum2), selectionNum1, selectionNum2,
						population.getFitnessOfIndividual(selectionNum1), population.getFitnessOfIndividual(selectionNum2));

				// GA2. CrossOver
				Crossover crossover = new Crossover(parentIndividuals);
				crossover.crossover();

				Individual[] childIndividualArr = new Individual[4];
				childIndividualArr[0] = crossover.getChildIndividualOne();
				childIndividualArr[1] = crossover.getChildIndividualTwo();
				childIndividualArr[2] = crossover.getChildIndividualThree();
				childIndividualArr[3] = crossover.getChildIndividualFour();

				double[] childFitnessArr = new double[4];

				// GA3. Mutation
				Mutation mutation;
				for (int i = 0; i < 4; i++) {
					mutation = new Mutation(childIndividualArr[i]);
					mutation.mutation();
				}

				// GA4. Adjustment
				for (int i = 0; i < 4; i++) {
					// System.out.println("조정연산시작" + i);
					ad = new Adjustment(childIndividualArr[i], givenLength);
					ad.adjustment();
					cl = new CalLength(childIndividualArr[i]);
					childIndividualArr[i].setLength(cl.getLength());
					calFitness = new CalFitness(childIndividualArr[i], givenLength);
					childFitnessArr[i] = calFitness.calFitness();
				}

				// GA5. Local Search: Steiner, Terminal
				LocalSearch_Steiner localSearch_Steiner;
				LocalSearch_Terminal localSearch_Terminal;

				for (int i = 0; i < 4; i++) {
					localSearch_Steiner = new LocalSearch_Steiner(childIndividualArr[i], givenLength);
					localSearch_Steiner.localSearch();
					localSearch_Terminal = new LocalSearch_Terminal(childIndividualArr[i], givenLength);
					localSearch_Terminal.localSearch();
					calFitness = new CalFitness(childIndividualArr[i], givenLength);
					childFitnessArr[i] = calFitness.calFitness();
				}

				ChildIndividuals childIndividuals = new ChildIndividuals(childIndividualArr[0], childIndividualArr[1],
						childIndividualArr[2], childIndividualArr[3], childFitnessArr);

				// GA6. Replacement
				Replacement replacement = new Replacement(population, parentIndividuals, childIndividuals);
				replacement.replacement();

				if(numOfGeneration % 100 == 0) {
					System.out.print(".");
				}
				
				if (isPart2 == false) {
					SwitchCondition sc = new SwitchCondition(population, givenLength);
					if (sc.switchConst() == true) {
						isPart2 = true;
						int maximumTerminal = 0;
						for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
							if(population.getIndividual(i).getNumOfTerminal() > maximumTerminal) {
								maximumTerminal = population.getIndividual(i).getNumOfTerminal();
							}
						}						
						switchMaximumTerminals[cycle] = maximumTerminal;
					}
				} 
				else {
					TerminateCondition tc = new TerminateCondition(population);
					if (tc.isTerminate() == true) {
						break;
					}
				}
				numOfGeneration++;
			}
			
			ConstOfGA.initValues();

			long endTime = System.currentTimeMillis();
			double totalTime = ( endTime - startTime ) / 1000.0;
			
			int maximumTerminal = 0;
			int indexOfbestIndividual = 0;
			for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
				if(population.getIndividual(i).getNumOfTerminal() > maximumTerminal) {
					maximumTerminal = population.getIndividual(i).getNumOfTerminal();
					indexOfbestIndividual = i;
				}
			}
			
			numOfGenerations[cycle] = numOfGeneration;
			time[cycle] = totalTime;
			try {
				bestIndividuals[cycle] = (Individual) population.getIndividual(indexOfbestIndividual).clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
		
		PrintFinalResult p = new PrintFinalResult();
		try {
			p.print(numOfGenerations, time, switchMaximumTerminals, bestIndividuals);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(true) {
			if(input.nextLine().equals("end")) {
				break;
			}
		}
		input.close();

	}
}
