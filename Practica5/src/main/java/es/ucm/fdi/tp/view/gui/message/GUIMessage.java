package es.ucm.fdi.tp.view.gui.message;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.GUIComponents;
import es.ucm.fdi.tp.view.gui.GameController;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;


@SuppressWarnings("serial")
public abstract class GUIMessage<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIComponents<S,A> {

	
	//Metodos indispensables para este tipo de componentes
	@Override
	public void setMessageViewer(GUIMessage<S, A> infoViewer) {	}
	abstract public void addContent(String msg); 
	abstract public void setContent(String msg);
	
	//Metodos que no son  prioridad en este tipo de Componentes
	@Override
	public void enable() {}
	public void disable() {}
	public void setPlayersInfoViewer(GUIPlayersInfo<S, A> playersInfoViewer) {}
	public void update(S state){}
	public void setGameController(GameController<S, A> gameCtrl) {}
	


}
