package es.ucm.fdi.tp.view.console;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState<S,A>,A extends GameAction<S,A>> implements Runnable {

	private List<GamePlayer> players;
	private GameTable<S, A> game;
	
	public ConsoleController (List<GamePlayer> players, GameTable<S, A> game){
		this.players=players;
		this.game=game;
	}
	
	@Override
	public void run() {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); 
		}
		
		S currentState = (S) game.getState();
		game.start();
		
		while(!currentState.isFinished()){
			//request move
			A action = (A) players.get(currentState.getTurn()).requestAction(currentState);
			//apply move
			game.execute(action);
			//update state
			currentState=game.getState();
			
		}
		
		if(currentState.isFinished())
			game.stop();
	}
}
