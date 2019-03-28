/**
 * @file Queen.java
 * @author nsquires
 * A Queen chess piece
 */

public class Queen {
	private int row;
	private int column;
	private int queens;
	
	/**
	 * Constructor. Sets Queen's row and column
	 * @param r
	 * @param c
	 */
	public Queen(int r, int c, int n){
		row = r;
		column  = c;
		queens= n;
	}
	
	/**
	 * Determines whether this queen can attack another 
	 * @param q
	 * @return boolean
	 */
	public boolean hasConflict(Queen q){
		boolean isConflict=false;
		
		//test rows and columns
		if(row==q.getRow() || column==q.getColumn())
			isConflict=true;
		//test diagonal
		else if(Math.abs(column-q.getColumn()) == Math.abs(row-q.getRow()))
			isConflict=true;
			
		return isConflict;
	}
	
	/**
	 * moves the piece down
	 * @param spaces
	 */
	public void moveDown(int spaces){
		row+=spaces;
		
		//bound check/reset
		if(row>(queens-1) && row%(queens-1)!=0){
			row=(row%(queens-1))-1;
		}
		else if(row>(queens-1) && row%(queens-1)==0){
			row=queens-1;
		}
	}
	
	/**
	 * moves the piece down
	 * @param spaces
	 */
	public void moveDownSideWays(int spaces){
		row+=spaces;
		
		//bound check/reset
		if(row>(queens) && row%(queens)!=0){
			row=(row%(queens))-1;
		}
		else if(row>(queens) && row%(queens)==0){
			row=queens;
		}
	}
	
	/**
	 * row setter
	 * @param r
	 */
	public void setRow(int r){
		row = r;
	}
	
	/**
	 * row getter
	 * @return int
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * column setter
	 * @param c
	 */
	public void setColumn(int c){
		column = c;
	}
	
	public void setQueen(int n){
		queens = n;
	}
	
	/**
	 * column getter
	 * @return int
	 */
	public int getColumn(){
		return column;
	}
	public int getQueens(){
		return queens;
	}
	
	@Override
	public String toString(){
		return "("+row+", "+column+")";
	}
}
