package MainProgram;

import java.util.Arrays;
import java.util.Scanner;

import ControlGroup.ControlGroup;
import ControlGroup.ControlGroup_MST;
import ControlOrLength.GivenLength;
import GenenticAlgorithm.Adjustment;
import GenenticAlgorithm.ChildIndividuals;
import GenenticAlgorithm.Crossover;
import GenenticAlgorithm.LocalSearch_Steiner;
import GenenticAlgorithm.LocalSearch_Terminal;
import GenenticAlgorithm.Mutation;
import GenenticAlgorithm.ParentIndividuals;
import GenenticAlgorithm.Replacement;
import GenenticAlgorithm.Selection;
import Populations.ConstOfGA;
import Populations.Individual;
import Populations.MakeIndividual;
import Populations.Population;
import PopulationsInfo.CalFitness;
import PopulationsInfo.CalLength;
import PopulationsInfo.InputDataProcess;

public class MainClass {
	public static void main(String[] args) {
		// 필요한 변수 선언
		int givenLength;
		double IGP;
		
		// 입력 받기
		Scanner input = new Scanner(System.in);
		InputDataProcess idp = InputDataProcess.getInputDataProcess();
		
		for(int i = 0; i < ConstOfGA.NUMOFTERMINALS; i++) {
			int x = input.nextInt();
			int y = input.nextInt();		
			idp.setTerminal(i, x, y);
		}
		
		// 하난 그리드 생성
		idp.makeHananGrid();
		System.out.println("* HananGrid Info..");
		System.out.println("  - Vertical: " + idp.getHanan_vertical());
		System.out.println("  - Horizental: " + idp.getHanan_horizental());
		
		System.out.println("* OutofConvexHall for Steiner Point Info..");
		System.out.println(idp.getInOfConvexHallSteinerPoints());
		
		// 초기 해 생성1. GivenLength 계산 후 IGP 얻기
		GivenLength gl = new GivenLength();
		givenLength = gl.getGivenLength();
		IGP = gl.getIGP();
		System.out.println("* Given Length is " + givenLength + ", this length is " + ConstOfGA.GIVENLENGTHVALUE + " * Total Length " + gl.getTotalLength());
		System.out.println("* IGP (SCALR OF IGP * (givenLength / totalLength)): " + IGP);

		
		// ControlGroup 생성
		ControlGroup controlGroup = new ControlGroup_MST(); 
		int controlGroupValue = controlGroup.getControlGroupValue(givenLength);
		
		System.out.println("* ControlGroupValue is " + controlGroupValue);

		// 초기 해 생성2. Population 생성 (해 생성 -> 조정 -> 적합도)
		double[] igpArr = {1.0}; // igp는 배열로 선언 가능, 각 Index 범위에 따라 확률을 다르게 주고 싶을 때 사용
		
		MakeIndividual makeIndividual;
		Individual[] individuals = new Individual[ConstOfGA.SIZEOFPOPULATION];
		
		// 해 생성
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			makeIndividual = new MakeIndividual(IGP, igpArr);
			individuals[i] = new Individual(makeIndividual.getTerminalStatus(), makeIndividual.getSteinerPointStatus());
		}
		
