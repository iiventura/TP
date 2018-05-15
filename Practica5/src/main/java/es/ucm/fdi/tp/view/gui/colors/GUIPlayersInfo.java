package es.ucm.fdi.tp.view.gui.colors;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;
import es.ucm.fdi.tp.view.gui.table.JBoard;

import java.awt.Color;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;

@SuppressWarnings("serial")
public abstract class GUIPlayersInfo<S extends GameState<S,A>,A extends GameAction<S,A>> extends GUIComponents<S,A> {

	public void setPlayersInfoViewer(GUIPlayersInfo<S, A> playersInfoViewer) {	}
	
	abstract public void setNumberOfPlayer(int n);
	abstract public Color getPlayerColor(int p);
	
	public interface PlayersInfoObserver{
		public void ColorChanged(int id, Color color);
	}
	
	protected List<PlayersInfoObserver> observersC;

	public void addObserver(PlayersInfoObserver o){
		observersC.add(o);
	}

	protected void notifyObservers(int id, Color c){
		for(PlayersInfoObserver o: observersC){
			o.ColorChanged(id, c);
		}
	}

	//Metodos que no son realmente necesarios para estos componentes
	@Override
	public void enable() {}
	public void disable() {}
	public void update(S state) {}
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {	}
	public void setGameController(GameController<S, A> gameCtrl) {}

	public abstract void setBoard(JBoard board);

}
