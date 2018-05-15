package es.ucm.fdi.tp.view.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * Controlador de la Interfaz Grafica  que implementa a GameControllers
 * maneja el estado del juego y realiza los movimientos de este
 *
 */
public class GUIController<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameController<S,A> {
	private GameTable<S,A> game;
	private GamePlayer randPlayer;
	private GamePlayer smartPlayer;
	private int playerId;
	private PlayerMode playerMode;
	
	/**
	 * Constructora de GUIController
	 * @param playerId id jugador
	 * @param randPlayer random 
	 * @param smartPlayer inteligente
	 * @param game juego
	 */
	public GUIController(int playerId, GamePlayer randPlayer, GamePlayer smartPlayer, GameTable<S,A> game){
		this.playerId=playerId;
		this.randPlayer=randPlayer;
		this.smartPlayer=smartPlayer;
		this.game=game;
		this.playerMode=PlayerMode.MANUAL;
		
	}

	/**
	 * Realice una acción manual ejecutando la acción 'a' en el modelo.
	 * Asegúrese de que es el turno del jugador que posee este controlador
	 */	
	@Override
	public void makeManualMove(A action) {
		if(getPlayerId()==game.getState().getTurn())
		game.execute(action);
	}

	/**
	 * Realiza una accion aleatoria(RandomPlayer).
	 * Solicita la accion y la ejecuta en el modelo (GameTable)
	 * sabiendo que el turno del jugador coincide con el jugador 
	 * que tiene el controlador.
	 */
	@Override
	public void makeRandomMove() {
		if(getPlayerId()==game.getState().getTurn()) {
			A action=randPlayer.requestAction(game.getState());
			game.execute(action);
		} 
		
	}

	/**
	 * Realiza una accion inteligente(SmartPlayer).
	 * Solicita la accion y la ejecuta en el modelo (GameTable)
	 * sabiendo que el turno del jugador coincide con el jugador 
	 * que tiene el controlador.
	 */
	@Override
	public void makeSmartMove() {
		if(getPlayerId()==game.getState().getTurn()){
			A action = smartPlayer.requestAction(game.getState());	
			game.execute(action);
		}
	}

	/**
	 * Iniciar Juego
	 */
	@Override
	public void restartGame() {
		game.start();
		
	}

	/**
	 * Detener Juego
	 */
	@Override
	public void stopGame() {
		game.stop();
	}

	/**
	 * Modifica el modo de un jugador
	 * @param playermode
	 */
	@Override
	public void changePlayerMode(PlayerMode playermode) {
		this.playerMode=playermode;
		
		if(getPlayerMode().equals(PlayerMode.SMART)||getPlayerMode().equals(PlayerMode.RANDOM)){
			decideMakeAutomaticMove();
		}
		
	}

	/**
	 * Evalua el Movimiento que tiene que hacer segun 
	 * el modo del Jugador
	 */
	private void decideMakeAutomaticMove(){
		if(getPlayerMode()!=PlayerMode.MANUAL){
			if(getPlayerMode()==PlayerMode.RANDOM){
				makeRandomMove();
				
			}else if(getPlayerMode()==PlayerMode.SMART){
				makeSmartMove();
			}
		}
	}

	/**
	 * Maneja un evento 
	 * @param e evento que posee un state
	 */
	@Override
	public void handleEvent(GameEvent<S, A> e) {
		
		if(!game.getState().isFinished()){
			if(e.getType().equals(EventType.Change)||e.getType().equals(EventType.Info)){
				if(getPlayerId()==e.getState().getTurn()){
					decideMakeAutomaticMove();
				}
			}	
		}
		
	}

	/**
	 * Solicita el modo del jugaodr
	 * @return tipo jugador
	 */
	@Override
	public PlayerMode getPlayerMode() {
		return playerMode;
	}

	/**
	 * Solicita el identificador de jugador
	 * @return id jugador
	 */
	@Override
	public int getPlayerId() {
		return playerId;
	}

}
