package es.ucm.fdi.tp.view.gui.table;

import java.awt.Color;
import java.util.List;

import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessBoard.Piece;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartPanel;

@SuppressWarnings("serial")
public class ChessView extends RectBoardView<ChessState,ChessAction>{
	private int count, row_o,col_o; 
	private int figures[][];
	private Piece piece;
	

	public ChessView(GameState<ChessState, ChessAction> state) {
		super(state);
		generarTablero(state);
		count=-1;
		row_o=-1;
		col_o=-1;
	}

	private void generarTablero(GameState<ChessState,ChessAction> state){
		tablero=new Integer[getNumRows()][getNumCols()];
		figures=new int[getNumRows()][getNumCols()];
		
		for(int i=0;i<getNumRows();i++){
			for(int j=0;j<getNumCols();j++){
				figures[i][j]=((ChessState) state).at(i, j);
				if(figures[i][j]==16)
					tablero[i][j]=null;
				else if(figures[i][j]<=6)
					tablero[i][j]=0;
				else if(figures[i][j]>=9 &&figures[i][j]!=16)
					tablero[i][j]=1;
			}
			
		}
		
		
		
	}
	
	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		if(gameCtrl.getPlayerId()==gstate.getTurn()&&gameCtrl.getPlayerMode().equals(PlayerMode.MANUAL)){
			if(gstate.getWinner()==-1){
				List<ChessAction> acciones=gstate.validActions(gameCtrl.getPlayerId());
				ChessAction aux;
				
				if(count==-1){
					infoViewer.addContent("Your turn:");
					count++;
					row_o=row;
					col_o=col;
					//Verificar si es peon y llego a la ultima fila para jugadas especiales 
				
				
				}else{
				
					int i=0;
					
					
					while(i<acciones.size()){
						aux=acciones.get(i);
						
						if(row_o==aux.getSrcRow()&&col_o==aux.getSrcCol()
								&&row==aux.getDstRow()&&col==aux.getDstCol()){
									gameCtrl.makeManualMove(aux);
									gstate=aux.applyTo(gstate);
									infoViewer.addContent("Source ("+row_o+";"+col_o+") Destination ("+row+";"+col+")");
								//System.out.println(aux.getCurrentRow()+";"+aux.getCurrentCol()+"-"+aux.getNewRow()+";"+aux.getNewCol());
							}
						i++;
					}
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
		return Color.LIGHT_GRAY;
	}

	@Override
	public void update(ChessState state) {
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
	public void setPlayersInfoViewer(GUIPlayersInfo<ChessState, ChessAction> playersInfoViewer) {
		this.playersViewer=playersInfoViewer;	
		this.playersViewer.setNumberOfPlayer(2);
		this.playersViewer.setBoard(board);
		
	}

	@Override
	public void setSmartPanel(SmartPanel<ChessState, ChessAction> sPanel) {	
	}

	@SuppressWarnings("static-access")
	@Override
	protected String getImage(int row, int col) {		
		return piece.iconName((byte) figures[row][col]);
	}



}
