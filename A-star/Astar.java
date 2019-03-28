import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;

public class Astar {

	public static void main(String args[]) throws IOException{

		//Creating start node
		Grid initial = new Grid();
		ArrayList<Integer> initialState = new ArrayList<>();
		System.out.println("Enter Initial state : \nEx: 1,2,3,4,6,7,8,5,0");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));  
		String initialStr = reader.readLine();
		String[] initialArr = initialStr.split(",");
		for(int i=0; i<initialArr.length; i++) {
			initialState.add(Integer.parseInt(initialArr[i]));
		}
		initial.state = initialState;
		initial.parent = null;
		initial.move = null;
		initial.priority = 0;
		initial.distance = -1;
		
		//Creating goal node
		Grid goal = new Grid();
		ArrayList<Integer> goalState = new ArrayList<>();
		System.out.println("Enter Goal state : \nEx: 1,2,3,4,5,6,7,8,0");
		String goalStr = reader.readLine();
		String[] goalArr = goalStr.split(",");
		for(int i=0; i<goalArr.length; i++) {
			goalState.add(Integer.parseInt(goalArr[i]));
		}
		goal.state = goalState;
		goal.parent = null;
		goal.distance = -1;
		goal.move = null;
		
		/*
		 * For testing 1
		 * Initial - 0,1,3,4,2,5,7,8,6
		 * Goal - 1,2,3,4,5,6,7,8,0
		 * 
		 * For testing 2
		 * Initial - 1,2,3,7,4,5,6,8,0
		 * Goal - 1,2,3,8,6,4,7,5,0
		 * 
		 * For testing 3
		 * Initial - 1,2,3,7,8,0,4,5,6
		 * Goal - 1,2,3,4,5,6,7,8,0
		 */
		System.out.println("\n--------------Mispalced Tiles--------------\n");
		AStar(initial, goal, 1);
		System.out.println("\n--------------Manhattan distance--------------\n");
		AStar(initial, goal, 2);
		
	}
	/*
	 * A* algorithm to search the path using heuristic function.
	 * initial - Initial state of the grid
	 * goal - goal state of the grid
	 * heuristicType - h(n) - 1 denotes Misplaced Tiles and 2 denotes Manhattan distance
	 * 
	 */
	public static void AStar(Grid initial, Grid goal, int heuristicType) {

		//To calculate time taken to find the solution
		final long startTime = System.nanoTime();

		//To maintain list of possible states for the current node
		Grid[] states = new Grid[4];
		//To store goal node when found
		Grid goalNodeFound = new Grid();
		goalNodeFound = null;
		//Using Stack to store operators
		Stack stack = new Stack();
		Grid current = new Grid();
		//To keep track of visited nodes
		LinkedList<ArrayList<Integer>> visited = new LinkedList<ArrayList<Integer>>();
		int count = 0, generated=0;
		//To compare each node's priority to sort the elements in the Priority Queue
		Comparator<Grid> comparator = new Grid();
		PriorityQueue<Grid> pQ = new PriorityQueue<Grid>(100, comparator);
		heuristicFunction(initial, goal, heuristicType);
		pQ.add(initial);
		visited.add(initial.state);

		while(!pQ.isEmpty()){
			//Taking node with minimum cost
			current = pQ.remove();
			current.print(heuristicType);
			//To find all possible states of the node
			states = findPossibleStates(current);

			for(int i = 0; i<=3; i++){
				if(states[i] != null){
					generated++;
					if (states[i].state.equals(goal.state)){
						goalNodeFound = states[i];
						goalNodeFound.print(heuristicType);
						break;
					}
					else{
						//Check if the node is already generated
						if(!visited.contains(states[i].state)){
							//calculate depth of the current node
							states[i].distance = current.distance + 1;	
							//Add current state to the visited list
							visited.add(states[i].state);
							//Calculate cost f(n) = g(n) + h(n)
							states[i].priority = states[i].distance + heuristicFunction(states[i], goal, heuristicType);
							pQ.add(states[i]);
						}
					}
				} 
			}
			if(goalNodeFound != null) {
				break;
			}

		}

		while (goalNodeFound.parent != null){
			if(goalNodeFound.move != null){
				count++;
				stack.push(goalNodeFound.move);
			}
			goalNodeFound = goalNodeFound.parent;
		}

		System.out.println("Operators : ");
		//To print operators
		while(!stack.isEmpty()){
			System.out.println(stack.pop());
		}
		
		System.out.println("\n"+generated + " Nodes generated.");
		System.out.println(visited.size() + " Nodes explored.");
		System.out.println("Path length : "+count);
		final long duration = System.nanoTime() - startTime;
		System.out.println("\nTime taken to find the solution");
		System.out.println(duration/1000000000.0 + " s");

}
	/* h(n) -- Heuristic function 1 - Misplaced Tiles
	 * Checks whether the value in current node's each tile position matches with the goal node's tile position.
	*/
	public static int misplacedTitles(Grid board, Grid goalBoard) {
		int NoOfMisplacedTitles = 0; 
		for(int i=0; i<9; i++) {
			if(goalBoard.state.get(i) != 0 && board.state.get(i) != goalBoard.state.get(i)) {
				NoOfMisplacedTitles++;
			}
		}
		board.NoOfMisplacedTitles = NoOfMisplacedTitles;
		return NoOfMisplacedTitles;
	}
	/* h(n) -- Heuristic function 2 - Manhattan Distance
	 * Calculate position of the tiles in current node and goal node by calculating Goal Row, Goal Column, Initial Row, Initial Column and then add or subtract the values based on the position of the tiles
	 */
	public static int manhattanDistance(Grid board, Grid goalBoard) {
		int manhattanDistance = 0;
		for(int i=0; i<9; i++) {
			int temp1 = goalBoard.state.get(i);
			if(temp1 != 0) {
				int goalIndex = i;
				int initialIndex = board.state.indexOf(temp1);
				if(goalIndex != initialIndex) {
					int initialRow = initialIndex / 3;
					int goalRow = goalIndex / 3;

					int initialColumn = initialIndex % 3;
					int goalColumn = goalIndex % 3;

					if(initialColumn == goalColumn) {
						manhattanDistance += Math.abs(initialRow - goalRow);
					}else if(initialRow == goalRow) {
						manhattanDistance += Math.abs(initialColumn - goalColumn);
					}else {
						manhattanDistance += Math.abs(initialRow - goalRow) + Math.abs(initialColumn - goalColumn);
					}
				}
			}
		}
		board.manhattanDistance = manhattanDistance;
		return manhattanDistance;
	}
	
	private static int heuristicFunction(Grid currentGrid, Grid goal, int heuristicType) {
		int cost = 0;
		if(heuristicType ==1) {
			cost = misplacedTitles(currentGrid, goal);
		}else if(heuristicType ==2){
			cost = manhattanDistance(currentGrid, goal);
		}
		return cost;
	}

	private static Grid[] findPossibleStates(Grid state) {
		// TODO Auto-generated method stub
		Grid state1,state2,state3,state4;
		
		state1 = Up(state);
		state2 = Down(state);
		state3 = Left(state);
		state4 = Right(state);
		
		Grid[] states = {state1, state2, state3, state4};
		
		return states;
	}
		
	@SuppressWarnings("unchecked")
	private static Grid Right(Grid grid) {
		// TODO Auto-generated method stub
		int space = grid.state.indexOf(0);
		ArrayList<Integer> childState;
		int temp;
		Grid childGrid = new Grid();
		
		if (space != 2 && space != 5 && space != 8) {
			childState = (ArrayList<Integer>) grid.state.clone();
			temp = childState.get(space+1);
			childState.set(space+1,0);
			childState.set(space,temp);			
			childGrid.state = childState;
			childGrid.parent = grid;
			childGrid.distance = grid.distance + 1;
			childGrid.move = "RIGHT";
			return childGrid;
		}
		else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static Grid Left(Grid grid) {
		// TODO Auto-generated method stub
		int space = grid.state.indexOf(0);
		ArrayList<Integer> childState;
		int temp;
		Grid childGrid = new Grid();
		
		if (space != 0 && space != 3 && space != 6) {
			childState = (ArrayList<Integer>) grid.state.clone();
			temp = childState.get(space-1);
			childState.set(space-1,0);
			childState.set(space,temp);			
			childGrid.state = childState;
			childGrid.parent = grid;
			childGrid.distance = grid.distance + 1;
			childGrid.move = "LEFT";
			return childGrid;
		}
		else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static Grid Down(Grid grid) {
		// TODO Auto-generated method stub
		int space = grid.state.indexOf(0);
		ArrayList<Integer> childState;
		int temp;
		Grid childGrid = new Grid();
		
		if (space <= 5) {
			childState = (ArrayList<Integer>) grid.state.clone();
			temp = childState.get(space+3);
			childState.set(space+3,0);
			childState.set(space,temp);			
			childGrid.state = childState;
			childGrid.parent = grid;
			childGrid.distance = grid.distance + 1;
			childGrid.move = "DOWN";
			return childGrid;
		}
		else{
			return null;
		}
	}

	private static Grid Up(Grid grid) {
		// TODO Auto-generated method stub
		int space = grid.state.indexOf(0);
		ArrayList<Integer> childState;
		int temp;
		Grid childGrid = new Grid();
		
		if (space > 2) {
			childState = (ArrayList<Integer>) grid.state.clone();
			temp = childState.get(space-3);
			childState.set(space-3,0);
			childState.set(space,temp);			
			childGrid.state = childState;
			childGrid.parent = grid;
			childGrid.distance = grid.distance + 1;
			childGrid.move = "UP";
			return childGrid;
		}
		else{
			return null;
		}
	}
	
}
