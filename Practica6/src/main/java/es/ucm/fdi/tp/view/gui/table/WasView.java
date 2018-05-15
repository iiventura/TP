package es.ucm.fdi.tp.view.gui.table;

import java.awt.Color;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartPanel;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

@SuppressWarnings("serial")
public class WasView extends RectBoardView<WolfAndSheepState,WolfAndSheepAction> {
	private int count;
	private int row_o,col_o;
	
	public WasView(GameState<WolfAndSheepState, WolfAndSheepAction> state){
		super(state);
		generarTablero(state);
		count=-1;
		row_o=-1;
		col_o=-1;
	}

	private void generarTablero(GameState<WolfAndSheepState, WolfAndSheepAction> state) {
		tablero=new Integer[getNumRows()][getNumCols()];
		int[][] aux=((WolfAndSheepState) state).getBoard();
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
		if(gameCtrl.getPlayerId()==gstate.getTurn()&& gameCtrl.getPlayerMode().equals(PlayerMode.MANUAL)){
			if(gstate.getWinner()==-1){
				
				if(count==-1){
					infoViewer.addContent("Your Turn: ");
					count++;
					row_o=row;
					col_o=col;
					
					
				}else{
				
					int i=0;
					List<WolfAndSheepAction> acciones=gstate.validActions(gameCtrl.getPlayerId());
					
					while(i<acciones.size()){
						WolfAndSheepAction aux=acciones.get(i);
					
						if(row_o==aux.getCurrentRow()&&col_o==aux.getCurrentCol()
							&&row==aux.getNewRow()&&col==aux.getNewCol()){
								gameCtrl.makeManualMove(aux);
								gstate=aux.applyTo(gstate);
								infoViewer.addContent("Source ("+row_o+";"+col_o+") Destination ("+row+";"+col+")");
							//System.out.println(aux.getCurrentRow()+";"+aux.getCurrentCol()+"-"+aux.getNewRow()+";"+aux.getNewCol());
						}
						i++;
					}
					
					count--;
					
				}		
				
			}
		}else{
			infoViewer.addContent("It's not your turn, please wait");
		}
		
	}

	@Override
	protected void keyTyped(int keyCode) {
		System.out.println("Key " + keyCode + " pressed ..");
	}

	@Override
	protected int getNumCols() {
		
		return 8;
	}

	@Override
	protected int getNumRows() {
		return 8;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		return tablero[row][col];
	}

	@Override
	protected Color getBackground(int row, int col) {
		return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}

	@Override
	public void update(WolfAndSheepState state) {
		
			gstate=state;
			generarTablero(state);
			board.repaint();
			
			if(state.isFinished()){
				if(state.getWinner()== gameCtrl.getPlayerId())
					infoViewer.addContent("You win!!\n");
				else if(state.getWinner()==-1)
					infoViewer.addContent("A draw!!\n");
				else
					infoViewer.addContent("You lost!!\n");
					
			}
	}

	@Override
	public void setPlayersInfoViewer(GUIPlayersInfo<WolfAndSheepState, WolfAndSheepAction> playersInfoViewer) {
		this.playersViewer=playersInfoViewer;	
		this.playersViewer.setNumberOfPlayer(2);
		this.playersViewer.setBoard(board);
	}

	@Override
	public void setSmartPanel(SmartPanel<WolfAndSheepState, WolfAndSheepAction> sPanel) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected String getImage(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
