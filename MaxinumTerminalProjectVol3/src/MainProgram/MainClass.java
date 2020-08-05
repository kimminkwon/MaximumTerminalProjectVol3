package MainProgram;

import Populations.*;
import PopulationsInfo.*;
import ControlOrLength.*;

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
		
		Population population = new Population();
		System.out.println("* Populations Info...");
		for(int i = 0; i < ConstOfGA.SIZEOFPOPULATION; i++) {
			System.out.println(individuals[i]);
		}
		
		CalFitness adj = new CalFitness(individuals[4], givenLength);
		System.out.println("before Adjust Length: " + adj.getLength());
		adj.adjustment();
		
		CalFitness cal = new CalFitness(individuals[4], givenLength);
		System.out.println("Adjusted length: " + cal.getLength());
	}
}
