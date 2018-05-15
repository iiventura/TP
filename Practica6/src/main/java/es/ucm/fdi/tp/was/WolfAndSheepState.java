package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;


public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction>{
	private static final long serialVersionUID = -2311666526914984440L;
	
	private final int turn;
    private final boolean finished;
    private final int[][] board;
    private final int winner;

    private final int dim=8;

    final static int EMPTY = -1;
    
	public WolfAndSheepState() {
		super(2);
		
		board = new int[dim][dim];

		for (int i=0; i<dim; i++) {
			for (int j=0; j<dim; j++) 
				board[i][j] = EMPTY;
		}
		
		//Colocar posicion de las ovejas
		for ( int i = 1; i<dim; i+=2)
			board[0][i] = 1;
		//Colocar posicion del Lobo
		 board[7][0] = 0;
		 
		 
		 this.turn = 0;
	     this.winner = -1;
	     this.finished = false;
	}

	public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int winner) {
		super(2);
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
	}
	
	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
	    ArrayList<WolfAndSheepAction> valid = new ArrayList<>();
	    
        if (finished) {
            return valid;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
               if(board[i][j] != EMPTY && board[i][j] == playerNumber){
            	   if(playerNumber==0){
            		   if(withinDimension(i-1,j+1) && board[i-1][j+1]==EMPTY){
            			   valid.add(new WolfAndSheepAction(playerNumber,i,j, i - 1, j + 1));
            		   }
            		   
            		   if(withinDimension(i-1,j-1) && board[i-1][j-1]==EMPTY){
            			   valid.add(new WolfAndSheepAction(playerNumber,i,j, i - 1, j - 1));
            		   }
            	   }
            	   
            	   if(withinDimension(i+1,j+1) && board[i+1][j+1]==EMPTY){
        			   valid.add(new WolfAndSheepAction(playerNumber,i,j, i + 1, j + 1));
        		   }
            	   
            	   if(withinDimension(i+1,j-1) && board[i+1][j-1]==EMPTY){
        			   valid.add(new WolfAndSheepAction(playerNumber,i,j, i + 1, j - 1));
        		   }
            	
               }
            }
        }
        
        return valid;
	}
	
	
	public boolean isWinner(int[][] board, int playerNumber) {
		boolean won=false;
		
		if(playerNumber==1){//Ganan las Ovejas?
			 List<WolfAndSheepAction> acciones=validActions(0);
			 
			 if(acciones.isEmpty()) 
				 won = true;
			 else
				for ( int i = 0; i<dim; i+=2){
						if(at(7,i)==1)
							won = true;
				}	
			 
		}else if(playerNumber==0){//Ganan el Lobo?
			for ( int i = 1; i<dim; i+=2){
				if(at(0,i)==0)
					won = true;
			}	
		}
		
	
		return won;
	}
	
	

	public int[][] getBoard() {
		int[][] copy = new int[board.length][];
		for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
		return copy;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int k=0; k<dim; k++)
			sb.append("   "+k); 
		sb.append(System.getProperty("line.separator"));
			
		for (int i=0; i<board.length; i++) {
			sb.append(i+"|");
			for (int j=0; j<board.length; j++) {
				sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " W |" : " S |");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	
	@Override
	public boolean isFinished() {
		return finished;
	}

	
	@Override
	public int getWinner() {
		return winner;
	}

	@Override
	public int getTurn() {
		return turn;
	}

	public boolean withinDimension(int x, int y){
		boolean ok=true;
		
		if(y >= dim || x>=dim|| x < 0 || y < 0)
			ok = false;
		
		return ok;
	}

	public int at(int x, int y){
		return board[x][y];
	}
}
