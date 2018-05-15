package es.ucm.fdi.tp.view.gui.table;

import java.awt.Color;

import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartPanel;


@SuppressWarnings("serial")
public class TttView extends RectBoardView<TttState,TttAction>{
	
	public TttView(GameState<TttState, TttAction> state ){

		super(state);
		generarTablero(state);
		this.cplayers.put(0, Color.GREEN);
		this.cplayers.put(1, Color.YELLOW);
		
	}

	private void generarTablero(GameState<TttState, TttAction> state) {
		tablero=new Integer[getNumRows()][getNumCols()];
		int[][] aux=((TttState) state).getBoard();
		for(int i=0;i<getNumRows();i++){
			for(int j=0;j<getNumCols();j++){
				if(aux[i][j]==-1)
					tablero[i][j]=null;
				else
					tablero[i][j]=aux[i][j];
					
			}
		}
	}
	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(clickCount==1){
			if(gameCtrl.getPlayerId()==gstate.getTurn()&&gameCtrl.getPlayerMode().equals(PlayerMode.MANUAL)){
				if(gstate.getWinner()==-1){
					infoViewer.addContent("Your Turn: ");
					TttAction action = new TttAction(gstate.getTurn(), row, col);
				
					if(gstate.isValid(action)){
						gameCtrl.makeManualMove(action);
						gstate = action.applyTo(gstate);
						infoViewer.setContent("("+row+","+col+")");
					}
				}
			}else{
				infoViewer.addContent("It's not your turn, please wait");
			}
		}
			
	}

	@Override
	protected void keyTyped(int keyCode) {
		System.out.println("Key " + keyCode + " pressed ..");
		
	}

	@Override
	protected int getNumCols() {
		return 3;
	}

	@Override
	protected int getNumRows() {
		return 3;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		return tablero[row][col];
	}

	@Override
	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;
	}

	@Override
	public void update(TttState state) {
		this.gstate=state;
		generarTablero(gstate);
		this.board.repaint();
		
		if(state.isFinished()){
			if(state.getWinner()== gameCtrl.getPlayerId())
				infoViewer.addContent("You win!! \n");
			else if(state.getWinner()==-1)
				infoViewer.addContent("A draw!!\n");
			else
				infoViewer.addContent("You lost!!\n");
				
		}
		
	
		
	}

	@Override
	public void setPlayersInfoViewer(GUIPlayersInfo<TttState, TttAction> playersInfoViewer) {
		this.playersViewer=playersInfoViewer;	
		this.playersViewer.setNumberOfPlayer(2);
		this.playersViewer.setBoard(board);
		
	}

	@Override
	public void setSmartPanel(SmartPanel<TttState, TttAction> sPanel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getImage(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
