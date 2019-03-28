/**
 * @file Node.java
 * @author Natasha Squires <nsquires@upei.ca>
 * Represents a Queen
 */
import java.util.*;

public class Board implements Comparable<Board>{
	private static int noOfQueens; //8 queens
	public Queen[] state; //the node's state
	private ArrayList<Board> neighbours;
	private int hn; //heuristic score
	
	/**
	 * Constructor
	 */
	public Board(int n){
		noOfQueens = n;
		state = new Queen[noOfQueens]; //empty state
		neighbours = new ArrayList<Board>(); //empty neighbour list
	} 
	
	/**
	 * Constructor which creates a copy of a node's state
	 * @param n
	 */
	public Board(Board n){
		state = new Queen[noOfQueens];
		neighbours = new ArrayList<Board>();
		for(int i=0; i<noOfQueens; i++)
			state[i] = new Queen(n.state[i].getRow(), n.state[i].getColumn(), n.state[i].getQueens());
		hn=0;
	}
	
	/**
	 * Generates the possible chess board configurations given a
	 * specific board state
	 * @param startState
	 */
	public ArrayList<Board> generateNeighbours(Board startState){
		int count=0;
		
		for(int i=0; i<noOfQueens; i++){
			for(int j=1; j<noOfQueens; j++){
				neighbours.add(count, new Board(startState));
				neighbours.get(count).state[i].moveDown(j);
				//make sure to compute its hn value
				neighbours.get(count).computeHeuristic();
				
				count++;
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Generates the possible chess board configurations given a
	 * specific board state
	 * @param startState
	 */
	public ArrayList<Board> generateNeighboursSideWays(Board startState){
		int count=0;
		
		for(int i=0; i<noOfQueens; i++){
			for(int j=1; j<noOfQueens; j++){
				neighbours.add(count, new Board(startState));
				neighbours.get(count).state[i].moveDownSideWays(j);
				neighbours.get(count).computeHeuristic();
				
				count++;
			}
		}
		
		return neighbours;
	}
	
	/**
	 * computes the heuristic, which is the number of 
	 * pieces that can attack each other
	 * @return int
	 */
	public int computeHeuristic(){
	
		for(int i=0; i<noOfQueens-1; i++){
			for(int j=i+1; j<noOfQueens; j++){
				if(state[i].hasConflict(state[j])){
						hn++;
				}
			}
		}
		
		return hn;
	}
	

	
	/**
	 * Hn getter
	 * @return
	 */
	public int getHeuristic(){
		return hn;
	}
	
	/**
	 * Implements Comparable method compareTo, judges a comparison
	 * on the basis of a Node's heuristic value
	 * @param n
	 * @return int
	 */
	public int compareTo(Board n){
		if(this.hn < n.getHeuristic())
			return -1;
		else if(this.hn > n.getHeuristic())
			return 1;
		else 
			return 0;
	}
	
	/**
	 * state setter
	 * @param s
	 */
	public void setState(Queen[] s){
		for(int i=0; i<noOfQueens; i++){
			state[i]= new Queen(s[i].getRow(), s[i].getColumn(), s[i].getQueens());
		}
	}
	
	
	/**
	 * state getter
	 * @return
	 */
	public Queen[] getState(){
		return state;
	}
	
	/**
	 * toString method prints out Node's state
	 * @return String
	 */
	public String toString(){
		String result="";
		String[][] board = new String[noOfQueens][noOfQueens];
		
		//initialise board with X's to indicate empty spaces
		for(int i=0; i<noOfQueens; i++)
			for(int j=0; j<noOfQueens; j++)
				board[i][j]="X ";
		
		//place the queens on the board
		for(int i=0; i<noOfQueens; i++){
			int row = state[i].getRow() > (noOfQueens -1) ? noOfQueens -1 : state[i].getRow();
			int column = state[i].getColumn()  > (noOfQueens -1) ? noOfQueens -1 : state[i].getColumn();
			board[row][column]="Q ";
		}
		
		//feed values into the result string
		for(int i=0; i<noOfQueens; i++){
			for(int j=0; j<noOfQueens; j++){
				result+=board[i][j];
			}
			result+="\n";
		}
		
		return result;
	}
}
