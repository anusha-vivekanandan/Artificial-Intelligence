/**
 * @file HillClimbing.java
 * @author Natasha Squires <nsquires@upei.ca>
 * Implementation of the hill climbing algorithm
 */
import java.util.*;

public class HillClimbingAlgorithm {
	private Queen[] startState;
	private Board start; //start state
	private int nodesGenerated;
	public int successSteps=0, failureSteps=0, queens;
	public ArrayList<Board> searchSeq = new ArrayList<Board>();
	
	/**
	 * Constructor
	 */
	public HillClimbingAlgorithm(int n){
		queens= n;
		start = new Board(n); //empty start node
		startState = new Queen[n]; //empty start state
		startState();
		nodesGenerated=0;
		successSteps = 0;
		failureSteps=0;
	}
	
	/**
	 * Constructs HillClimbing with a starting board
	 * @param s
	 */
	public HillClimbingAlgorithm(Queen[] s, int n){
		start = new Board(n);
		startState = new Queen[n];
		for(int i=0; i<s.length; i++){
			startState[i] = new Queen(s[i].getRow(), s[i].getColumn(), s[i].getQueens());
		}
		start.setState(startState);
		start.computeHeuristic();
		
		nodesGenerated=0;
	}
	
	/**
	 * Sets the starting state
	 */
	public void startState(){
		//sets up a pseudo random start state
		Random gen = new Random();
		for(int i=0; i<queens; i++){
			startState[i] = new Queen(gen.nextInt(queens), i, queens);
		}
		start.setState(startState);
		start.computeHeuristic();
	//	System.out.println("start:\n"+start);
	}
	
	/**
	 *  Steepest hill climbing algorithm
	 * @return Board
	 */
	public Board steepestHillClimbing(){
		Board currentNode = start;
		while(true){
			ArrayList<Board> successors = currentNode.generateNeighbours(currentNode);
			nodesGenerated+=successors.size();
			
			Board nextNode = null;
			
			for(int i=0; i<successors.size(); i++){
				if(successors.get(i).compareTo(currentNode) < 0){
					currentNode = nextNode = successors.get(i);
				}
			}
			searchSeq.add(currentNode);

			if(nextNode==null) {
				return currentNode;
			}
		}
	}
	
	/**
	 *  Hill climbing algorithm without sideways
	 * @return Board
	 */
	public Board hillClimbingWithoutSideWays(){
		Board currentNode = start;
		while(true){
			ArrayList<Board> successors = currentNode.generateNeighbours(currentNode);
			nodesGenerated+=successors.size();
			
			Board nextNode = null;
			
			for(int i=0; i<successors.size(); i++){
				if(successors.get(i).compareTo(currentNode) < 0){
					currentNode = nextNode = successors.get(i);
					//break;
				}
			}
			searchSeq.add(nextNode);

			if(nextNode==null) {
				return currentNode;
			}
		}
	}
	
	
	/**
	 *  hill climbing algorithm with sideways for Random Restart
	 * @return Board
	 */
	public Board hillClimbingWithSideWaysForRandom(){
		Board currentNode = start;
		boolean isSideWaysAllowed = true;
		int sideMoves = 0;
		while(true){
			ArrayList<Board> successors = currentNode.generateNeighbours(currentNode);
			nodesGenerated+=successors.size();
			
			Board nextNode = null;
			
			for(int i=0; i<successors.size(); i++){
				if(successors.get(i).compareTo(currentNode) < 0){
					nextNode = successors.get(i);
					searchSeq.add(nextNode);
					break;
				}
			}
			//Sideways
			if(nextNode==null && currentNode.getHeuristic() != 0) {
				for(int i=0; i<successors.size(); i++){ 
					int difference = successors.get(i).compareTo(currentNode);
					if(difference == 0 && (isSideWaysAllowed && sideMoves < 100)){
						sideMoves++;
						currentNode = nextNode = successors.get(i);
						//searchSeq.add(nextNode);
						break;
					}
				}
			}
			
			if(nextNode==null) {
				return currentNode;
			}
			currentNode = nextNode;
		}
	}
	

	/**
	 *  hill climbing algorithm with sideways
	 * @return Board
	 */
	public Board hillClimbingWithSideWays(){
		Board currentNode = start;
		boolean isSideWaysAllowed = true;
		int sideMoves = 0;
		while(true){
			ArrayList<Board> successors = currentNode.generateNeighboursSideWays(currentNode);
			nodesGenerated+=successors.size();
			
			Board nextNode = null;
			
			for(int i=0; i<successors.size(); i++){
				if(successors.get(i).compareTo(currentNode) < 0){
					currentNode = nextNode = successors.get(i);
					searchSeq.add(currentNode);
					break;
				}
			}
			//Sideways
			if(nextNode==null && currentNode.getHeuristic() != 0) {
				for(int i=0; i<successors.size(); i++){ 
					int difference = successors.get(i).compareTo(currentNode);
					if(difference == 0 && (isSideWaysAllowed && sideMoves < 100)){
						sideMoves++;
						nextNode = successors.get(i);
						//searchSeq.add(nextNode);
						ArrayList<Board> sideWaysSuccessors = nextNode.generateNeighboursSideWays(nextNode);
						nodesGenerated+=sideWaysSuccessors.size();
						for(int k=0; k<sideWaysSuccessors.size(); k++){ 
							if(sideWaysSuccessors.get(k).compareTo(nextNode) <= 0 && sideMoves < 30){
								//System.out.println("Better node found on i= "+i+" k= "+k+" sideWaysSuccessors : "+sideWaysSuccessors.get(k).getHeuristic()+" nextNode : "+nextNode.getHeuristic());
								searchSeq.add(nextNode);
								currentNode = nextNode = sideWaysSuccessors.get(k);
								searchSeq.add(currentNode);
								if(currentNode.getHeuristic() == 0) {
									return currentNode;
								}
								break;
							}
						}
					}
				}
			}
			
			if(nextNode==null) {
				return currentNode;
			}
			currentNode = nextNode;
		}
	}
	
	/**
	 * Returns the Board's state
	 * @return Node
	 */
	public Board getStartNode(){
		return start;
	}
	
	/**
	 * Returns amount of Boards generated
	 * @return int
	 */
	public int getNodesGenerated(){
		return nodesGenerated;
	}
}
