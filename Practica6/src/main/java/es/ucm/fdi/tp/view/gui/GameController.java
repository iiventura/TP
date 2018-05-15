package es.ucm.fdi.tp.view.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.view.gui.cpanel.smart.SmartThread;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;


public interface GameController<S extends GameState<S, A>, A extends GameAction<S,A>> {
	public void makeManualMove(A action);
	public void makeRandomMove();
	public void makeSmartMove();
	public void restartGame();
	public void stopGame();
	public void changePlayerMode(PlayerMode playermode);
	public void handleEvent(GameEvent<S,A> e);
	public PlayerMode getPlayerMode();
	public int getPlayerId();
	public void setThread(SmartThread<S,A> thread);
}
