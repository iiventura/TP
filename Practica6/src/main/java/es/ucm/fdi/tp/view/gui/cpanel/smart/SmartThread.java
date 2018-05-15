package es.ucm.fdi.tp.view.gui.cpanel.smart;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;

public class SmartThread<S extends GameState<S,A>, A extends GameAction<S,A>> extends Thread {
	private ConcurrentAiPlayer smart;
	private GameTable<S,A> game;
	private SmartPanel<S,A> panel;
	private GUIMessage<S,A> infoViewer;
	
	
	public SmartThread(GamePlayer smartPlayer, GameTable<S, A> game) {
		this.smart=(ConcurrentAiPlayer) smartPlayer;
		this.game=game;
	}


	public void run(){
		smart.setMaxThreads(panel.getNThreads());
		smart.setTimeout(panel.getTimeout());
		SwingUtilities.invokeLater(() -> {
			panel.enable();
			});
		long init = System.currentTimeMillis();
		
		A action = smart.requestAction(game.getState());
		
		SwingUtilities.invokeLater(() -> {
			panel.disable();
		});
		
		if(action == null) {
			this.infoViewer.addContent("Searched movement interrupted!");
			
		}
		else {
			long end = System.currentTimeMillis();
			long total = end - init;
			int div = (int) (smart.getEvaluationCount() / total);
			
			if(game.getState().getTurn() == action.getPlayerNumber() && !game.getState().isFinished())
				game.execute((A)action);
			
			SwingUtilities.invokeLater(() -> {
				this.infoViewer.addContent(smart.getEvaluationCount() + " nodes in "+ total
						+ " ms ("+div+ "n/ms) value ="+ smart.getValue()+"\n");
			});
		
		}
	}
	
	
	

	public void setSmartPanel(SmartPanel<S,A> panel){
		this.panel=panel;
	}
	
	public void setMessageViewer(GUIMessage<S,A> infoViewer){
		this.infoViewer=infoViewer;
	}
}
