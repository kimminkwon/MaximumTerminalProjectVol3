package Populations;

public class ConstOfGA {
	public static final int SIZEOFPOPULATION = 5;
	public static int NUMOFTERMINALS = 20;
	public static double GIVENLENGTHVALUE = 0.5;
	public static double SCALROFIGP = 1.0;
	public static double ALPHA = 0.2;
	public static double BETA = 0.7;
	public static double GAMMA = 0.1;
	public static double MUTATIONPORB_TERMINAL = 0.5;
	public static double MUTATIONPORB_STEINER = 0.5;
	public static int NUMOFMUATATION_TERMINAL = 3;
	public static int NUMOFMUATATION_STEINER = 2;
	public static double PROBSTEINERDELETE = 0.5;
	public static double PROBSTEINERADD = 1.0;
	public static double PROBSTEINERADDPARTTWO = 0.5;
	
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