package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	private S initialState;
	private S currentState;
	private boolean stop=true;
	private List<GameObserver<S, A>> observers= new ArrayList<GameObserver<S, A>>();
	
	/**
	 * Constructora del Modelo 
	 * @param initState estado inicial de un tablero
	 */
    public GameTable(S initState) {
    	this.initialState = initState;
		this.currentState=initState;
    }
        
    /**
     * Se inicializa la partida del juego
     */
    public void start() {
    	this.currentState = this.initialState;
    	stop=false; //indica que el juego no se ha detenido
    	notifyObservers(new GameEvent<S, A>(EventType.Start,null,currentState,
    					null,"The game started"));

    }
    
    /**
	 * Notifica a un usuario 
	 * @param e evento nuevo a la lista de observadores
	 */
    private void notifyObservers(GameEvent<S, A> e) {
    	for (GameObserver<S, A> o : observers){
			o.notifyEvent(e);
		}
	}

    /**
	 * Se detiene la partida de un juego
	 */
	public void stop() {
		
		if(stop){
			GameError error=new GameError("The game already has been stopped");
				
				notifyObservers(new GameEvent<S, A>(EventType.Stop,null,currentState,
					error,"The game already has been stopped"));
			
			throw error;
		}else{
			stop=true;
			notifyObservers(new GameEvent<S, A>(EventType.Stop,null,currentState,null,"The game has been stopped"));
		}
		
	}
	
	/**
	 * Se evalua el estado del juego (!start , stop, start) 
	 * @param action a aplicar en el estado del juego
	 */
	public void execute(A action) {
		S testState=action.applyTo(currentState);
		if (stop ||testState.equals(null)){
			String text="Impossible to make the action: ";
			
			if(stop){
				text+="The game was stopped before ";
			}
			else if(testState.equals(null)){
				text+="The game should start before realize an action";
			}
			
			GameError error=new GameError(text);
			notifyObservers(new GameEvent<S, A>(EventType.Error, null,currentState, error, error.getMessage()));
			throw error;
			
					
		}else{
			this.currentState=testState;
			notifyObservers(new GameEvent<S, A>(EventType.Change, action, currentState, null,"The game has change"));
				
		}
			
	
 	}
	
	/**
	 * Solcita el estado del Juego
	 * @return estado actual 
	 */
    public S getState() {
        return currentState;
    }

	/**
	 * Anade un observador a la lista y en caso de que el juego 
	 * ya haya iniciado, se le notifica a los observadores
	 */
    public void addObserver(GameObserver<S, A> o) {
        observers.add(o);
    }
    
    
    public void removeObserver(GameObserver<S, A> o) {
        observers.remove(o);
    }
}
