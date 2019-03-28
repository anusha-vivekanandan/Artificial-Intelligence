/**
 * @file RandomRestart.java
 * @author Natasha Squires <nsquires@upei.ca>
 * Implements the random restart hill climbing algorithm
 */
public class RandomRestart {
	private HillClimbingAlgorithm hillClimber;
	private int nodesGenerated;
	private Board start;
	public int noOfRestarts=0, steps=0, n;
	
	/**
	 * Constructor
	 */
	public RandomRestart(Queen[] startBoard, int queens){
		n= queens;
		hillClimber = new HillClimbingAlgorithm(startBoard, queens);
		nodesGenerated = 0;
		noOfRestarts=0;
	}
	
	/**
	 * The random restart hill climbing algorithm without sideways
	 * @return
	 */
	public Board randomRestartWithoutSideWays(){
		Board currentNode = hillClimber.getStartNode();
		setStartNode(currentNode);
		int heuristic = currentNode.getHeuristic();
				
		while(heuristic!=0){
			Board nextNode = hillClimber.hillClimbingWithoutSideWays();
			nodesGenerated+=hillClimber.getNodesGenerated();
			heuristic = nextNode.getHeuristic();
			
			steps += hillClimber.searchSeq.size();
			if(heuristic!=0){ //restart
				hillClimber = new HillClimbingAlgorithm(n);
				noOfRestarts++;
			}else {
				currentNode = nextNode;
			}
		}
		return currentNode;
	}
	
	/**
	 * The random restart hill climbing algorithm with sideways
	 * @return
	 */
	public Board randomRestartWithSideWays(){
		Board currentNode = hillClimber.getStartNode();
		setStartNode(currentNode);
		int heuristic = currentNode.getHeuristic();
				
		while(heuristic!=0){
			Board nextNode = hillClimber.hillClimbingWithSideWaysForRandom();
			nodesGenerated+=hillClimber.getNodesGenerated();
			heuristic = nextNode.getHeuristic();
			
			if(heuristic!=0){ //restart
				steps += hillClimber.searchSeq.size();
				hillClimber = new HillClimbingAlgorithm(n);
				noOfRestarts++;
			}else {
				steps += hillClimber.searchSeq.size();
				currentNode = nextNode;
			}
		}
		return currentNode;
	}
	
	/**
	 * Sets the initial board
	 * @param n
	 */
	public void setStartNode(Board n){
		start = n;
	}
	
	/**
	 * Start set getter
	 * @return Node
	 */
	public Board getStartNode(){
		return start;
	}
	
	/**
	 * Returns the amount of nodes generated during the 
	 * random restart algorithm
	 * @return int
	 */
	public int getNodesGenerated(){
		return nodesGenerated;
	}
}
