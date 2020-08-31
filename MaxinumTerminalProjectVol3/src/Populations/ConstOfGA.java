package Populations;

public class ConstOfGA {
	// 출력할 것
	// 몇번째 사이클인지, 변수의 값은 어떤지, 실험 제목
	// 걸린 시간, 대조군 값, 최대 터미널 값, 가장 좋은 해의 정보
	public static String TOPIC = "";
	public static String INPUTNAME = "";
	

	public static int NUMOFCYCLE = 0;
	public static double TOTALTIME = 0;
	public static double CONTROLGROUPVALUE = 0;
	public static int GIVENLENGTH = 0;

	public static int SIZEOFPOPULATION = 150;
	public static int NUMOFMAXIMUMGENERATIONS = 40000;
	public static double PROBOFSWITCHNEWSTEINERMAKE = 0.5;
	
	public static double PROBOFSWITCHCONDITION = 0.5;
	public static int NUMOFTERMINALS = 1000;
	public static double GIVENLENGTHVALUE = 0.5;
	public static double SCALROFIGP = 1.0;
	
	public static double ALPHA = 0.2;
	public static double BETA = 0.7;
	public static double GAMMA = 0.1;
	public static double ALPHA_PART2 = 0.2;
	public static double BETA_PART2 = 0.7;
	public static double GAMMA_PART2 = 0.1;
	
	public static double MUTATIONPORB_TERMINAL = 0.05;
	public static double MUTATIONPORB_STEINER = 0.05;
	public static int NUMOFMUATATION_TERMINAL = 5;
	public static int NUMOFMUATATION_STEINER = 2;
	public static double PROBSTEINERDELETE = 0.1;
	public static double PROBSTEINERADD = 0.5;
	public static double PROBSTEINERADDPARTTWO = 0.5;
	
	
	public static void initValues() {
		PROBOFSWITCHNEWSTEINERMAKE = 0.5;
		PROBSTEINERDELETE = 0.1;
		PROBSTEINERADD = 0.5;
		PROBSTEINERADDPARTTWO = 0.5;
	}
	
	public static String getINPUTNAME() {
		return INPUTNAME;
	}

	public static void setINPUTNAME(String iNPUTNAME) {
		INPUTNAME = iNPUTNAME;
	}
	
	public static int getSIZEOFPOPULATION() {
		return SIZEOFPOPULATION;
	}

	public static void setSIZEOFPOPULATION(int sIZEOFPOPULATION) {
		SIZEOFPOPULATION = sIZEOFPOPULATION;
	}
	
	public static int getGIVENLENGTH() {
		return GIVENLENGTH;
	}

	public static void setGIVENLENGTH(int gIVENLENGTH) {
		GIVENLENGTH = gIVENLENGTH;
	}
	
	public static void setFitnessPart1(double a, double b, double r) {
		ALPHA = a; BETA = b; GAMMA = r;
	}
	
	public static void setFitnessPart2(double a, double b, double r) {
		ALPHA_PART2 = a; BETA_PART2 = b; GAMMA_PART2 = r;
	}
	public static String getTOPIC() {
		return TOPIC;
	}

	public static int getNUMOFCYCLE() {
		return NUMOFCYCLE;
	}

	public static double getTOTALTIME() {
		return TOTALTIME;
	}

	public static double getCONTROLGROUPVALUE() {
		return CONTROLGROUPVALUE;
	}

	public static void setTOPIC(String tOPIC) {
		TOPIC = tOPIC;
	}

	public static void setNUMOFCYCLE(int nUMOFCYCLE) {
		NUMOFCYCLE = nUMOFCYCLE;
	}

	public static void setTOTALTIME(double tOTALTIME) {
		TOTALTIME = tOTALTIME;
	}

	public static double getPROBOFSWITCHCONDITION() {
		return PROBOFSWITCHCONDITION;
	}

