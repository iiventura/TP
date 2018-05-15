package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;


public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction>  {

	private static final long serialVersionUID = -7061739732232203112L;
	private int player;
	//C=Current N=New
    private int rowC;
    private int colC;
    private int rowN;
    private int colN;
    
	public WolfAndSheepAction(int player, int rowC, int colC, int rowN, int colN){
		this.player=player;
		this.rowC=rowC;
		this.colC=colC;
		this.rowN=rowN;
		this.colN=colN;
	}
    
	@Override
	public int getPlayerNumber() {
		return player;
	}

	@Override
	public WolfAndSheepState applyTo(WolfAndSheepState state) {
		if (player != state.getTurn()) {
            throw new IllegalArgumentException("Not the turn of this player");
        }

		 // make move
        int[][] board = state.getBoard();
        board[this.rowN][this.colN] = player;
        board[this.rowC][this.colC] = -1;
        
        // update state
    	WolfAndSheepState next, actual=new WolfAndSheepState(state,board,false,-1);
		if (actual.isWinner(board, state.getTurn())) {
			next = new WolfAndSheepState(actual, board, true, state.getTurn());
			
		} else {
			next =new WolfAndSheepState(state, board, false, -1);
		}
		
		return next;
	}
	
	public int  getCurrentRow(){
		return this.rowC;
	}
	
	public int getCurrentCol(){
		return this.colC;
	}
	
	public int getNewRow(){
		return this.rowN;
	}
	
	public int getNewCol(){
		return this.colN;
	}
	
	public String toString() {
		return "place " + player + " from (" + rowC + ", " + colC + ") to ("+rowN+","+colN+")";
	}
}