		// 해 집단 확인
		System.out.println("\n* Before adjust Populations Info...");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.println(individuals[i]);
		}

		// 해 집단 조정연산
		Adjustment ad; 
		CalLength cl;
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			ad = new Adjustment(individuals[i], givenLength);
			ad.adjustment();
			cl = new CalLength(individuals[i]);
			individuals[i].setLength(cl.getLength());
			// System.out.println(i + "-th Individual Length: " + cl.getLength());
		}
		
		// 적합도 배열 생성
		double[] fitnessArr = new double[ConstOfGA.SIZEOFPOPULATION];
		CalFitness calFitness; 
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			calFitness = new CalFitness(individuals[i], givenLength);
			fitnessArr[i] = calFitness.calFitness();
		}

		// 해집단에 저장
		Population population = new Population();
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			population.setIndividual(i, individuals[i], fitnessArr[i]);
		}
		
		System.out.println("\n* After adjust Populations Info...");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.println(population.getIndividual(i));
			System.out.print("    Fitness: " + population.getFitnessOfIndividual(i) + "\n");
		}
		
		// GA1. Selection
		int selectionNum1, selectionNum2;
		
		Selection s1 = new Selection(population, -1);
		selectionNum1 = s1.selection();
		Selection s2 = new Selection(population, selectionNum1);
		selectionNum2 = s2.selection();

		ParentIndividuals parentIndividuals = new ParentIndividuals(population.getIndividual(selectionNum1), population.getIndividual(selectionNum2), selectionNum1, selectionNum2, population.getFitnessOfIndividual(selectionNum1), population.getFitnessOfIndividual(selectionNum2));
		
		System.out.println("\n* GA-1: Selection");
		System.out.println("  - Selected Index: " + selectionNum1 + ", " + selectionNum2);
		System.out.println(parentIndividuals.getParentIndividualOne());
		System.out.println(parentIndividuals.getParentIndividualTwo());
		
		// GA2. CrossOver
		Crossover crossover = new Crossover(parentIndividuals);
		crossover.crossover();
		
		Individual[] childIndividualArr = new Individual[4];
		childIndividualArr[0] = crossover.getChildIndividualOne();
		childIndividualArr[1] = crossover.getChildIndividualTwo();
		childIndividualArr[2] = crossover.getChildIndividualThree();
		childIndividualArr[3] = crossover.getChildIndividualFour();
		
		double[] childFitnessArr = new double[4];
		
		System.out.println("\n* GA-2: CrossOver");
		System.out.println("  - ChildIndividualOne...");
		System.out.println(childIndividualArr[0]);
		System.out.println("  - ChildIndividualTwo...");
		System.out.println(childIndividualArr[1]);
		System.out.println("  - ChildIndividualThree...");
		System.out.println(childIndividualArr[2]);
		System.out.println("  - ChildIndividualFour...");
		System.out.println(childIndividualArr[3]);
		
		// GA3. Mutation
		Mutation mutation;
		for(int i = 0; i < 4; i++) {
			mutation = new Mutation(childIndividualArr[i]);
			mutation.mutation();
		}
		
		System.out.println("\n* GA-3: Mutation");
		System.out.println("  - ChildIndividualOne...");
		System.out.println(childIndividualArr[0]);
		System.out.println("  - ChildIndividualTwo...");
		System.out.println(childIndividualArr[1]);
		System.out.println("  - ChildIndividualThree...");
		System.out.println(childIndividualArr[2]);
		System.out.println("  - ChildIndividualFour...");
		System.out.println(childIndividualArr[3]);
	
		// GA4. Adjustment
		for(int i = 0; i < 4; i++) {
			// System.out.println("조정연산시작" + i);
			ad = new Adjustment(childIndividualArr[i], givenLength);
			ad.adjustment();
			cl = new CalLength(childIndividualArr[i]);
			childIndividualArr[i].setLength(cl.getLength());
			calFitness = new CalFitness(childIndividualArr[i], givenLength);
			childFitnessArr[i] = calFitness.calFitness();
		}

		System.out.println("\n* GA4. Adjustment CrossOver");
		System.out.println("  - ChildIndividualOne...");
		System.out.println(childIndividualArr[0]);
		System.out.print("    Fitness: " + childFitnessArr[0] + "\n");
		
		System.out.println("  - ChildIndividualTwo...");
		System.out.println(childIndividualArr[1]);
		System.out.print("    Fitness: " + childFitnessArr[1] + "\n");
		
		System.out.println("  - ChildIndividualThree...");
		System.out.println(childIndividualArr[2]);
		System.out.print("    Fitness: " + childFitnessArr[2] + "\n");
		
		System.out.println("  - ChildIndividualFour...");
		System.out.println(childIndividualArr[3]);
		System.out.print("    Fitness: " + childFitnessArr[3] + "\n");
		
		// GA5. Local Search: Steiner, Terminal
		LocalSearch_Steiner localSearch_Steiner;
		LocalSearch_Terminal localSearch_Terminal;
		
		for(int i = 0; i < 4; i++) {
			localSearch_Steiner = new LocalSearch_Steiner(childIndividualArr[i], givenLength);
			localSearch_Steiner.localSearch();
			localSearch_Terminal = new LocalSearch_Terminal(childIndividualArr[i], givenLength);
			localSearch_Terminal.localSearch();
			calFitness = new CalFitness(childIndividualArr[i], givenLength);
			childFitnessArr[i] = calFitness.calFitness();
		}
		
		ChildIndividuals childIndividuals = new ChildIndividuals(childIndividualArr[0], childIndividualArr[1], childIndividualArr[2], childIndividualArr[3], childFitnessArr);
		
		System.out.println("\n* GA5. LocalSearch CrossOver");
		System.out.println("  - ChildIndividualOne...");
		System.out.println(childIndividuals.getIndexOfChildIndividual(0));
		System.out.print("    Fitness: " + childIndividuals.getFitnessOfChildIndividuals()[0] + "\n");
		
		System.out.println("  - ChildIndividualTwo...");
		System.out.println(childIndividuals.getIndexOfChildIndividual(1));
		System.out.print("    Fitness: " + childIndividuals.getFitnessOfChildIndividuals()[1] + "\n");
		
		System.out.println("  - ChildIndividualThree...");
		System.out.println(childIndividuals.getIndexOfChildIndividual(2));
		System.out.print("    Fitness: " + childIndividuals.getFitnessOfChildIndividuals()[2] + "\n");
		
		System.out.println("  - ChildIndividualFour...");
		System.out.println(childIndividuals.getIndexOfChildIndividual(3));
		System.out.print("    Fitness: " + childIndividuals.getFitnessOfChildIndividuals()[3] + "\n");
		
		// GA6. Replacement
		Replacement replacement = new Replacement(population, parentIndividuals, childIndividuals);
		replacement.replacement();
		
		System.out.println("\n* GA6. Replacement of Population");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.println(population.getIndividual(i));
			System.out.print("    Fitness: " + population.getFitnessOfIndividual(i) + "\n");
		}
		
	}
}
