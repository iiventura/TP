package es.ucm.fdi.tp.view.gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.gui.colors.GUIPlayersInfo;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartPanel;
import es.ucm.fdi.tp.view.gui.message.GUIMessage;
import es.ucm.fdi.tp.view.gui.window.GameWindow;

@SuppressWarnings("serial")
public abstract class GUIComponents<S extends GameState<S,A>,A extends GameAction<S,A>> extends JPanel {
	protected GameWindow<S,A> window;

	public GUIComponents(String tittle){
		window=new GameWindow<S,A>(tittle);
	}
	
	public GUIComponents(){}

	/**
	 * Asigna el titulo de un componente
	 */
	public void setTitle(String title){
		this.setBorder(BorderFactory.createTitledBorder(title));
	}
	
	public abstract void enable();
	public abstract void disable();
	public abstract void update(S state);
	public abstract void setMessageViewer(GUIMessage<S,A> infoViewer);
	public abstract void setPlayersInfoViewer(GUIPlayersInfo<S,A> playersInfoViewer);
	public abstract void setGameController(GameController<S, A> gameCtrl);
	public abstract void setSmartPanel(SmartPanel<S, A> sPanel);

}