	public static void setPROBOFSWITCHCONDITION(double pROBOFSWITCHCONDITION) {
		PROBOFSWITCHCONDITION = pROBOFSWITCHCONDITION;
	}

	public static void setCONTROLGROUPVALUE(double cONTROLGROUPVALUE) {
		CONTROLGROUPVALUE = cONTROLGROUPVALUE;
	}

	public static double getALPHA_PART2() {
		return ALPHA_PART2;
	}

	public static double getBETA_PART2() {
		return BETA_PART2;
	}

	public static double getGAMMA_PART2() {
		return GAMMA_PART2;
	}

	public static void setALPHA_PART2(double aLPHA_PART2) {
		ALPHA_PART2 = aLPHA_PART2;
	}

	public static void setBETA_PART2(double bETA_PART2) {
		BETA_PART2 = bETA_PART2;
	}

	public static void setGAMMA_PART2(double gAMMA_PART2) {
		GAMMA_PART2 = gAMMA_PART2;
	}
	
	public static int getNumofterminals() {
		return NUMOFTERMINALS;
	}
	
	public static int getSizeofpopulation() {
		return SIZEOFPOPULATION;
	}
	
	public static double getGivenlengthvalue() {
		return GIVENLENGTHVALUE;
	}
	
	public static double getScalrofigp() {
		return SCALROFIGP;
	}
	
	public static double getAlpha() {
		return ALPHA;
	}
	
	public static double getBeta() {
		return BETA;
	}
	
	public static double getGamma() {
		return GAMMA;
	}
	
	public static double getMutationporbTerminal() {
		return MUTATIONPORB_TERMINAL;
	}
	
	public static double getMutationporbSteiner() {
		return MUTATIONPORB_STEINER;
	}
	
	public static int getNumofmuatationTerminal() {
		return NUMOFMUATATION_TERMINAL;
	}
	
	public static int getNumofmuatationSteiner() {
		return NUMOFMUATATION_STEINER;
	}
	
	public static double getProbsteinerdelete() {
		return PROBSTEINERDELETE;
	}
	
	public static double getProbsteineradd() {
		return PROBSTEINERADD;
	}
	
	public static double getProbsteineraddparttwo() {
		return PROBSTEINERADDPARTTWO;
	}
	
	public static void setNumofterminals(int numofterminals) {
		NUMOFTERMINALS = numofterminals;
	}
	
	public static void setGivenlengthvalue(double givenlengthvalue) {
		GIVENLENGTHVALUE = givenlengthvalue;
	}
	
	public static void setScalrofigp(double scalrofigp) {
		SCALROFIGP = scalrofigp;
	}
	
	public static void setAlpha(double alpha) {
		ALPHA = alpha;
	}
	
	public static void setBeta(double beta) {
		BETA = beta;
	}
	
	public static void setGamma(double gamma) {
		GAMMA = gamma;
	}
	
	public static void setMutationporbTerminal(double mutationporbTerminal) {
		MUTATIONPORB_TERMINAL = mutationporbTerminal;
	}
	
	public static void setMutationporbSteiner(double mutationporbSteiner) {
		MUTATIONPORB_STEINER = mutationporbSteiner;
	}
	
	public static void setNumofmuatationTerminal(int numofmuatationTerminal) {
		NUMOFMUATATION_TERMINAL = numofmuatationTerminal;
	}
	
	public static void setNumofmuatationSteiner(int numofmuatationSteiner) {
		NUMOFMUATATION_STEINER = numofmuatationSteiner;
	}
	
	public static void setProbsteinerdelete(double probsteinerdelete) {
		PROBSTEINERDELETE = probsteinerdelete;
	}
	
	public static void setProbsteineradd(double probsteineradd) {
		PROBSTEINERADD = probsteineradd;
	}
	
	public static void setProbsteineraddparttwo(double probsteineraddparttwo) {
		PROBSTEINERADDPARTTWO = probsteineraddparttwo;
	}

	
}