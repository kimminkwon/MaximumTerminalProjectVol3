package MainProgram;

import Populations.*;
import PopulationsInfo.*;
import ControlOrLength.*;
import GenenticAlgorithm.*;

import java.util.*;
import java.io.*;

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
		
		// 초기 해 생성1. GivenLength 계산 후 IGP 얻기
		GivenLength gl = new GivenLength();
		givenLength = gl.getGivenLength();
		IGP = gl.getIGP();
		System.out.println("* Given Length is " + givenLength + ", this length is " + ConstOfGA.GIVENLENGTHVALUE + " * Total Length " + gl.getTotalLength());
		System.out.println("* IGP (SCALR OF IGP * (givenLength / totalLength)): " + IGP);
		
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
		
		
	}
}
