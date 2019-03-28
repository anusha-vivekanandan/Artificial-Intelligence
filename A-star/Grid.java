import java.util.ArrayList;
import java.util.Comparator;

public class Grid implements Comparator<Grid>{
	
	ArrayList<Integer> state;
	//Parent of the node
	Grid parent;
	//depth of the node from root
	int distance;
	//Operation done on the node
	String move;
	//f(n) = g(n) + h(n)
	public int priority;
	//Heuristic 1 - Misplaced tiles
	public int NoOfMisplacedTitles;
	//Heuristic 1 - Manhattan distance
	public int manhattanDistance;
	
	public void print(int heuristicType) {
		for(int i=0; i< 9; i++)
		{
			if(i!=0 && i%3 == 0)
				System.out.print("\n");
				System.out.print(this.state.get(i)+"  ");
		}
		if(heuristicType == 1) {
			System.out.println("\nMisplacedTitles: "+this.NoOfMisplacedTitles+"\n");
		}else {
			System.out.println("\nManhattan Distance: "+this.manhattanDistance+"\n");	
		}
	}
	
	@Override
	//To compare each node's priority to sort the elements in the Priority Queue
	public int compare(Grid grid1, Grid grid2) {
		// TODO Auto-generated method stub
		if (grid1.priority > grid2.priority){
			return 1;
		}
		if (grid1.priority < grid2.priority){
			return -1;
		}
		return 0;
	}
}
