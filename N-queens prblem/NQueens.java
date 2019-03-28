/**
 * @file EightQueens.java
 * @author nsquires
 * Solves the eight queens problem using various AI techniques
 */
import java.util.*;
import java.text.NumberFormat;

public class NQueens {
	
	public NQueens(){
	}
	static int n;
	public static void main(String[] args){
		NQueens board = new NQueens();
		int numberOfRuns = 500;
		System.out.println("Enter number of Queens:");
    	Scanner in = new Scanner(System.in);
    	n = in.nextInt();
		int hillClimbNodes=0, hillClimbNodesWOSW=0, randomRestartNodes=0, steepHillClimbNodes=0, randomRestartWOSWNodes=0;
		int hillClimbSuccesses=0,  hillClimbFailures=0, hillClimbSuccessesWOSW=0, hillClimbFailuresWOSW=0, randomRestartSuccesses=0, randomRestartWOSWSuccesses=0, steepHillClimbSuccesses=0, steepHillClimbFailures=0;
		int avgSuccessSteps = 0, avgFailureSteps = 0, avgSuccessWOSW=0, avgFailureWOSW=0;
		int steepAvgSuccessSteps = 0, steepAvgFailureSteps = 0;
		int noOfRestarts=0, noOfRestartsWOSW=0, steepRandCount=0, hillRandCount=0;
		int randomRestartSteps=0, randomRestartWOSWSteps=0;
		int randomNumbers[] = new int[3];
		randomNumbers[0]=(int)(Math.random()*Integer.MAX_VALUE)%100+100;
		randomNumbers[1]=(int)(Math.random()*Integer.MAX_VALUE)%100+200;
		randomNumbers[2]=(int)(Math.random()*Integer.MAX_VALUE)%100+300;
		
		for(int i=0; i<numberOfRuns; i++){
			Queen[] startBoard = board.generateBoard();
			
			//Steepest Hill Climbing
			HillClimbingAlgorithm steepBoardSolver = new HillClimbingAlgorithm(startBoard, n);
			Board steepHillSolved = steepBoardSolver.steepestHillClimbing();
			if(steepHillSolved.getHeuristic()==0){
				steepAvgSuccessSteps += steepBoardSolver.searchSeq.size();
				steepHillClimbSuccesses++;
			}else {
				steepAvgFailureSteps += steepBoardSolver.searchSeq.size();
				steepHillClimbFailures++;
			}
			ArrayList<Board> steepSearchSeq = steepBoardSolver.searchSeq;
			if(steepRandCount < 3 && randomNumbers[steepRandCount] == i) {
				System.out.println("-----Steepest Hill Climbing-----");
				for(int k=0; k<steepSearchSeq.size(); k++) {
					System.out.println(steepSearchSeq.get(k).toString());
				}
				System.out.println(steepHillSolved.toString());
				steepRandCount++;
			}
			steepHillClimbNodes += steepBoardSolver.getNodesGenerated();

			
			
			//Hill Climbing Algorithm with sideways
			HillClimbingAlgorithm boardSolver = new HillClimbingAlgorithm(startBoard, n);
			Board hillSolved = boardSolver.hillClimbingWithSideWays();
			
			if(hillSolved.getHeuristic()==0){
				avgSuccessSteps += boardSolver.searchSeq.size();
				hillClimbSuccesses++;
			}else {
				//System.out.println("Each Failure size : "+boardSolver.searchSeq.size());
				avgFailureSteps += boardSolver.searchSeq.size();
				hillClimbFailures++;
			}
			hillClimbNodes += boardSolver.getNodesGenerated();
			
			ArrayList<Board> searchSeq = boardSolver.searchSeq;
			if(hillRandCount < 3 && randomNumbers[hillRandCount] == i) {
				System.out.println("-----Hill Climbing with sideways-----");
				for(int k=0; k<searchSeq.size(); k++) {
					System.out.println(searchSeq.get(k).toString());
				}
				System.out.println(hillSolved.toString());
				hillRandCount++;
			}
			
			
			//Random Restart with sideways
			RandomRestart randomRestart = new RandomRestart(startBoard, n);
			Board randomSolved = randomRestart.randomRestartWithSideWays();
			if(randomSolved.getHeuristic()==0){
				randomRestartSuccesses++;
			}
			randomRestartNodes += randomRestart.getNodesGenerated();
			noOfRestarts += randomRestart.noOfRestarts;
			randomRestartSteps += randomRestart.steps;
			
			
			//Random Restart without sideways
			RandomRestart randomRestartWOSW = new RandomRestart(startBoard, n);
			Board randomSolvedWithoutSideways = randomRestartWOSW.randomRestartWithoutSideWays();
			if(randomSolvedWithoutSideways.getHeuristic()==0){
				randomRestartWOSWSuccesses++;
			}
			randomRestartWOSWNodes += randomRestartWOSW.getNodesGenerated();
			noOfRestartsWOSW += randomRestartWOSW.noOfRestarts;
			randomRestartWOSWSteps += randomRestartWOSW.steps;
		}
		
		NumberFormat fmt = NumberFormat.getPercentInstance();

		//System.out.println("\n\navgFailureSteps: "+hillClimbFailures+"\n\n");
		
		//Steepest Hill Climbing
		double steepHillClimbPercent = (double)steepHillClimbSuccesses/(double)numberOfRuns;
		double steepHillClimbFailurePercent = (double)steepHillClimbFailures/(double)numberOfRuns;
		System.out.println("\nSteepest Hill climbing:\n");
		//System.out.println("Nodes generated: "+steepHillClimbNodes);
		System.out.println("Success rate: "+fmt.format(steepHillClimbPercent));
		System.out.println("Failure rate: "+fmt.format(steepHillClimbFailurePercent));
		System.out.println("Average successes: "+ steepAvgSuccessSteps/steepHillClimbSuccesses);
		System.out.println("Average failure: "+ steepAvgFailureSteps/steepHillClimbFailures);
		
		
		//Hill Climbing with sideways
		double hillClimbPercent = (double)hillClimbSuccesses/(double)numberOfRuns;
		double hillClimbFailurePercent = (double)hillClimbFailures/(double)numberOfRuns;
		System.out.println("\n\nHill climbing with sideways:\n");
		System.out.println("Nodes generated: "+hillClimbNodes);
		System.out.println("Hill climb successes: "+hillClimbSuccesses);
		System.out.println("Success rate: "+fmt.format(hillClimbPercent));
		System.out.println("Failure rate: "+fmt.format(hillClimbFailurePercent));
		System.out.println("Average successes: "+ avgSuccessSteps/hillClimbSuccesses);
		System.out.println("Average failures: "+ avgFailureSteps/hillClimbFailures);
		
		/*
		//Hill Climbing without sideways
		double hillClimbPercentWOSW = (double)hillClimbSuccessesWOSW/(double)numberOfRuns;
		double hillClimbFailurePercentWOSW = (double)hillClimbFailuresWOSW/(double)numberOfRuns;
		System.out.println("\n\nHill climbing without sideways:\nNodes: "+hillClimbNodesWOSW);
		System.out.println("Hill climb successes: "+hillClimbSuccessesWOSW);
		System.out.println("Success rate: "+fmt.format(hillClimbPercentWOSW));
		System.out.println("Failure rate: "+fmt.format(hillClimbFailurePercentWOSW));
		System.out.println("Average successes: "+ avgSuccessWOSW/hillClimbSuccessesWOSW);
		System.out.println("Average failures: "+ avgFailureWOSW/hillClimbFailuresWOSW);
		*/
		
		//Random Restart with sideways
		double randomRestartPercent = (double)(randomRestartSuccesses/numberOfRuns);
		System.out.println("\n\nRandom Restart with sideways:\n");
		//System.out.println("Nodes generated: "+randomRestartNodes);
		System.out.println("Success rate: "+fmt.format(randomRestartPercent));
		System.out.println("Number of Restarts: "+noOfRestarts/numberOfRuns);
		System.out.println("Number of steps: "+randomRestartSteps/numberOfRuns);
		
		
		//Random Restart without sideways
		double randomRestartWOSWPercent = (double)(randomRestartWOSWSuccesses/numberOfRuns);
		System.out.println("\n\nRandom Restart without sideways:\n");
		//System.out.println("Nodes generated: "+randomRestartWOSWNodes);
		System.out.println("Success rate: "+fmt.format(randomRestartWOSWPercent));
		System.out.println("Number of restarts: "+noOfRestartsWOSW/numberOfRuns);
		System.out.println("Number of steps: "+randomRestartWOSWSteps/numberOfRuns);
		
	}
	
	/**
	 * The starter board
	 * @return Queen[]
	 */
	public Queen[] generateBoard(){
		Queen[] start = new Queen[n];
		Random gen = new Random();
		
		for(int i=0; i<n; i++){
			start[i] = new Queen(gen.nextInt(n),i, n);
		}
		return start;
	}
}
