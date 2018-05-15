package es.ucm.fdi.tp.view.gui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo.PlayersInfoObserver;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;
import es.ucm.fdi.tp.view.gui.table.JBoard.Shape;


@SuppressWarnings("serial")
public abstract class RectBoardView<S extends GameState<S,A>, A extends GameAction<S,A>> 
				extends GUIComponents<S,A> implements PlayersInfoObserver {
	protected JBoard board;
	protected Integer [][] tablero;
	protected GUIMessage<S,A> infoViewer;
	protected GUIPlayersInfo<S,A> playersViewer;
	protected HashMap<Integer, Color> cplayers;
	protected S gstate;
	protected GameController<S,A> gameCtrl;
	
	@SuppressWarnings("unchecked")
	public RectBoardView(GameState<S,A> state){
		this.gstate=(S) state;
		cplayers=new HashMap<>();	
		
		initGUI();
	}



	private void initGUI(){
		this.setLayout(new BorderLayout());
		board=new JBoard(){

			@Override
			protected void keyTyped(int keyCode) {
				RectBoardView.this.keyTyped(keyCode);
				
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				RectBoardView.this.mouseClicked(row, col, clickCount,mouseButton);
				
			}

			@Override
			protected Shape getShape(int player) {
				return RectBoardView.this.getShape(player);
			}

			@Override
			protected Color getColor(int player) {
				return RectBoardView.this.getColor(player);
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return RectBoardView.this.getPosition(row,col);
			}

			@Override
			protected Color getBackground(int row, int col) {
				return RectBoardView.this.getBackground(row,col);
			}

			@Override
			protected int getNumRows() {
				return RectBoardView.this.getNumRows();
			}

			@Override
			protected int getNumCols() {
				return RectBoardView.this.getNumCols();
			}
			
			@Override
			protected int getSepPixels() {
				return RectBoardView.this.getSepPixels();
			}

			@Override
			protected String getImage(int row, int col) {
				
				return RectBoardView.this.getImage(row,col);
			}	
		};
		
		this.add(board,BorderLayout.CENTER);
	}
	



	protected int getSepPixels() {
		return 2;
	}

	

	
	protected Color getColor(int player) {
		return playersViewer.getPlayerColor(player);
	}

	protected Shape getShape(int player) {
		return Shape.CIRCLE;
	}

	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);
	protected abstract void keyTyped(int keyCode);
	protected abstract int getNumCols();
	protected abstract int getNumRows();	
	protected  abstract Integer getPosition(int row, int col);
	protected abstract Color getBackground(int row, int col);
	protected abstract String getImage(int row, int col);
	

	@Override
	public void ColorChanged(int id, Color color) {
		if(cplayers.get(id)!=null){
			this.cplayers.remove(id);
		}			
			this.cplayers.put(id, color);		
		
			this.board.repaint();
		
	}
	
	@Override
	public void enable() {
		board.setEnabled(true);
	}

	@Override
	public void disable() {
		board.setEnabled(false);	
	}


	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
		this.gameCtrl=gameCtrl;
	}
	
	//Metodos que no se implementan en este componente 
	@Override
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {
		this.infoViewer=infoViewer;
		
	}
	
	


}
